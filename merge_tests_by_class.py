#!/usr/bin/env python3
"""
Merge LLM-generated per-method test files into per-class test files,
matching ProDJ's output structure.

Usage:
    python3 merge_tests_by_class.py \
        --input-dir /path/to/r2t-output-v2 \
        --output-dir /path/to/merged-tests \
        --manifest /path/to/manifest.json
"""

import argparse
import json
import re
from pathlib import Path


COMMON_IMPORTS = """\
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
"""


def extract_class_name(method_id: str) -> str:
    """org.apache.pdfbox.cos.COSArray#getObject(int) -> COSArray"""
    return method_id.split("#")[0].split(".")[-1]


def extract_package(method_id: str) -> str:
    """org.apache.pdfbox.cos.COSArray#getObject(int) -> org.apache.pdfbox.cos"""
    class_part = method_id.split("#")[0]
    parts = class_part.split(".")
    return ".".join(parts[:-1])


def extract_test_methods(java_content: str) -> list[str]:
    """Extract individual @Test method blocks from a Java file."""
    first_test = java_content.find("@Test")
    if first_test == -1:
        return []
    content = java_content[first_test:]

    # Remove trailing class closing brace
    content = content.rstrip()
    if content.endswith("}"):
        content = content[:-1].rstrip()

    # Split on @Test boundaries
    parts = re.split(r'(?=@Test)', content)
    methods = []
    for part in parts:
        part = part.strip()
        if not part.startswith("@Test"):
            continue
        if "{" not in part:
            continue
        methods.append(part)

    return methods


def build_folder_prefix(method_id: str) -> str:
    """
    Convert method_id to the folder name prefix used by scheduler.
    org.apache.pdfbox.cos.COSArray#getObject(int)
    -> org.apache.pdfbox.cos.COSArray_getObject_int
    """
    # Replace # with _
    s = method_id.replace("#", "_")
    # Remove parentheses content chars that are sanitized
    s = re.sub(r"[^a-zA-Z0-9_.\-]+", "_", s)
    return s[:60]  # folders are truncated to ~120 chars but first 60 is enough


def merge(input_dir: Path, output_dir: Path, manifest_path: Path) -> None:
    manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
    results = manifest.get("results", [])

    # Build map: folder_name -> java file path
    # folder structure: <input_dir>/<method_folder>/se/kth/castor/generated/HybridRockyTest.java
    all_java_files = {}
    for f in input_dir.rglob("HybridRockyTest.java"):
        # Walk up to find the top-level method folder (direct child of input_dir)
        parts = f.relative_to(input_dir).parts
        folder_name = parts[0]
        all_java_files[folder_name] = f

    # Group by declaring class
    class_to_methods: dict[str, list[dict]] = {}
    for r in results:
        if not r.get("success"):
            continue
        method_id = r["methodId"]
        class_name = extract_class_name(method_id)
        class_to_methods.setdefault(class_name, []).append(r)

    output_dir.mkdir(parents=True, exist_ok=True)
    stats = {"total_classes": 0, "total_tests": 0, "empty_classes": 0}

    for class_name, method_results in sorted(class_to_methods.items()):
        all_methods: list[str] = []
        package = ""

        for r in method_results:
            method_id = r["methodId"]
            if not package:
                package = extract_package(method_id)

            # Find matching folder by prefix
            prefix = build_folder_prefix(method_id)
            matched_folder = None
            for folder_name in all_java_files:
                if folder_name.startswith(prefix[:50]):
                    matched_folder = folder_name
                    break

            if not matched_folder:
                # fallback: search by class+method name
                class_method = f"{class_name}_{method_id.split('#')[1].split('(')[0]}"
                for folder_name in all_java_files:
                    if class_method in folder_name:
                        matched_folder = folder_name
                        break

            if not matched_folder:
                continue

            content = all_java_files[matched_folder].read_text(encoding="utf-8", errors="ignore")
            methods = extract_test_methods(content)
            all_methods.extend(methods)

        if not all_methods:
            stats["empty_classes"] += 1
            continue

        # Deduplicate by method name
        seen_names: set[str] = set()
        unique_methods = []
        for m in all_methods:
            name_match = re.search(r'void\s+(\w+)\s*\(', m)
            name = name_match.group(1) if name_match else None
            if name and name in seen_names:
                continue
            if name:
                seen_names.add(name)
            unique_methods.append(m)

        # Build merged class file
        test_class_name = f"{class_name}RockyTest"
        indented_methods = "\n\n    ".join(
            m.replace("\n", "\n    ") for m in unique_methods
        )

        java_file = (
            f"package {package};\n\n"
            f"{COMMON_IMPORTS}\n"
            f"public class {test_class_name} {{\n\n"
            f"    {indented_methods}\n\n"
            f"}}\n"
        )

        package_path = output_dir / Path(*package.split("."))
        package_path.mkdir(parents=True, exist_ok=True)
        out_file = package_path / f"{test_class_name}.java"
        out_file.write_text(java_file, encoding="utf-8")

        stats["total_classes"] += 1
        stats["total_tests"] += len(unique_methods)
        print(f"[OK] {test_class_name:50} — {len(unique_methods)} tests")

    print()
    print(f"=== 合并完成 ===")
    print(f"生成类文件数:   {stats['total_classes']}")
    print(f"总 @Test 方法:  {stats['total_tests']}")
    print(f"空类（跳过）:   {stats['empty_classes']}")


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Merge per-method tests into per-class files")
    parser.add_argument("--input-dir", required=True)
    parser.add_argument("--output-dir", required=True)
    parser.add_argument("--manifest", required=True)
    return parser.parse_args()


if __name__ == "__main__":
    args = parse_args()
    merge(Path(args.input_dir), Path(args.output_dir), Path(args.manifest))
