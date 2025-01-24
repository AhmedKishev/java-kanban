package task;

import org.junit.jupiter.api.Test;
import status.Status;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    public static Epic original = new Epic("a", "b", Status.NEW);


    @Test
    public void notMatchesEpic1AndEpic2() {
        Epic comparable = new Epic("c", "d", Status.NEW);
        assertFalse(comparable.equals(original));
    }

    @Test
    public void matchesEpic1AndEpic2() {
        Epic comparable = new Epic("a", "b", Status.NEW);
        assertTrue(comparable.equals(original));
    }

    @Test
    public void addSubTaskNotError() {
        SubTask comparable = new SubTask("a", "b", Status.NEW, original);
        assertEquals("not error", original.addSubTask(comparable));
    }

    @Test
    public void addSubTaskError() {
        assertEquals("error", original.addSubTask(original));
    }
}