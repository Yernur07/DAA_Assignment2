package structures;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DynamicArrayTest {
    @Test
    void testAddAndGet() {
        DynamicArray array = new DynamicArray();
        array.add(10);
        array.add(20);
        array.add(30);
        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }
    @Test
    void testAddByIndex() {
        DynamicArray array = new DynamicArray();
        array.add(10);
        array.add(30);
        array.add(1, 20);
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }
    @Test
    void testRemove() {
        DynamicArray array = new DynamicArray();
        array.add(10);
        array.add(20);
        array.add(30);
        int removed = array.remove(1);
        assertEquals(20, removed);
        assertEquals(2, array.size());
        assertEquals(30, array.get(1));
    }
    @Test
    void testContains() {
        DynamicArray array = new DynamicArray();
        array.add(10);
        array.add(20);
        array.add(30);
        assertTrue(array.contains(20));
        assertFalse(array.contains(50));
    }
    @Test
    void testInvalidIndex() {
        DynamicArray array = new DynamicArray();
        array.add(10);
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(5)
        );
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.remove(5)
        );
    }
}
