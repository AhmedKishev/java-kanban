package memoryManager;

import historyManagerInterface.HistoryManager;
import interfaceManager.TaskManager;
import task.Epic;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager <T extends Task> implements TaskManager{
    private HashMap<Long, Task> tasks = new HashMap<>();
    private HashMap<Long, Epic> epics = new HashMap<>();
    private HashMap<Long, SubTask> subTasks = new HashMap<>();
    private HistoryManager historyManager=new InMemoryHistoryManager();
    private final static String WARNING = "Задачи с таким номером нету";


    @Override
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public HistoryManager getHistoryManager() {
        return this.historyManager;
    }


    @Override
    public void addSubTasks(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> temp = new ArrayList<>();
        for (Task task : tasks.values()) {
            temp.add(task);
        }
        return temp;
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        ArrayList<SubTask> temp = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            temp.add(subTask);
        }
        return temp;
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> temp = new ArrayList<>();
        for (Epic epic : epics.values()) {
            temp.add(epic);
        }
        return temp;
    }

    @Override
    public Task getOfIdTask(Task t) {
        long id=t.getId();
        for (Task task : tasks.values()) {
            if (task.getId() == id) {
                historyManager.add(task);
                return task;
            }
        }
        System.out.println(WARNING);
        return null;
    }

    @Override
    public SubTask getOfIdSubTask(SubTask s) {
        long id=s.getId();
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getId() == id) {
                historyManager.add(subTask);
                return subTask;
            }
        }
        System.out.println(WARNING);
        return null;
    }

    @Override
    public Epic getOfIdEpic(Epic e) {
        long id=e.getId();
        for (Epic epic : epics.values()) {
            if (epic.getId() == id) {
                historyManager.add(epic);
                return epic;
            }
        }
        System.out.println(WARNING);
        return null;
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        subTask.getEpic().statusCheck();
    }

    @Override
    public void updateEpic(Epic epic) {
        tasks.put(epic.getId(), epic);
        epic.statusCheck();
    }

    @Override
    public void deleteOfIdTask(Task t) {
        long id = t.getId();
        for (Task task : tasks.values()) {
            if (task.getId() == id) tasks.remove(id);
        }
    }

    @Override
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

    @Override
    public void deleteOfIdEpic(Epic e) {
        long id = e.getId();
        for (Epic epic : epics.values()) {
            if (epic.getId() == id) {
                if (epic.getSubTasks().isEmpty()) epics.remove(id);
                else System.out.println("В эпике есть сабтаски");
            }
        }
    }

    @Override
    public void deleteOfAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteOfAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void deleteOfAllSubTasks() {
        deleteOfAllEpics();
    }

    @Override
    public ArrayList<SubTask> getAllSubTasksOfEpic(Epic e) {
        long id=e.getId();
        for (Epic epic : epics.values()) {
            if (epic.getId() == id) return epic.getSubTasks();
        }
        System.out.println(WARNING);
        return null;
    }
}
