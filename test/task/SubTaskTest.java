package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    public static Epic originalEpic;
    public static SubTask originalSub;


    @BeforeEach
    public void createEpicAndSubTask1() {
        originalEpic = new Epic("a", "b", Status.IN_PROGRESS);
        originalSub = new SubTask("c", "d", Status.NEW, originalEpic);
    }

    @Test
    public void notMatchesSubTask1AndSubTask2() {
        SubTask comparable = new SubTask("e", "f", Status.NEW, originalEpic);
        assertFalse(comparable.equals(originalSub));
    }

    @Test
    public void matchesSubTask1AndSubTask2() {
        SubTask comparable = new SubTask("c", "d", Status.NEW, originalEpic);
        assertTrue(comparable.equals(originalSub));
    }

    @Test
    public void SubTaskInConstuctorSubTask() {
        SubTask subTask1 = new SubTask("a", "b", Status.NEW, originalSub);
        assertNull(subTask1.getEpic());
    }
}