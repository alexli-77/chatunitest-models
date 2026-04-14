from flask import Flask, request, jsonify
import torch
from transformers import LlamaForCausalLM, AutoTokenizer, GenerationConfig, BitsAndBytesConfig
from peft import PeftModel

app = Flask(__name__)

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

@app.route('/generation', methods=['POST'])
def completion():
    data = request.get_json(silent=True) or {}

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

        output_ids = generate(prompt)
        # Decode only the newly generated tokens (skip the input prompt)
        input_len = tokenizer(prompt, return_tensors="pt")["input_ids"].shape[1]
        generated_ids = output_ids[0][input_len:]
        result = tokenizer.decode(generated_ids, skip_special_tokens=True)

        return jsonify({
            "success": True,
            "message": "ok",
            "files": [
                {
                    "relativePath": "se/kth/castor/generated/HybridRockyTest.java",
                    "content": result
                }
            ]
        }), 200

    except Exception as e:
        return jsonify({
            "success": False,
            "message": str(e),
            "files": []
        }), 200

if __name__ == '__main__':
    app.run(debug=False, port=1234, threaded=True)
