# DAA Assignment 2 - Data Structures

This project implements the three data structures required by Assignment 2:

- `DynamicArray`
- `MyLinkedList`
- `MinHeap`

The project also contains JUnit 5 tests, operation counters, the four benchmark workloads, CSV export, and a plotting script.

## Requirements

- Java 17 or newer
- Maven 3.9+ or the Maven Wrapper included by IntelliJ/Maven
- Python 3.x + pandas + matplotlib for plots

## Project structure

```text
DAA_Assignment2/
├── src/
│   ├── main/java/
│   │   ├── benchmark/
│   │   ├── metrics/
│   │   └── structures/
│   └── test/java/structures/
├── results/
│   ├── results.csv
│   └── plots/
├── pom.xml
├── README.md
├── REPORT.md
└── plot_results.py
```

## Build and test

From the project root:

```bash
mvn clean test
```

On Windows, if the Maven Wrapper is available:

```bat
mvnw.cmd clean test
```

## Run benchmark

Run `benchmark.BenchmarkMain` from IntelliJ, or use Maven after compiling:

```bash
mvn -q compile
java -cp target/classes benchmark.BenchmarkMain
```

The benchmark creates:

```text
results/results.csv
```

The benchmark uses:

- `n = 100, 1000, 10000, 100000`
- `new Random(42)`
- one warm-up run
- five measured runs
- median measured time

## Workloads

### W1 - Random Access

DynamicArray and MyLinkedList are filled with `n` values and then perform 10,000 `get(index)` calls.

### W2 - Search

DynamicArray and MyLinkedList perform 1,000 `contains(x)` queries: 500 present values and 500 absent values.

### W3 - Insert & Remove

Both list structures perform 1,000 insertions followed by 1,000 removals.

Two variants are measured:

- `head`: index `0`
- `middle`: index `n / 2`

### W4 - Priority Processing

MinHeap inserts `n` values and then extracts the minimum `n` times. The output is checked to be non-decreasing.

## Metrics

The structures count:

- `steps`
- `moves`
- `comparisons`

The counters are maintained inside the data-structure operations.

## Generate plots

Install dependencies once:

```bash
pip install pandas matplotlib
```

Then run:

```bash
python plot_results.py
```

PNG files are written to `results/plots/`.

## Git workflow

Required branches:

```text
main
feature/array
feature/list
feature/heap
feature/metrics
```

Final release tag:

```text
v1.0
```

## Defense

Be able to explain every submitted line, especially:

- dynamic resizing;
- linked-list traversal and pointer updates;
- heap bubble-up and bubble-down;
- operation counters;
- loop invariants;
- Big-O / Omega / Theta complexity;
- cache locality and pointer chasing;
- benchmark methodology.
