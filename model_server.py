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
        max_tokens: int = 256,
        temperature: float = 0.6,
        ):
    generation_config = GenerationConfig(
            temperature=temperature,
            do_sample=False,       # greedy decoding: faster + more deterministic
            repetition_penalty=1.1,
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
            static_snapshot = data.get('staticSnapshot', '')
            runtime_facts = data.get('runtimeFacts', '')

            prompt = (
                f"mode={mode}\n"
                f"projectPath={project_path}\n"
                f"assertionStyle={assertion_style}\n"
                f"staticSnapshot:\n{static_snapshot}\n"
                f"runtimeFacts:\n{runtime_facts}\n"
            )

        input_ids = tokenizer(prompt, return_tensors="pt")["input_ids"]
        input_tokens = input_ids.shape[1]

        t_start = time.time()
        output_ids = generate(prompt)
        inference_time_ms = (time.time() - t_start) * 1000

        generated_ids = output_ids[0][input_tokens:]
        output_tokens = len(generated_ids)
        result = tokenizer.decode(generated_ids, skip_special_tokens=True)

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
                    "relativePath": "se/kth/castor/generated/HybridRockyTest.java",
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
