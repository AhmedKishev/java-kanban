package memory;

import interfaces.TaskManager;
import interfaces.HistoryManager;
import task.Epic;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager<T extends Task> implements TaskManager {
    private HashMap<Long, Task> tasks = new HashMap<>();
    private HashMap<Long, Epic> epics = new HashMap<>();
    private HashMap<Long, SubTask> subTasks = new HashMap<>();
    private HistoryManager historyManager = new InMemoryHistoryManager();


    @Override
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        List<SubTask> subTasks = epic.getSubTasks();
        for (int itNewSubTask = 0; itNewSubTask < subTasks.size(); itNewSubTask++) { //добавляем из эпика сабтаски, если их еще нету в мапе
            if (!this.subTasks.containsKey(subTasks.get(itNewSubTask).getId())) {
                this.subTasks.put(subTasks.get(itNewSubTask).getId(), subTasks.get(itNewSubTask));
            }
        }
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
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            allTasks.add(task);
        }
        return allTasks;
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        ArrayList<SubTask> allSubTusks = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            allSubTusks.add(subTask);
        }
        return allSubTusks;
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> allEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            allEpics.add(epic);
        }
        return allEpics;
    }

    @Override
    public Task getOfIdTask(Task t) {
        long id = t.getId();
        for (Task task : tasks.values()) {
            if (task.getId() == id) {
                historyManager.add(task);
                return task;
            }
        }
        return null;
    }

    @Override
    public SubTask getOfIdSubTask(SubTask s) {
        long id = s.getId();
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getId() == id) {
                historyManager.add(subTask);
                return subTask;
            }
        }
        return null;
    }

    @Override
    public Epic getOfIdEpic(Epic e) {
        long id = e.getId();
        for (Epic epic : epics.values()) {
            if (epic.getId() == id) {
                historyManager.add(epic);
                return epic;
            }
        }
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
            if (task.getId() == id) {
                tasks.remove(id);
                historyManager.remove(t.getId());
                Node node = new Node(task);
                historyManager.removeNode(node);
                return;
            }
        }
    }

    @Override
    public void deleteOfIdSubTask(SubTask s) {
        long id = s.getId();
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getId() == id) {
                Epic epic = subTask.getEpic();
                epic.removeSubTask(s);
                subTask.getEpic().statusCheck();
                subTasks.remove(id);
                historyManager.remove(s.getId());
                Node node = new Node(subTask);
                historyManager.removeNode(node);
                return;
            }
        }

    }

    @Override
    public void deleteOfIdEpic(Epic e) {
        long id = e.getId();
        for (Epic epic : epics.values()) {
            if (epic.getId() == id) {
                epics.remove(id);
                Node node = new Node(epic);
                historyManager.removeNode(node);
                historyManager.remove(e.getId());
            }
        }
    }

    @Override
    public void deleteOfAllTasks() {
        for (int itForDeleteTasks = 0; itForDeleteTasks < tasks.size(); itForDeleteTasks++) {
            historyManager.remove(tasks.get(itForDeleteTasks).getId());
        }
        tasks.clear();
    }

    @Override
    public void deleteOfAllEpics() {
        for (int itForDeleteEpics = 0; itForDeleteEpics < epics.size(); itForDeleteEpics++) {
            historyManager.remove(epics.get(itForDeleteEpics).getId());
        }
        epics.clear();
        for (int itForDeleteSubTasks = 0; itForDeleteSubTasks < subTasks.size(); itForDeleteSubTasks++) {
            historyManager.remove(subTasks.get(itForDeleteSubTasks).getId());
        }
        subTasks.clear();
    }

    @Override
    public void deleteOfAllSubTasks() {
        for (int itForDeleteSubTasks = 0; itForDeleteSubTasks < subTasks.size(); itForDeleteSubTasks++) {
            historyManager.remove(subTasks.get(itForDeleteSubTasks).getId());
        }
        deleteOfAllEpics();
    }

    @Override
    public ArrayList<SubTask> getAllSubTasksOfEpic(Epic e) {
        long id = e.getId();
        for (Epic epic : epics.values()) {
            if (epic.getId() == id) return epic.getSubTasks();
        }
        return null;
    }
}
