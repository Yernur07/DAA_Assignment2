package structures;
public class MyLinkedList implements IntList {
    private static class Node {
        int value;
        Node next;
        Node(int value) {
            this.value = value;
        }
    }
    private Node head;
    private Node tail;
    private int size;
    private long steps;
    private long moves;
    private long comparisons;
    @Override
    public void add(int x) {
        Node newNode = new Node(x);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            moves++;
            tail = newNode;
            moves++;
        }
        size++;
    }
    @Override
    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(x);
            return;
        }
        if (index == 0) {
            Node newNode = new Node(x);
            newNode.next = head;
            moves++;
            head = newNode;
            moves++;
            size++;
            return;
        }
        Node previous = getNode(index - 1);
        Node newNode = new Node(x);
        newNode.next = previous.next;
        moves++;
        previous.next = newNode;
        moves++;
        size++;
    }
    @Override
    public int remove(int index) {
        checkIndex(index);
        if (index == 0) {
            int removed = head.value;
            steps++;
            head = head.next;
            moves++;
            size--;
            if (size == 0) {
                tail = null;
                moves++;
            }
            return removed;
        }
        Node previous = getNode(index - 1);
        Node removedNode = previous.next;
        int removed = removedNode.value;
        steps++;
        previous.next = removedNode.next;
        moves++;
        if (index == size - 1) {
            tail = previous;
            moves++;
        }
        size--;
        return removed;
    }
    @Override
    public int get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }
    private Node getNode(int index) {
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
            steps++;
        }
        return current;
    }
    @Override
    public boolean contains(int x) {
        Node current = head;
        while (current != null) {
            steps++;
            comparisons++;
            if (current.value == x) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public long getSteps() {
        return steps;
    }
    @Override
    public long getMoves() {
        return moves;
    }
    @Override
    public long getComparisons() {
        return comparisons;
    }
    @Override
    public void resetCounters() {

        steps = 0;
        moves = 0;
        comparisons = 0;
    }
}
