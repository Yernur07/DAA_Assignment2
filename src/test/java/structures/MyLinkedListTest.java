package structures;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class MyLinkedListTest {
    @Test
    void addAndGet() {
        MyLinkedList list = new MyLinkedList();
        list.add(10);
        list.add(20);
        list.add(30);
        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }
    @Test
    void addByIndex() {
        MyLinkedList list = new MyLinkedList();
        list.add(10);
        list.add(30);
        list.add(1, 20);
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }
    @Test
    void remove() {
        MyLinkedList list = new MyLinkedList();
        list.add(10);
        list.add(20);
        list.add(30);
        assertEquals(20, list.remove(1));
        assertEquals(2, list.size());
        assertEquals(30, list.get(1));
    }
    @Test
    void contains() {
        MyLinkedList list = new MyLinkedList();
        list.add(10);
        list.add(20);
        assertTrue(list.contains(20));
        assertFalse(list.contains(50));
    }
    @Test
    void invalidIndex() {
        MyLinkedList list = new MyLinkedList();
        list.add(10);
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(5)
        );
    }
}