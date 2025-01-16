package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    public static Epic original;

    @BeforeEach
    public void createEpic() {
        original = new Epic("a", "b", Status.NEW);
    }

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
    public void allSubTasksWithNewStatus() {
        SubTask subTaskWithStatusNew = new SubTask("a", "b", Status.NEW, original);
        SubTask subTaskWithStatusNew1 = new SubTask("a", "b", Status.NEW, original);
        original.addSubTask(subTaskWithStatusNew1);
        original.addSubTask(subTaskWithStatusNew);
        assertEquals(original.getStatus(), Status.NEW);
    }

    @Test
    public void allSubTasksWithDoneStatus() {
        SubTask subTaskWithStatusDone = new SubTask("a", "b", Status.DONE, original);
        SubTask subTaskWithStatusDone1 = new SubTask("a", "b", Status.DONE, original);
        original.addSubTask(subTaskWithStatusDone);
        original.addSubTask(subTaskWithStatusDone1);
        assertEquals(original.getStatus(), Status.DONE);
    }

    @Test
    public void allSubTasksWithNewAndDoneStatus() {
        SubTask subTaskWithStatusNew = new SubTask("a", "b", Status.NEW, original);
        SubTask subTaskWithStatusDone = new SubTask("a", "b", Status.DONE, original);
        original.addSubTask(subTaskWithStatusNew);
        original.addSubTask(subTaskWithStatusDone);
        assertEquals(original.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void allSubTasksWithInProgressStatus() {
        SubTask subTaskWithStatusInProgress = new SubTask("a", "b", Status.IN_PROGRESS, original);
        SubTask subTaskWithStatusInProgress1 = new SubTask("a", "b", Status.IN_PROGRESS, original);
        original.addSubTask(subTaskWithStatusInProgress);
        original.addSubTask(subTaskWithStatusInProgress1);
        assertEquals(original.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void addSubTaskError() {
        assertEquals("error", original.addSubTask(original));
    }
}