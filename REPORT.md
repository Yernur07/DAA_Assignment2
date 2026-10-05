# DAA Assignment 2 - Data Structures Report

**Student:** ____________________  
**Group:** ____________________  
**Course:** Design and Analysis of Algorithms  
**Assignment:** 2 - Data Structures

> Replace the placeholders in this report after running the benchmark. Insert the PNG charts from `results/plots/` in the Plots section and add the GitHub repository link.

## 1. Implemented Data Structures

This project implements three data structures from scratch using primitive `int` storage:

- `DynamicArray` with doubling resize;
- `MyLinkedList` as a singly linked list with a tail pointer;
- `MinHeap` as an array-based binary min-heap.

The implementation does not use `java.util.ArrayList`, `java.util.LinkedList`, or `java.util.PriorityQueue` inside the custom structures.

## 2. Complexity Analysis

| Structure | Operation | Best | Average | Worst | Auxiliary Space | Justification |
|---|---|---:|---:|---:|---:|---|
| DynamicArray | `add(x)` | Θ(1) | Θ(1) amortized | O(n) | Θ(n) | Normal append writes one cell; a full array requires copying to a doubled array. |
| DynamicArray | `add(index,x)` | Θ(1) | Θ(n) | Θ(n) | Θ(n) | Insertion at the end needs no shifts; insertion before existing elements shifts them. |
| DynamicArray | `remove(index)` | Θ(1) | Θ(n) | Θ(n) | Θ(n) | Removing the last element needs no shift; earlier positions require shifting following elements. |
| DynamicArray | `get(index)` | Θ(1) | Θ(1) | Θ(1) | Θ(n) | Direct array indexing. |
| DynamicArray | `contains(x)` | Θ(1) | Θ(n) | Θ(n) | Θ(n) | Search may stop at the first position or inspect the whole array. |
| MyLinkedList | `add(x)` | Θ(1) | Θ(1) | Θ(1) | Θ(n) | A tail pointer allows append without traversal. |
| MyLinkedList | `add(index,x)` | Θ(1) | Θ(n) | Θ(n) | Θ(n) | Index 0 is immediate; other positions require traversal. |
| MyLinkedList | `remove(index)` | Θ(1) | Θ(n) | Θ(n) | Θ(n) | Removing the head is immediate; other positions require traversal. |
| MyLinkedList | `get(index)` | Θ(1) | Θ(n) | Θ(n) | Θ(n) | The list must follow links from the head to the requested position. |
| MyLinkedList | `contains(x)` | Θ(1) | Θ(n) | Θ(n) | Θ(n) | The value may be at the first node or absent/at the end. |
| MinHeap | `insert(x)` | Θ(1) | O(log n) | O(log n) | Θ(n) | The new value is placed at the end and can bubble up through the heap height. |
| MinHeap | `peekMin()` | Θ(1) | Θ(1) | Θ(1) | Θ(n) | The minimum is always at the root. |
| MinHeap | `extractMin()` | Θ(log n) | O(log n) | O(log n) | Θ(n) | Replacing the root can require bubble-down along the heap height. |

## 3. Loop Invariant Proofs

### 3.1 `MyLinkedList.remove(index)` - traversal loop

Loop:

java
Node previous = head;
for (int i = 0; i < index - 1; i++) {
    previous = previous.next;
}

**Invariant:** Before every iteration with counter `i`, `previous` points to the node at list position `i`.

**Initialization:** Before the first iteration, `i = 0` and `previous = head`. The head is position 0, so the invariant is true.

**Maintenance:** If the invariant is true before an iteration, `previous` is at position `i`. The statement `previous = previous.next` moves it to position `i + 1`. Therefore, before the next iteration the invariant is still true.

**Termination:** The loop stops when `i = index - 1`. Therefore, `previous` points to the node immediately before the node being removed.

**Conclusion:** The operation can correctly save the target node and update `previous.next` to skip that node. Thus the requested element is removed while the remaining list stays connected.

### 3.2 `DynamicArray.contains(x)` - search loop

Loop:

java
for (int i = 0; i < size; i++) {
    if (data[i] == x) {
        return true;
    }
}


**Invariant:** Before each iteration at index `i`, every element at positions `0` through `i - 1` has already been compared with `x`, and none of those elements equals `x`.

**Initialization:** Before the first iteration, `i = 0`, so no elements have been checked. The invariant is true vacuously.

**Maintenance:** If `data[i] == x`, the method immediately returns `true`, which is correct. Otherwise, position `i` has been checked and is not equal to `x`; therefore, before the next iteration all positions `0` through `i` have been checked and none equals `x`.

**Termination:** If the loop finishes, every position from `0` through `size - 1` has been checked and none equals `x`. Returning `false` is therefore correct.

**Conclusion:** The invariant establishes that `contains(x)` returns `true` exactly when the value exists in the structure.

## 4. Benchmark Methodology

The benchmark uses the required sizes:

- 100
- 1,000
- 10,000
- 100,000

The input data is generated reproducibly with `new Random(42)`. Each case has one warm-up run and five measured runs. The median of the five measured times is stored in `results/results.csv`.

The CSV columns are:

```text
workload,variant,structure,n,time_ms,steps,moves,comparisons
```

## 5. Workloads

### W1 - Random Access

DynamicArray and MyLinkedList are filled with `n` values. Each then performs 10,000 `get(index)` calls using the same random indices.

### W2 - Search

DynamicArray and MyLinkedList perform 1,000 `contains(x)` queries. Half of the queries use values present in the structure and half use absent values.

### W3 - Insert & Remove

Two variants are measured:

- `head`: 1,000 insertions and 1,000 removals at index 0;
- `middle`: 1,000 insertions and 1,000 removals at index `n / 2`.

### W4 - Priority Processing

MinHeap inserts `n` values and then calls `extractMin()` `n` times. The benchmark checks that extracted values are in non-decreasing order.

## 6. Plots

After running `plot_results.py`, insert the generated PNG files here.

### W1 - Random Access

Insert `W1_time.png` and `W1_operations.png`.

### W2 - Search

Insert `W2_time.png` and `W2_operations.png`.

### W3 - Insert & Remove

Insert the head and middle time/operation charts.

### W4 - Priority Processing

Insert `W4_time.png` and `W4_operations.png`.

## 7. Discussion

The DynamicArray is expected to be faster for `get(i)` because array indexing directly calculates the target memory location, while MyLinkedList has to follow links from the head until it reaches the requested node. Arrays also have better spatial locality: nearby elements are stored next to each other, so CPU cache lines can bring several useful values into cache at once. A linked list stores nodes separately, so consecutive nodes may be located far apart in memory. This pointer chasing makes the processor wait for memory more often. The linked list can therefore be slower even when a theoretical operation count looks similar. DynamicArray insertion or removal in the middle is expensive because many elements have to be shifted. MyLinkedList can change links without shifting all following elements, but it still needs to traverse the list to find the position. For head insertion and removal, MyLinkedList can be efficient because the head is immediately available. MinHeap is the appropriate structure for priority processing because the minimum value is available at the root and `extractMin()` restores the heap using bubble-down. The heap therefore supports repeated minimum extraction in logarithmic time. The benchmark results should be used to discuss the exact differences observed on the test machine.

**After the benchmark:** replace this section with the exact observations from `results.csv`, including which structure was faster for each workload and how the operation counts changed with `n`.

## 8. Conclusion

The project implements the required custom data structures, correctness tests, physical operation counters, benchmark workloads, CSV export, and plots. The final results demonstrate why asymptotic complexity is important but does not completely determine real running time: memory layout, cache locality, pointer chasing, object overhead, and JVM behavior also influence performance.

## 9. GitHub

Repository: ______________________________

Main branch: `main`

Release tag: `v1.0`

Required feature branches:

- `feature/array`
- `feature/list`
- `feature/heap`
- `feature/metrics`
