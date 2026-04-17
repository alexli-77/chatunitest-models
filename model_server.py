from flask import Flask, request, jsonify
import torch
import time
from transformers import LlamaForCausalLM, AutoTokenizer, GenerationConfig, BitsAndBytesConfig
from peft import PeftModel

app = Flask(__name__)

# Global stats
stats = {
    "total_requests": 0,
    "success_count": 0,
    "error_count": 0,
    "total_inference_time_ms": 0.0,
    "total_tokens_generated": 0,
}

device = "cuda" if torch.cuda.is_available() else ("mps" if torch.backends.mps.is_available() else "cpu")
BASE_MODEL = "codellama/CodeLlama-7b-Instruct-hf"

tokenizer = AutoTokenizer.from_pretrained(BASE_MODEL)

bnb_config = BitsAndBytesConfig(
    load_in_4bit=True,
    bnb_4bit_use_double_quant=True,
    bnb_4bit_quant_type="nf4",
    bnb_4bit_compute_dtype=torch.float16,
)

model = LlamaForCausalLM.from_pretrained(
    BASE_MODEL,
    quantization_config=bnb_config,
    device_map="auto",
)

model = PeftModel.from_pretrained(
    model,
    "Leon-20292783/my-testgen-lora",
)

model.eval()

import json as _json
import re as _re

def _format_static_snapshot(raw: str) -> str:
    """Convert staticSnapshot JSON into plain Java source text matching training format."""
    if not raw:
        return ""
    try:
        snapshot = _json.loads(raw)
    except Exception:
        return raw  # already plain text, pass through
    parts = []
    for method in snapshot.get("methods", []):
        snippet = method.get("sourceSnippet", "").strip()
        if snippet:
            parts.append(snippet)
    return "\n\n".join(parts) if parts else raw


def _format_runtime_facts(raw: str) -> str:
    """Convert runtimeFacts JSON into readable comments matching training format.

    Training data had empty runtimeFacts. For runtime-grounded condition we add
    a compact comment block so the model can see real values without format shock.
    """
    if not raw:
        return ""
    try:
        facts = _json.loads(raw)
    except Exception:
        return raw
    methods = facts.get("methods", [])
    if not methods:
        return ""
    lines = []
    for m in methods:
        mid = m.get("methodId", "")
        ret_vals = m.get("returnedValues") or []
        param_vals = m.get("parameterValues") or []
        if ret_vals or param_vals:
            lines.append(f"// Runtime observations for {mid.split('#')[-1]}:")
            if ret_vals:
                lines.append(f"//   returned: {', '.join(str(v) for v in ret_vals[:3])}")
            if param_vals:
                sample = param_vals[0]
                lines.append(f"//   params:   {', '.join(str(v) for v in sample[:5])}")
    return "\n".join(lines)


def tokenize(text):
    result = tokenizer(
        text,
        truncation=True,
        max_length=2048,
        padding=False,
        return_tensors="pt",
        )
    return result["input_ids"].to(device)

def generate(
        text: str,
        max_tokens: int = 512,
        temperature: float = 0.6,
        ):
    generation_config = GenerationConfig(
            do_sample=True,
            temperature=temperature,
            top_p=0.9,
            repetition_penalty=1.3,
            eos_token_id=2,
            pad_token_id=0,
            )
    input_ids = tokenize(text)
    with torch.no_grad():
        result = model.generate(
                input_ids=input_ids,
                generation_config=generation_config,
                output_scores=True,
                max_new_tokens=max_tokens,
                )
    return result

@app.route('/stats', methods=['GET'])
def get_stats():
    total = stats["success_count"] + stats["error_count"]
    avg_ms = (
        stats["total_inference_time_ms"] / stats["success_count"]
        if stats["success_count"] > 0 else 0
    )
    return jsonify({
        "total_requests": total,
        "success_count": stats["success_count"],
        "error_count": stats["error_count"],
        "avg_inference_time_ms": round(avg_ms, 1),
        "total_tokens_generated": stats["total_tokens_generated"],
    })


@app.route('/generation', methods=['POST'])
def completion():
    data = request.get_json(silent=True) or {}
    stats["total_requests"] += 1

    try:
        # old protocol：input
        if 'input' in data:
            prompt = data['input']
        else:
            # new protocol：Runtime2Test object request
            mode = data.get('mode', 'UNKNOWN')
            project_path = data.get('projectPath', '')
            assertion_style = data.get('assertionStyle', 'JUNIT')
            static_snapshot_raw = data.get('staticSnapshot', '')
            runtime_facts_raw = data.get('runtimeFacts', '')

            # --- Format staticSnapshot ---
            # Training data used plain Java source code; inference sends JSON.
            # Extract sourceSnippet fields so the prompt matches training format.
            static_text = _format_static_snapshot(static_snapshot_raw)

            # --- Format runtimeFacts ---
            # Training data had empty runtimeFacts; for runtime-grounded condition
            # we summarise real values as readable comments so the model can use them.
            runtime_text = _format_runtime_facts(runtime_facts_raw)

            # Always use mode=COMPLETION to match training format exactly.
            prompt = (
                f"mode=COMPLETION\n"
                f"projectPath={project_path}\n"
                f"assertionStyle={assertion_style}\n"
                f"staticSnapshot:\n{static_text}\n"
                f"runtimeFacts:\n{runtime_text}\n"
                f"### JUnit Test:\n"
            )

        input_ids = tokenizer(prompt, return_tensors="pt")["input_ids"]
        input_tokens = input_ids.shape[1]

        t_start = time.time()
        output_ids = generate(prompt)
        inference_time_ms = (time.time() - t_start) * 1000

        generated_ids = output_ids[0][input_tokens:]
        output_tokens = len(generated_ids)
        result = tokenizer.decode(generated_ids, skip_special_tokens=True)

        # Truncate at prompt bleed-through markers
        for stop_marker in ["runtimeFacts:", "### JUnit Test:", "staticSnapshot:"]:
            idx = result.find(stop_marker)
            if idx != -1:
                result = result[:idx].strip()

        # Extract class name from methodId for proper naming
        import re
        declaring_class = "HybridRocky"
        if 'static_snapshot_raw' in dir() or True:
            # parse from request data
            _mid = data.get('staticSnapshot', '')
            _cls_match = re.search(r'"methodId"\s*:\s*"([^"#]+)#', _mid)
            if not _cls_match:
                # fallback: parse from already-decoded static_text
                _cls_match = re.search(r'(?:class|interface)\s+(\w+)', static_text if 'static_text' in dir() else '')
            if _cls_match:
                _fqn = _cls_match.group(1)
                declaring_class = _fqn.split('.')[-1]

        # Extract only @Test method blocks, strip trailing garbage
        # (static final fields, @BeforeEach, @AfterAll, bare variable declarations, etc.)
        def extract_clean_test_methods(text: str) -> list[str]:
            first = text.find("@Test")
            if first == -1:
                return []
            body = text[first:]
            # Split on @Test boundaries
            parts = re.split(r'(?=@Test)', body)
            methods = []
            for part in parts:
                part = part.strip()
                if not part.startswith("@Test"):
                    continue
                if "{" not in part:
                    continue
                # Find the end of this method: balance braces
                depth = 0
                end = -1
                in_str = False
                for i, ch in enumerate(part):
                    if ch == '"' and (i == 0 or part[i-1] != '\\'):
                        in_str = not in_str
                    if in_str:
                        continue
                    if ch == '{':
                        depth += 1
                    elif ch == '}':
                        depth -= 1
                        if depth == 0:
                            end = i + 1
                            break
                if end != -1:
                    methods.append(part[:end])
                else:
                    # unbalanced - still include but trimmed
                    methods.append(part)
            return methods

        # Always re-extract @Test blocks to strip trailing garbage
        # (static final fields, @BeforeEach, bare variable declarations, etc.)
        test_methods = extract_clean_test_methods(result)
        body = "\n\n".join(m.strip() for m in test_methods) if test_methods else result.strip()

        result = (
            f"package se.kth.castor.generated;\n\n"
            f"import org.junit.jupiter.api.Test;\n"
            f"import static org.junit.jupiter.api.Assertions.*;\n\n"
            f"public class {declaring_class}RockyTest {{\n\n"
            f"{body}\n\n"
            f"}}"
        )

        stats["success_count"] += 1
        stats["total_inference_time_ms"] += inference_time_ms
        stats["total_tokens_generated"] += output_tokens

        return jsonify({
            "success": True,
            "message": "ok",
            "stats": {
                "inference_time_ms": round(inference_time_ms, 1),
                "input_tokens": input_tokens,
                "output_tokens": output_tokens,
            },
            "files": [
                {
                    "relativePath": f"se/kth/castor/generated/{declaring_class}RockyTest.java",
                    "content": result
                }
            ]
        }), 200

    except Exception as e:
        stats["error_count"] += 1
        return jsonify({
            "success": False,
            "message": str(e),
            "files": []
        }), 200

def start_cloudflare_tunnel(port: int) -> None:
    """Start a cloudflared tunnel and print the public URL."""
    import os
    import shutil
    import subprocess
    import threading
    import re

    binary = shutil.which("cloudflared") or os.path.expanduser("~/cloudflared")
    if not os.path.isfile(binary):
        print("[tunnel] cloudflared not found. Run: curl -L https://github.com/cloudflare/cloudflared/releases/latest/download/cloudflared-linux-amd64 -o ~/cloudflared && chmod +x ~/cloudflared")
        return

    def _run():
        proc = subprocess.Popen(
            [binary, "tunnel", "--url", f"http://localhost:{port}"],
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
        )
        for line in proc.stdout:
            print(f"[tunnel] {line}", end="")
            match = re.search(r"https://[a-zA-Z0-9-]+\.trycloudflare\.com", line)
            if match:
                print(f"\n[tunnel] Public URL: {match.group(0)}/generation\n")

    threading.Thread(target=_run, daemon=True).start()


if __name__ == '__main__':
    start_cloudflare_tunnel(1234)
    app.run(debug=False, port=1234, threaded=True)
