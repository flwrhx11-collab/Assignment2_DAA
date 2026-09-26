# Assignment 2: Algorithmic Analysis, Correctness, and Performance Trade-offs

## 1. Project Structure
* `src/DynamicArray.java`: Dynamic array implementation based on a primitive `int[]` array.
* `src/LinkedList.java`: Singly linked list implementation.
* `src/MinHeap.java`: Binary min-heap implementation.
* `src/Tests.java`: Verification test suite matched against JDK standard collections.
* `src/Benchmark.java`: Execution benchmarking suite covering all 4 workloads.
* `results/`: Output data tables and generated charts.

---

## 2. Asymptotic Analysis

| Data Structure | Operation | Best Case | Average Case | Worst Case | Auxiliary Space |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Dynamic Array** | `add(x)` | $\Omega(1)$ | $\Theta(1)$ | $O(n)$ | $O(n)$ |
| **Dynamic Array** | `add(index, x)` | $\Omega(1)$ | $\Theta(n)$ | $O(n)$ | $O(1)$ |
| **Dynamic Array** | `remove(index)` | $\Omega(1)$ | $\Theta(n)$ | $O(n)$ | $O(1)$ |
| **Dynamic Array** | `get(index)` | $\Omega(1)$ | $\Theta(1)$ | $O(1)$ | $O(1)$ |
| **Dynamic Array** | `contains(x)` | $\Omega(1)$ | $\Theta(n)$ | $O(n)$ | $O(1)$ |
| **Linked List** | `add(x)` | $\Omega(1)$ | $\Theta(1)$ | $O(1)$ | $O(n)$ |
| **Linked List** | `add(index, x)` | $\Omega(1)$ | $\Theta(n)$ | $O(n)$ | $O(1)$ |
| **Linked List** | `remove(index)` | $\Omega(1)$ | $\Theta(n)$ | $O(n)$ | $O(1)$ |
| **Linked List** | `get(index)` | $\Omega(1)$ | $\Theta(n)$ | $O(n)$ | $O(1)$ |
| **Linked List** | `contains(x)` | $\Omega(1)$ | $\Theta(n)$ | $O(n)$ | $O(1)$ |
| **Min-Heap** | `insert(x)` | $\Omega(1)$ | $\Theta(\log n)$ | $O(\log n)$ | $O(n)$ |
| **Min-Heap** | `peekMin()` | $\Omega(1)$ | $\Theta(1)$ | $O(1)$ | $O(1)$ |
| **Min-Heap** | `extractMin()` | $\Omega(\log n)$ | $\Theta(\log n)$ | $O(\log n)$ | $O(1)$ |

### Justification:
1. **`get(index)`**: `DynamicArray` runs in $O(1)$ time due to direct memory offset calculation. `LinkedList` requires sequential pointer traversal from head to index, resulting in $\Theta(n)$.
2. **`add(0, x)` / `remove(0)`**: `LinkedList` performs reference updates in $O(1)$ time. `DynamicArray` requires shifting all $n$ remaining elements via `System.arraycopy`, yielding $\Theta(n)$.
3. **`Min-Heap`**: Insertions append elements to the end and call `heapifyUp`, requiring $O(\log n)$ in average/worst cases. `extractMin()` replaces the root with the last element and executes `heapifyDown`, always taking $O(\log n)$ operations.

---

## 3. Algorithmic Correctness (Loop Invariants)

### Operation 1: Dynamic Array - `contains(int x)`
* **Loop Invariant:** At the start of iteration $i$, target element $x$ is not present in subarray `data[0...i-1]`.
* **Initialization:** Before the first iteration ($i = 0$), the range `data[0...-1]` is empty. Thus, the invariant holds trivially.
* **Maintenance:** If the loop advances from $i$ to $i+1$, the condition `data[i] == x` evaluated to `false`. Since $x \notin data[0...i-1]$ and $data[i] \neq x$, it follows that $x \notin data[0...i]$. The invariant is maintained.
* **Termination:** The loop terminates either when `data[i] == x` (returning `true`) or when $i = size$.
* **Proof of Correctness:** If terminated because $i = size$, the invariant guarantees $x \notin data[0...size-1]$. Thus, the target element does not exist in the array, making `false` the correct return value.

### Operation 2: Min-Heap - `heapifyUp(int index)`
* **Loop Invariant:** The structure satisfies the min-heap property at all nodes except possibly between `heap[index]` and its parent `heap[(index-1)/2]`.
* **Initialization:** Insertion places the element at position `size - 1`. Because the heap was valid prior to insertion, the only potential violation is between the newly inserted element and its parent.
* **Maintenance:** During an iteration, if `heap[index] < heap[parent]`, the elements are swapped. After swapping, the min-heap property holds for subtree `index`, but a violation may move up to `parent`. The invariant holds for the updated index.
* **Termination:** The loop terminates when `index == 0` or when `heap[index] >= heap[parent]`.
* **Proof of Correctness:** Upon termination, no violation exists between the node and its parent, and the invariant guarantees no other violations exist. The min-heap property is fully restored.

---

## 4. Experimental Results

### Workload 1: Random Access (10,000 operations)
| $n$ | Array Avg Time (ns) | List Avg Time (ns) |
| :--- | :--- | :--- |
| 100 | 383,159 | 1,540,020 |
| 1,000 | 65,879 | 8,261,760 |
| 10,000 | 6,079 | 103,301,420 |
| 100,000 | 7,160 | 798,531,999 |

### Workload 2: Search (1,000 operations)
| $n$ | Array Avg Time (ns) | List Avg Time (ns) |
| :--- | :--- | :--- |
| 100 | 465,679 | 420,759 |
| 1,000 | 1,200,640 | 2,039,640 |
| 10,000 | 2,237,760 | 18,247,019 |
| 100,000 | 20,740,980 | 173,629,760 |

### Workload 3: Insertion & Removal (1,000 operations)
| $n$ | Operation | Array Avg Time (ns) | List Avg Time (ns) |
| :--- | :--- | :--- | :--- |
| **100** | Insert 0 | 170,419 | 71,420 |
| | Remove 0 | 376,220 | 54,640 |
| | Insert Mid | 112,040 | 206,200 |
| | Remove Mid | 98,839 | 192,260 |
| **1,000** | Insert 0 | 194,599 | 51,340 |
| | Remove 0 | 171,140 | 42,559 |
| | Insert Mid | 132,340 | 845,679 |
| | Remove Mid | 139,700 | 830,840 |
| **10,000** | Insert 0 | 1,017,420 | 21,260 |
| | Remove 0 | 778,079 | 7,620 |
| | Insert Mid | 511,460 | 8,106,500 |
| | Remove Mid | 353,080 | 7,932,860 |
| **100,000** | Insert 0 | 9,237,680 | 9,919 |
| | Remove 0 | 6,762,660 | 5,920 |
| | Insert Mid | 4,731,879 | 79,622,800 |
| | Remove Mid | 3,379,820 | 78,177,180 |

### Workload 4: Min-Heap Processing ($n$ elements)
| $n$ | Insert $n$ Time (ns) | Extract $n$ Time (ns) |
| :--- | :--- | :--- |
| 100 | 44,959 | 109,900 |
| 1,000 | 96,980 | 207,959 |
| 10,000 | 573,080 | 1,131,459 |
| 100,000 | 1,552,360 | 10,867,320 |

---

## 5. Performance and Design Analysis

1. **Random Access Trade-offs**: Empirical findings align with $O(1)$ vs $O(n)$ theory. At $n = 100,000$, `DynamicArray` executes in 7.1 $\mu s$ compared to 798.5 $ms$ for `LinkedList`. Performance is influenced not only by asymptotic complexity but also by CPU cache locality, as contiguous arrays utilize cache lines efficiently.
2. **In-place Operations**:
    * For operations at the head (`index = 0`), `LinkedList` executes in $O(1)$ time ($9.9\ \mu s$ at $n=100,000$), whereas `DynamicArray` takes $9.2\ ms$ due to memory relocation via `System.arraycopy`.
    * For middle operations (`index = n/2`), `DynamicArray` shifts half its contiguous buffer, outperforming `LinkedList`, which incurs sequential reference traversals across memory nodes ($n/2$ pointer jumps).
3. **Min-Heap Efficiency**: The binary heap demonstrates $O(n \log n)$ total scalability across $n$ insertions and extractions, requiring $10.8\ ms$ to extract 100,000 elements.

---

## 6. Design Recommendations
* **DynamicArray**: Recommended for workloads requiring frequent random index access and tail insertions.
* **LinkedList**: Recommended specifically when insertions and deletions occur strictly at boundaries (head/tail) without index-based lookups.
* **MinHeap**: Recommended for priority processing where finding and removing the minimum key must scale within $O(1)$ / $O(\log n)$ boundaries.