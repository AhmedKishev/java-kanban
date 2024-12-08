package memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import task.Epic;
import task.SubTask;
import task.Task;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private static InMemoryTaskManager taskManager;


    @BeforeEach
    public void createTaskManager() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void addTask() {
        Task addedTask = new Task("a", "b", Status.NEW);
        taskManager.addTask(addedTask);
        assertTrue(taskManager.getOfIdTask(addedTask).equals(addedTask));
    }

    @Test
    public void addSubTask() {
        Epic epicForAddedSubTask = new Epic("a", "b", Status.NEW);
        SubTask addedSubTask = new SubTask("a", "b", Status.NEW, epicForAddedSubTask);

        taskManager.addSubTasks(addedSubTask);
        assertTrue(taskManager.getOfIdSubTask(addedSubTask).equals(addedSubTask));
    }

    @Test
    public void SubTaskIdToBeDeleted() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic forSubTaskId = new Epic("a", "b", Status.NEW);
        SubTask deleteSubTaskId = new SubTask("a", "b", Status.NEW, forSubTaskId);
        taskManager.addSubTasks(deleteSubTaskId);
        taskManager.deleteOfIdSubTask(deleteSubTaskId);


        assertNull(taskManager.getOfIdSubTask(deleteSubTaskId));
    }

    @Test
    public void notActualSubTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic forSubTask = new Epic("a", "b", Status.NEW);
        SubTask subTask = new SubTask("a", "b", Status.NEW, forSubTask);

        forSubTask.addSubTask(subTask);
        taskManager.addSubTasks(subTask);
        taskManager.addEpic(forSubTask);
        taskManager.deleteOfIdSubTask(subTask);
        List<Epic> emptyEpics = taskManager.getAllEpics();
        assertTrue(emptyEpics.get(0).getSubTasks().isEmpty());
    }

    @Test
    public void addEpic() {
        Epic add = new Epic("a", "b", Status.NEW);

        taskManager.addEpic(add);
        assertTrue(taskManager.getOfIdEpic(add).equals(add));
    }

    @Test
    public void immutabilityTask() {
        Task task = new Task("a", "b", Status.NEW);

        taskManager.addTask(task);
        assertTrue(task.getNameOfTask().equals("a") &&
                task.getDescription().equals("b") && task.getStatus() == Status.NEW);
    }

    @Test
    public void updateTask() {
        Task update = new Task("a", "b", Status.NEW);

        taskManager.addTask(update);
        update.setStatus(Status.DONE);
        taskManager.updateTask(update);
        assertTrue(Status.DONE == taskManager.getOfIdTask(update).getStatus());
    }

    @Test
    public void updateSubTask() {
        Epic forSubTask = new Epic("a", "b", Status.NEW);
        SubTask update = new SubTask("a", "b", Status.NEW, forSubTask);

        taskManager.addSubTasks(update);
        update.setStatus(Status.DONE);
        taskManager.updateTask(update);
        assertTrue(Status.DONE == taskManager.getOfIdTask(update).getStatus());
    }

    @Test
    public void updateEpic() {
        Epic update = new Epic("a", "b", Status.NEW);

        taskManager.addEpic(update);
        update.setStatus(Status.DONE);
        taskManager.updateTask(update);
        assertTrue(Status.DONE == taskManager.getOfIdTask(update).getStatus());
    }

}