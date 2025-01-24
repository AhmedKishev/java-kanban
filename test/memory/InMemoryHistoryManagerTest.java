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
    public void EmptyHistory() {
        Task notWatch = new Task("a", "b", Status.NEW);
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        inMemoryTaskManager.addTask(notWatch);
        assertNull(inMemoryTaskManager.getHistoryManager().getHistory());
    }

    @Test
    public void deleteInHistoryStart() {
        Task watch = new Task("a", "b", Status.NEW);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        inMemoryHistoryManager.add(watch);
        Node node = new Node(watch);
        inMemoryHistoryManager.remove(watch.getId());
        inMemoryHistoryManager.removeNode(node);
        assertNull(inMemoryHistoryManager.getHistory());
    }


    @Test
    public void deleteInHistoryInMid() {
        Task watch = new Task("a", "b", Status.NEW);
        Task watch1 = new Task("c", "d", Status.NEW);
        Task watch2 = new Task("e", "f", Status.NEW);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        inMemoryHistoryManager.add(watch);
        inMemoryHistoryManager.add(watch1);
        inMemoryHistoryManager.add(watch2);
        Node node = new Node(watch1);
        inMemoryHistoryManager.remove(watch1.getId());
        inMemoryHistoryManager.removeNode(node);
        assertEquals(2, inMemoryHistoryManager.getHistory().size());
    }

    @Test
    public void deleteInHistoryInTail() {
        Task watch = new Task("a", "b", Status.NEW);
        Task watch1 = new Task("c", "d", Status.NEW);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        inMemoryHistoryManager.add(watch);
        inMemoryHistoryManager.add(watch1);
        inMemoryHistoryManager.remove(watch1.getId());
        Node node = new Node(watch1);
        inMemoryHistoryManager.removeNode(node);
        assertTrue(inMemoryHistoryManager.getHistory().get(0).equals(watch) &&
                inMemoryHistoryManager.getHistory().size() == 1);
    }

    @Test
    public void equalsTaskAndElementHistoryManager() {
        Task original = new Task("a", "b", Status.NEW);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        inMemoryHistoryManager.add(original);
        assertTrue(inMemoryHistoryManager.getHistory().get(0).equals(original));
    }
}