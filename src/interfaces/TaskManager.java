package interfaces;

import task.Epic;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {

    boolean addTask(Task task);

    boolean addEpic(Epic epic);

    HistoryManager getHistoryManager();

    boolean addSubTasks(SubTask subTask);

    List<Task> getAllTasks();

    List<SubTask> getAllSubTasks();

    List<Epic> getAllEpics();

    Task getOfIdTask(Task t);

    SubTask getOfIdSubTask(SubTask s);

    Epic getOfIdEpic(Epic e);

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void updateEpic(Epic epic);

    void deleteOfIdTask(Task t);

    void deleteOfIdSubTask(SubTask s);

    boolean intersectionOfTime(Task task);

    void deleteOfIdEpic(Epic e);

    TreeSet<Task> getPrioritizedTasks();

    void deleteOfAllTasks();

    void deleteOfAllEpics();

    void deleteOfAllSubTasks();

    ArrayList<SubTask> getAllSubTasksOfEpic(Epic e);
}
