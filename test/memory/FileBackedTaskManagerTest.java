package memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import task.Epic;
import task.SubTask;
import task.Task;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileBackedTaskManagerTest {
    private Epic epicFromRead;
    private Task taskFromRead;
    private SubTask subTaskFromRead;
    private SubTask subTaskFromReadSecond;
    FileBackedTaskManager manager;

    @BeforeEach
    public void createTasks() {
        epicFromRead = new Epic("a", "b", Status.NEW);
        taskFromRead = new Task("b", "c", Status.NEW);
        subTaskFromRead = new SubTask("c", "d", Status.NEW, epicFromRead);
        subTaskFromReadSecond = new SubTask("e", "f", Status.NEW, epicFromRead);
        manager = new FileBackedTaskManager();


        epicFromRead.addSubTask(subTaskFromRead);
        epicFromRead.addSubTask(subTaskFromReadSecond);
        epicFromRead.addSubTask(subTaskFromRead);
        epicFromRead.addSubTask(subTaskFromReadSecond);
        manager.addTask(taskFromRead);
        manager.addSubTasks(subTaskFromRead);
        manager.addSubTasks(subTaskFromReadSecond);
        manager.addEpic(epicFromRead);
    }


    @Test
    public void saveToEmptyFile() {
        FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(new File("C:/Users/Astemir/IdeaProjects/java-kanban/src/filesForManager/Empty.txt"));

        assertTrue(manager.getAllTasks().size() == 0 && manager.getAllSubTasks().size() == 0 &&
                manager.getAllEpics().size() == 0);
    }

    @Test
    public void saveToFile() {
        FileBackedTaskManager manager = new FileBackedTaskManager();

        epicFromRead.addSubTask(subTaskFromRead);
        epicFromRead.addSubTask(subTaskFromReadSecond);
        manager.addTask(taskFromRead);
        manager.addSubTasks(subTaskFromRead);
        manager.addSubTasks(subTaskFromReadSecond);
        manager.addEpic(epicFromRead);
    }

    @Test
    public void readToFileTasks() {
        FileBackedTaskManager read = FileBackedTaskManager.loadFromFile(new File("C:/Users/Astemir/IdeaProjects/java-kanban/src/filesForManager/FileBackedTaskManager.txt"));
        ArrayList<Task> tasks = read.getAllTasks();

        assertTrue(tasks.get(0).getNameOfTask().equals(taskFromRead.getNameOfTask()) &&
                tasks.get(0).getDescription().equals(taskFromRead.getDescription()) &&
                tasks.get(0).getStatus().equals(taskFromRead.getStatus()));
    }

    @Test
    public void readToFileSubTasks() {
        FileBackedTaskManager read = FileBackedTaskManager.loadFromFile(new File("C:/Users/Astemir/IdeaProjects/java-kanban/src/filesForManager/FileBackedTaskManager.txt"));
        ArrayList<SubTask> subTasks = read.getAllSubTasks();


        assertTrue(subTasks.get(0).getNameOfTask().equals(subTaskFromRead.getNameOfTask()) &&
                subTasks.get(0).getDescription().equals(subTaskFromRead.getDescription()));
    }

    @Test
    public void readToFileEpic() {
        FileBackedTaskManager read = FileBackedTaskManager.loadFromFile(new File("C:/Users/Astemir/IdeaProjects/java-kanban/src/filesForManager/FileBackedTaskManager.txt"));
        ArrayList<Epic> epics = read.getAllEpics();

        assertTrue(epics.get(0).getNameOfTask().equals(epicFromRead.getNameOfTask()) &&
                epics.get(0).getDescription().equals(epicFromRead.getDescription()) &&
                epics.get(0).getStatus() == epicFromRead.getStatus());
    }

}
