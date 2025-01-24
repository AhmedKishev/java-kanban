package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    public static Epic initialEpic;
    public static SubTask initialSubTask;


    @BeforeEach
    public void createEpicAndSubTask1() {
        initialEpic = new Epic("a", "b", Status.IN_PROGRESS);
        initialSubTask = new SubTask("c", "d", Status.NEW, initialEpic);
    }

    @Test
    public void notMatchesSubTask1AndSubTask2() {
        SubTask notSameSubTask = new SubTask("e", "f", Status.NEW, initialEpic);
        assertNotEquals(notSameSubTask, initialSubTask);
    }

    @Test
    public void matchesSubTask1AndSubTask2() {
        SubTask sameSubTask = new SubTask("c", "d", Status.NEW, initialEpic);
        assertEquals(sameSubTask, initialSubTask);
    }

    @Test
    public void SubTaskInConstructorSubTask() {
        SubTask subTaskWithoutEpic = new SubTask("a", "b", Status.NEW, initialSubTask);
        assertNull(subTaskWithoutEpic.getEpic());
    }
}