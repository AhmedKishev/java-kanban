package taskmanager;

import task.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Long, Task> tasks = new HashMap<>();
    private HashMap<Long, Epic> epics = new HashMap<>();
    private HashMap<Long, SubTask> subTasks = new HashMap<>();
    private final static String WARNING = "Задачи с таким номером нету";


    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void addSubTasks(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> temp = new ArrayList<>();
        for (Task task : tasks.values()) {
            temp.add(task);
        }
        return temp;
    }

    public ArrayList<SubTask> getAllSubTasks() {
        ArrayList<SubTask> temp = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            temp.add(subTask);
        }
        return temp;
    }

    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> temp = new ArrayList<>();
        for (Epic epic : epics.values()) {
            temp.add(epic);
        }
        return temp;
    }

    public Task getOfIdTask(Task t) {
        long id=t.getId();
        for (Task task : tasks.values()) {
            if (task.getId() == id) return task;
        }
        System.out.println(WARNING);
        return null;
    }

    public SubTask getOfIdSubTask(SubTask s) {
        long id=s.getId();
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getId() == id) return subTask;
        }
        System.out.println(WARNING);
        return null;
    }

    public Epic getOfIdEpic(Epic e) {
        long id=e.getId();
        for (Epic epic : epics.values()) {
            if (epic.getId() == id) return epic;
        }
        System.out.println(WARNING);
        return null;
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        subTask.getEpic().statusCheck();
    }

    public void updateEpic(Epic epic) {
        tasks.put(epic.getId(), epic);
        epic.statusCheck();
    }

    public void deleteOfIdTask(Task t) {
        long id = t.getId();
        for (Task task : tasks.values()) {
            if (task.getId() == id) tasks.remove(id);
        }
    }

    public void deleteOfIdSubTask(SubTask s) {
        long id = s.getId();
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getId() == id) {
                subTask.getEpic().getSubTasks().remove(subTask);
                subTask.getEpic().statusCheck();
                subTasks.remove(id);
                return;
            }
        }

    }

    public void deleteOfIdEpic(Epic e) {
        long id = e.getId();
        for (Epic epic : epics.values()) {
            if (epic.getId() == id) {
                if (epic.getSubTasks().isEmpty()) epics.remove(id);
                else System.out.println("В эпике есть сабтаски");
            }
        }
    }

    public void deleteOfAllTasks() {
        tasks.clear();
    }

    public void deleteOfAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void deleteOfAllSubTasks() {
        deleteOfAllEpics();
    }

    public ArrayList<SubTask> getAllSubTasksOfEpic(Epic e) {
        long id=e.getId();
        for (Epic epic : epics.values()) {
            if (epic.getId() == id) return epic.getSubTasks();
        }
        System.out.println(WARNING);
        return null;
    }

}
