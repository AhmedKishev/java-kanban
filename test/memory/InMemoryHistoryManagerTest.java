package memory;

import org.junit.jupiter.api.Test;
import status.Status;
import task.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    public void dataStorageAfteHistoryManager() {
        Task task = new Task("a", "b", Status.NEW);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        inMemoryHistoryManager.add(task);
        Task task1 = inMemoryHistoryManager.getHistory().get(0);
        assertTrue(task1.getDescription().equals(task.getDescription()) &&
                task1.getNameOfTask().equals(task.getNameOfTask()) &&
                task1.getStatus() == task.getStatus());
    }

    @Test
    public void equalsTaskAndElementHistoryManager() {
        Task task = new Task("a", "b", Status.NEW);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        inMemoryHistoryManager.add(task);
        assertTrue(inMemoryHistoryManager.getHistory().get(0).equals(task));
    }
}