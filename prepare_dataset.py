"""
数据预处理脚本：将 zzzghttt/context2test 转换为与推理 prompt 一致的训练格式。

推理时 model_server.py 使用的 prompt 格式：
    mode=COMPLETION
    projectPath=...
    assertionStyle=JUNIT
    staticSnapshot:
    {Java 方法 + 上下文}
    runtimeFacts:
    (空)

训练数据也使用同样格式，确保 train/infer 一致。
"""

import re
from datasets import load_dataset, Dataset
import pandas as pd


PROMPT_TEMPLATE = """\
mode=COMPLETION
projectPath=unknown
assertionStyle=JUNIT
staticSnapshot:
{context}
runtimeFacts:

### JUnit Test:
"""


def build_prompt(context: str) -> str:
    return PROMPT_TEMPLATE.format(context=context.strip())


def build_full_sample(context: str, test: str) -> str:
    """训练时 input + output 拼接成一条完整序列"""
    return build_prompt(context) + test.strip()


def has_assertion(test: str) -> bool:
    """过滤掉无断言的无效测试"""
    assertion_keywords = ["assert", "Assert", "verify", "Verify", "fail(", "Fail("]
    return any(kw in test for kw in assertion_keywords)


def is_valid_java(code: str) -> bool:
    """简单结构校验：括号基本匹配"""
    return code.count("{") > 0 and abs(code.count("{") - code.count("}")) <= 2


def estimate_tokens(text: str) -> int:
    """粗略估算 token 数（按字符数/4）"""
    return len(text) // 4


def preprocess(
    dataset_name: str = "zzzghttt/context2test",
    split: str = "train",
    max_samples: int = 5000,
    max_token_len: int = 2048,
    output_path: str = "data/train_formatted.jsonl",
):
    print(f"Loading dataset: {dataset_name} ...")
    raw = load_dataset(dataset_name, split=split)
    print(f"Raw samples: {len(raw)}")

    # 打印字段名，便于调试
    print(f"Columns: {raw.column_names}")

    # context2test 字段名可能是 'input'/'output' 或 'context'/'test'
    # 自动检测
    col_context = None
    col_test = None
    for c in ["context", "input", "source"]:
        if c in raw.column_names:
            col_context = c
            break
    for t in ["test", "output", "target"]:
        if t in raw.column_names:
            col_test = t
            break

    if col_context is None or col_test is None:
        print(f"ERROR: Cannot find context/test columns. Available: {raw.column_names}")
        return

    print(f"Using columns: context='{col_context}', test='{col_test}'")

    df = raw.to_pandas()[[col_context, col_test]].copy()
    df.columns = ["context", "test"]

    before = len(df)

    # 1. 去重
    df = df.drop_duplicates(subset=["context"])
    print(f"After dedup: {len(df)} (removed {before - len(df)})")

    # 2. 过滤无断言样本
    df = df[df["test"].apply(has_assertion)]
    print(f"After assertion filter: {len(df)}")

    # 3. 过滤结构异常的测试代码
    df = df[df["test"].apply(is_valid_java)]
    print(f"After java structure filter: {len(df)}")

    # 4. 过滤 context 为空的样本
    df = df[df["context"].str.strip().str.len() > 30]
    print(f"After empty context filter: {len(df)}")

    # 5. 构建 prompt，过滤超长样本
    df["full_text"] = df.apply(
        lambda row: build_full_sample(row["context"], row["test"]), axis=1
    )
    df["token_est"] = df["full_text"].apply(estimate_tokens)
    df = df[df["token_est"] <= max_token_len]
    print(f"After token length filter (<={max_token_len} tokens): {len(df)}")

    # 6. 取前 max_samples 条（随机打乱后取）
    df = df.sample(frac=1, random_state=42).reset_index(drop=True)
    df = df.head(max_samples)
    print(f"Final samples: {len(df)}")

    # 7. 保存
    import os
    os.makedirs("data", exist_ok=True)
    df[["full_text"]].to_json(output_path, orient="records", lines=True, force_ascii=False)
    print(f"Saved to {output_path}")

    # 打印一个样例
    print("\n=== Sample ===")
    print(df["full_text"].iloc[0][:800])
    print("...")


if __name__ == "__main__":
    preprocess()
