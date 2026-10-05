package structures;
public class DynamicArray implements IntList {
    private int[] data;
    private int size;
    private long steps;
    private long moves;
    private long comparisons;
    public DynamicArray() {
        data = new int[10];
        size = 0;
    }
    public void add(int x) {
        if (size == data.length) {
            resize();
        }
        data[size] = x;
        moves++;
        size++;
    }
    public void add(int index, int x) {
        checkIndexForAdd(index);
        if (size == data.length) {
            resize();
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            steps++;
            moves++;
        }
        data[index] = x;
        moves++;
        size++;
    }
    public int remove(int index) {
        checkIndex(index);
        int removed = data[index];
        steps++;
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            steps++;
            moves++;
        }
        size--;
        return removed;
    }
    public int get(int index) {
        checkIndex(index);
        steps++;
        return data[index];
    }
    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            steps++;
            comparisons++;
            if (data[i] == x) {
                return true;
            }
        }
        return false;
    }
    private void resize() {
        int[] newData = new int[data.length * 2];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
            steps++;
            moves++;
        }
        data = newData;
    }
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
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