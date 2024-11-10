package memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import task.Epic;
import task.SubTask;
import task.Task;


import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private static InMemoryTaskManager inMemoryTaskManager;


    @BeforeEach
    public void createTaskManager() {
        inMemoryTaskManager=new InMemoryTaskManager();
    }

    @Test
    public void addTask() {
        Task task = new Task("a", "b", Status.NEW);
        inMemoryTaskManager.addTask(task);
        assertTrue(inMemoryTaskManager.getOfIdTask(task).equals(task));
    }

    @Test
    public void addSubTask() {
        Epic epic = new Epic("a", "b", Status.NEW);
        SubTask task = new SubTask("a", "b", Status.NEW, epic);

        inMemoryTaskManager.addSubTasks(task);
        assertTrue(inMemoryTaskManager.getOfIdSubTask(task).equals(task));
    }

    @Test
    public void addEpic() {
        Epic epic=new Epic("a","b",Status.NEW);

        inMemoryTaskManager.addEpic(epic);
        assertTrue(inMemoryTaskManager.getOfIdEpic(epic).equals(epic));
    }

    @Test
    public void immutabilityTask() {
        Task task=new Task("a","b",Status.NEW);

        inMemoryTaskManager.addTask(task);
        assertTrue(task.getNameOfTask().equals("a")&&
                task.getDescription().equals("b")&&task.getStatus()==Status.NEW);
    }

    @Test
    public void updateTask() {
        Task task=new Task("a","b",Status.NEW);

        inMemoryTaskManager.addTask(task);
        task.setStatus(Status.DONE);
        inMemoryTaskManager.updateTask(task);
        assertTrue(Status.DONE==inMemoryTaskManager.getOfIdTask(task).getStatus());
    }

    @Test
    public void updateSubTask() {
        Epic epic=new Epic("a","b",Status.NEW);
        SubTask subTask=new SubTask("a","b",Status.NEW,epic);

        inMemoryTaskManager.addSubTasks(subTask);
        subTask.setStatus(Status.DONE);
        inMemoryTaskManager.updateTask(subTask);
        assertTrue(Status.DONE==inMemoryTaskManager.getOfIdTask(subTask).getStatus());
    }

    @Test
    public void updateEpic() {
        Epic epic=new Epic("a","b",Status.NEW);

        inMemoryTaskManager.addEpic(epic);
        epic.setStatus(Status.DONE);
        inMemoryTaskManager.updateTask(epic);
        assertTrue(Status.DONE==inMemoryTaskManager.getOfIdTask(epic).getStatus());
    }

}