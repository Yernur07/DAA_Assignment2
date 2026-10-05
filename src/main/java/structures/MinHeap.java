package structures;
public class MinHeap {
    private int[] heap;
    private int size;
    private long steps;
    private long moves;
    private long comparisons;
    public MinHeap() {
        heap = new int[10];
        size = 0;
    }
    public void insert(int x) {
        if (size == heap.length) {
            resize();
        }
        heap[size] = x;
        moves++;
        int index = size;
        size++;
        bubbleUp(index);
    }
    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        steps++;
        return heap[0];
    }
    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        int result = heap[0];
        steps++;
        size--;
        if (size > 0) {
            heap[0] = heap[size];
            moves++;
            bubbleDown(0);
        }
        return result;
    }
    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            steps++;
            comparisons++;
            if (heap[parent] <= heap[index]) {
                break;
            }
            swap(parent, index);
            index = parent;
        }
    }
    private void bubbleDown(int index) {
        while (true) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smallest = index;
            if (left < size) {
                steps++;
                comparisons++;
                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }
            if (right < size) {
                steps++;
                comparisons++;
                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }
            if (smallest == index) {
                break;
            }
            swap(index, smallest);
            index = smallest;
        }
    }
    private void swap(int i, int j) {
        int temp = heap[i];
        steps++;
        heap[i] = heap[j];
        moves++;
        heap[j] = temp;
        moves++;
    }
    private void resize() {
        int[] newHeap = new int[heap.length * 2];
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
            steps++;
            moves++;
        }
        heap = newHeap;
    }
    public int size() {
        return size;
    }
    public long getSteps() {
        return steps;
    }
    public long getMoves() {
        return moves;
    }
    public long getComparisons() {
        return comparisons;
    }
    public void resetCounters() {
        steps = 0;
        moves = 0;
        comparisons = 0;
    }
}
