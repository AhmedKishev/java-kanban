package interfaceManager;

import historyManagerInterface.HistoryManager;
import task.Epic;
import task.SubTask;
import task.Task;

import java.util.ArrayList;

public interface TaskManager {
    void addTask(Task task);
    void addEpic(Epic epic);
    HistoryManager getHistoryManager();
    void addSubTasks(SubTask subTask);

    ArrayList<Task> getAllTasks();

    ArrayList<SubTask> getAllSubTasks();

    ArrayList<Epic> getAllEpics();

    Task getOfIdTask(Task t);

    SubTask getOfIdSubTask(SubTask s);

    Epic getOfIdEpic(Epic e);

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void updateEpic(Epic epic);

    void deleteOfIdTask(Task t);

    void deleteOfIdSubTask(SubTask s);

    void deleteOfIdEpic(Epic e);

    void deleteOfAllTasks();

    void deleteOfAllEpics();

    void deleteOfAllSubTasks();

    ArrayList<SubTask> getAllSubTasksOfEpic(Epic e);
}
