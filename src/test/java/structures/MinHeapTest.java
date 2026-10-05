package structures;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class MinHeapTest {
    @Test
    void insertAndPeek() {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(5);
        heap.insert(7);
        heap.insert(2);
        assertEquals(2, heap.peekMin());
    }
    @Test
    void extractInSortedOrder() {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(5);
        heap.insert(7);
        heap.insert(2);
        assertEquals(2, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(7, heap.extractMin());
        assertEquals(10, heap.extractMin());
    }
    @Test
    void emptyHeap() {
        MinHeap heap = new MinHeap();
        assertThrows(
                IllegalStateException.class,
                heap::peekMin
        );
        assertThrows(
                IllegalStateException.class,
                heap::extractMin
        );
    }
    @Test
    void duplicates() {
        MinHeap heap = new MinHeap();
        heap.insert(5);
        heap.insert(5);
        heap.insert(2);
        assertEquals(2, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
    }
}