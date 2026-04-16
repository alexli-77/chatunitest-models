#!/usr/bin/env python3
"""
Compare LLM-generated tests vs ProDJ baseline.

Usage:
    python3 compare_tests.py \
        --llm-dir /path/to/merged-llm-tests \
        --prodj-dir /path/to/pdfbox/rtf/generated-tests/src/test/java \
        [--llm-manifest /path/to/manifest.json]

Metrics:
    - Class file count
    - @Test method count (total and per-class)
    - Methods with at least one assertion
    - Assertion density (assertions / @Test)
    - JUnit5 (@Test) vs AssertJ (assertThat) assertion style
"""

from __future__ import annotations

import argparse
import json
import re
from pathlib import Path
from dataclasses import dataclass, field


# ── Assertion detection ──────────────────────────────────────────────────────

# JUnit 5 / standard assertions
JUNIT_ASSERT = re.compile(
    r'\b(assertEquals|assertNotEquals|assertNull|assertNotNull|assertTrue|assertFalse'
    r'|assertSame|assertNotSame|assertArrayEquals|assertIterableEquals'
    r'|assertThrows|assertDoesNotThrow|assertTimeout|fail)\s*\(',
    re.MULTILINE,
)

# AssertJ fluent assertions
ASSERTJ_ASSERT = re.compile(
    r'\bassertThat\s*\(',
    re.MULTILINE,
)

# Combined
ANY_ASSERT = re.compile(
    r'\b(assertEquals|assertNotEquals|assertNull|assertNotNull|assertTrue|assertFalse'
    r'|assertSame|assertNotSame|assertArrayEquals|assertIterableEquals'
    r'|assertThrows|assertDoesNotThrow|assertTimeout|fail|assertThat)\s*\(',
    re.MULTILINE,
)


# ── Analysis helpers ─────────────────────────────────────────────────────────

@dataclass
class ClassStats:
    file_path: str
    class_name: str
    test_count: int = 0
    tests_with_assertions: int = 0
    total_assertions: int = 0
    junit_assertions: int = 0
    assertj_assertions: int = 0
    test_names: list[str] = field(default_factory=list)


def extract_test_blocks(content: str) -> list[str]:
    """Split Java file into individual @Test method blocks."""
    first = content.find("@Test")
    if first == -1:
        return []
    # Split on @Test boundaries
    parts = re.split(r'(?=@Test)', content[first:])
    blocks = []
    for part in parts:
        part = part.strip()
        if part.startswith("@Test") and "{" in part:
            blocks.append(part)
    return blocks


def analyze_java_file(path: Path) -> ClassStats:
    content = path.read_text(encoding="utf-8", errors="ignore")
    class_match = re.search(r'public\s+class\s+(\w+)', content)
    class_name = class_match.group(1) if class_match else path.stem

    stats = ClassStats(file_path=str(path), class_name=class_name)

    blocks = extract_test_blocks(content)
    for block in blocks:
        name_match = re.search(r'void\s+(\w+)\s*\(', block)
        test_name = name_match.group(1) if name_match else "unknown"
        stats.test_names.append(test_name)
        stats.test_count += 1

        junit_count = len(JUNIT_ASSERT.findall(block))
        assertj_count = len(ASSERTJ_ASSERT.findall(block))
        total = junit_count + assertj_count

        stats.junit_assertions += junit_count
        stats.assertj_assertions += assertj_count
        stats.total_assertions += total
        if total > 0:
            stats.tests_with_assertions += 1

    return stats


def analyze_dir(root: Path) -> list[ClassStats]:
    results = []
    for java_file in sorted(root.rglob("*.java")):
        # Skip helper/utility files
        name = java_file.stem
        if "Helper" in name or "Util" in name or "Base" in name:
            continue
        stats = analyze_java_file(java_file)
        if stats.test_count > 0:
            results.append(stats)
    return results


def print_table(title: str, all_stats: list[ClassStats]) -> None:
    print(f"\n{'='*70}")
    print(f"  {title}")
    print(f"{'='*70}")
    print(f"{'Class':<45} {'Tests':>6} {'w/Assert':>9} {'JUnit':>6} {'AssertJ':>8}")
    print(f"{'-'*70}")
    for s in sorted(all_stats, key=lambda x: x.class_name):
        print(
            f"{s.class_name:<45} {s.test_count:>6} "
            f"{s.tests_with_assertions:>9} {s.junit_assertions:>6} {s.assertj_assertions:>8}"
        )
    print(f"{'-'*70}")
    total_tests = sum(s.test_count for s in all_stats)
    total_with_assert = sum(s.tests_with_assertions for s in all_stats)
    total_junit = sum(s.junit_assertions for s in all_stats)
    total_assertj = sum(s.assertj_assertions for s in all_stats)
    total_assert_count = sum(s.total_assertions for s in all_stats)
    print(
        f"{'TOTAL':<45} {total_tests:>6} "
        f"{total_with_assert:>9} {total_junit:>6} {total_assertj:>8}"
    )
    print(f"\n  Classes: {len(all_stats)}")
    print(f"  @Test methods: {total_tests}")
    print(f"  Tests with ≥1 assertion: {total_with_assert} ({100*total_with_assert//total_tests if total_tests else 0}%)")
    print(f"  Total assertions: {total_assert_count}")
    if total_tests:
        print(f"  Avg assertions / test: {total_assert_count / total_tests:.2f}")
        print(f"  Avg tests / class: {total_tests / len(all_stats):.1f}")


def compare_summary(llm: list[ClassStats], prodj: list[ClassStats]) -> None:
    llm_tests = sum(s.test_count for s in llm)
    prodj_tests = sum(s.test_count for s in prodj)
    llm_with_assert = sum(s.tests_with_assertions for s in llm)
    prodj_with_assert = sum(s.tests_with_assertions for s in prodj)
    llm_total_assert = sum(s.total_assertions for s in llm)
    prodj_total_assert = sum(s.total_assertions for s in prodj)

    print(f"\n{'='*70}")
    print(f"  COMPARISON SUMMARY")
    print(f"{'='*70}")
    print(f"{'Metric':<40} {'LLM':>10} {'ProDJ':>10}")
    print(f"{'-'*60}")
    print(f"{'Class files':<40} {len(llm):>10} {len(prodj):>10}")
    print(f"{'@Test methods':<40} {llm_tests:>10} {prodj_tests:>10}")
    print(f"{'Tests w/ assertions':<40} {llm_with_assert:>10} {prodj_with_assert:>10}")
    print(f"{'Assert rate (%)':<40} {100*llm_with_assert//llm_tests if llm_tests else 0:>9}% {100*prodj_with_assert//prodj_tests if prodj_tests else 0:>9}%")
    print(f"{'Total assertions':<40} {llm_total_assert:>10} {prodj_total_assert:>10}")
    avg_llm = llm_total_assert/llm_tests if llm_tests else 0
    avg_prodj = prodj_total_assert/prodj_tests if prodj_tests else 0
    print(f"{'Avg assertions/test':<40} {avg_llm:>10.2f} {avg_prodj:>10.2f}")
    avg_t_llm = llm_tests/len(llm) if llm else 0
    avg_t_prodj = prodj_tests/len(prodj) if prodj else 0
    print(f"{'Avg tests/class':<40} {avg_t_llm:>10.1f} {avg_t_prodj:>10.1f}")

    # Class overlap
    llm_classes = {s.class_name.replace("RockyTest", "") for s in llm}
    prodj_classes = {s.class_name.replace("RockyTest", "") for s in prodj}
    overlap = llm_classes & prodj_classes
    print(f"\n  Classes in common: {len(overlap)} / {len(prodj_classes)} ProDJ classes")
    print(f"  LLM-only classes: {sorted(llm_classes - prodj_classes)}")
    print(f"  ProDJ-only classes: {sorted(prodj_classes - llm_classes)}")

    print(f"\n  LLM vs ProDJ @Test ratio: {llm_tests/prodj_tests*100:.1f}% of ProDJ count")

    # Per-class comparison for overlapping classes
    print(f"\n{'='*70}")
    print(f"  PER-CLASS COMPARISON (overlapping classes)")
    print(f"{'='*70}")
    print(f"{'Class':<40} {'LLM tests':>10} {'ProDJ tests':>12} {'LLM/ProDJ':>10}")
    print(f"{'-'*72}")
    llm_map = {s.class_name.replace("RockyTest", ""): s for s in llm}
    prodj_map = {s.class_name.replace("RockyTest", ""): s for s in prodj}
    for cls in sorted(overlap):
        lt = llm_map[cls].test_count if cls in llm_map else 0
        pt = prodj_map[cls].test_count if cls in prodj_map else 0
        ratio = f"{lt/pt*100:.0f}%" if pt else "N/A"
        print(f"{cls:<40} {lt:>10} {pt:>12} {ratio:>10}")


def main() -> None:
    parser = argparse.ArgumentParser(description="Compare LLM vs ProDJ test quality")
    parser.add_argument("--llm-dir", required=True, help="Directory with merged LLM test files")
    parser.add_argument("--prodj-dir", required=True, help="Directory with ProDJ test files")
    parser.add_argument("--llm-manifest", default="", help="Optional LLM manifest.json for additional stats")
    args = parser.parse_args()

    llm_root = Path(args.llm_dir)
    prodj_root = Path(args.prodj_dir)

    print(f"LLM tests dir:   {llm_root}")
    print(f"ProDJ tests dir: {prodj_root}")

    llm_stats = analyze_dir(llm_root)
    prodj_stats = analyze_dir(prodj_root)

    print_table("LLM GENERATED TESTS", llm_stats)
    print_table("ProDJ BASELINE TESTS", prodj_stats)
    compare_summary(llm_stats, prodj_stats)

    if args.llm_manifest:
        mf = json.loads(Path(args.llm_manifest).read_text())
        print(f"\n  LLM manifest: {mf.get('methodsSucceeded')}/{mf.get('methodsRequested')} methods succeeded")


if __name__ == "__main__":
    main()
