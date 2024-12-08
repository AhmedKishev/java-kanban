package memory;

import org.junit.jupiter.api.Test;
import status.Status;
import task.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    public void dataStorageAfterHistoryManager() {
        Task original = new Task("a", "b", Status.NEW);
        InMemoryHistoryManager history = new InMemoryHistoryManager();

        history.add(original);
        Task copy = history.getHistory().get(0);
        assertTrue(copy.getDescription().equals(original.getDescription()) &&
                copy.getNameOfTask().equals(original.getNameOfTask()) &&
                copy.getStatus() == original.getStatus());
    }

    @Test
    public void correctSequenceOfTasks() {
        Task watch = new Task("a", "b", Status.NEW);
        Task watch1 = new Task("c", "d", Status.NEW);
        InMemoryHistoryManager history = new InMemoryHistoryManager();

        history.add(watch);
        history.add(watch1);
        history.add(watch);
        ArrayList<Task> watchTasks = history.getHistory();
        assertTrue(watch1.equals(watchTasks.get(0)) && watch.equals(watchTasks.get(1)));
    }


    @Test
    public void equalsTaskAndElementHistoryManager() {
        Task original = new Task("a", "b", Status.NEW);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        inMemoryHistoryManager.add(original);
        assertTrue(inMemoryHistoryManager.getHistory().get(0).equals(original));
    }
}