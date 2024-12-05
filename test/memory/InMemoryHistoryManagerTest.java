package memory;

import org.junit.jupiter.api.Test;
import status.Status;
import task.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    public void dataStorageAfterHistoryManager() {
        Task task = new Task("a", "b", Status.NEW);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        inMemoryHistoryManager.add(task);
        Task task1 = inMemoryHistoryManager.getHistory().get(0);
        assertTrue(task1.getDescription().equals(task.getDescription()) &&
                task1.getNameOfTask().equals(task.getNameOfTask()) &&
                task1.getStatus() == task.getStatus());
    }

    @Test
    public void correctSequenceOfTasks() {
        Task task = new Task("a", "b", Status.NEW);
        Task task1 = new Task("c", "d", Status.NEW);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task);
        ArrayList<Task> watchTasks = inMemoryHistoryManager.getHistory();
        assertTrue(task1.equals(watchTasks.get(0)) && task.equals(watchTasks.get(1)));
    }


    @Test
    public void equalsTaskAndElementHistoryManager() {
        Task task = new Task("a", "b", Status.NEW);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        inMemoryHistoryManager.add(task);
        assertTrue(inMemoryHistoryManager.getHistory().get(0).equals(task));
    }
}