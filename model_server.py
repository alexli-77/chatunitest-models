from flask import Flask, request, jsonify
import torch
from transformers import LlamaForCausalLM, AutoTokenizer, GenerationConfig
from peft import PeftModel

app = Flask(__name__)

device = "mps" if torch.backends.mps.is_available() else "cpu"
BASE_MODEL = "codellama/CodeLlama-7b-Instruct-hf"

tokenizer = AutoTokenizer.from_pretrained(BASE_MODEL)

model = LlamaForCausalLM.from_pretrained(
    BASE_MODEL,
    torch_dtype=torch.float16,
    device_map="auto",
)

model = PeftModel.from_pretrained(
    model,
    "Leon-20292783/my-testgen-lora",
    torch_dtype=torch.float16,
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
        max_tokens: int = 512,
        temperature: float = 0.6,
        ):
    generation_config = GenerationConfig(
            temperature=temperature,
            do_sample=True,
            top_p=0.95,
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

        result = generate(prompt)

        # 统一返回 Java 客户端期望结构
        # 这里演示单文件输出，你可按模型结果拆多文件
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
    app.run(debug=True, port=1234, threaded=True)
