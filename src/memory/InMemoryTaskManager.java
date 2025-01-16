package memory;

import interfaces.TaskManager;
import interfaces.HistoryManager;
import task.Epic;
import task.SubTask;
import task.Task;

import java.util.*;
import java.util.stream.Collectors;


public class InMemoryTaskManager<T extends Task> implements TaskManager {
    protected HashMap<Long, Task> tasks = new HashMap<>();
    protected HashMap<Long, Epic> epics = new HashMap<>();
    protected HashMap<Long, SubTask> subTasks = new HashMap<>();
    protected HistoryManager historyManager = new InMemoryHistoryManager();
    private Comparator<Task> comparatorForPrioritizedTasks = (o1, o2) -> {
        if (o1.getStartTime().isBefore(o2.getStartTime())) return 1;
        return -1;
    };
    protected TreeSet<Task> prioritizedTasks = new TreeSet<Task>(comparatorForPrioritizedTasks);

    @Override
    public void addTask(Task task) {
        boolean isTask = false;
        for (Long key : tasks.keySet()) {
            if (tasks.get(key).getNameOfTask().equals(task.getNameOfTask())) {
                isTask = true;
                break;
            }
        }
        if (task.getStartTime() != null) {
            if (!intersectionOfTime(task)) prioritizedTasks.add(task);
        }
        if (!isTask) tasks.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        List<SubTask> subTasks = epic.getSubTasks();
        boolean isEpic = false;
        for (Long key : epics.keySet()) {
            if (epics.get(key).getNameOfTask().equals(epic.getNameOfTask())) isEpic = true;
        }
        if (!isEpic) {
            epics.put(epic.getId(), epic);
            for (int itNewSubTask = 0; itNewSubTask < subTasks.size(); itNewSubTask++) { //добавляем из эпика сабтаски, если их еще нету в мапе
                if (!this.subTasks.containsKey(subTasks.get(itNewSubTask).getId())) {
                    this.subTasks.put(subTasks.get(itNewSubTask).getId(), subTasks.get(itNewSubTask));
                }
            }
        }
        if (epic.getStartTime() != null) {
            if (!intersectionOfTime(epic)) prioritizedTasks.add(epic);
        }
    }

    @Override
    public HistoryManager getHistoryManager() {
        return this.historyManager;
    }


    @Override
    public void addSubTasks(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        if (subTask.getStartTime() != null) {
            if (!intersectionOfTime(subTask)) prioritizedTasks.add(subTask);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks.values().stream().map(task -> {
            return new Task(task.getNameOfTask(), task.getDescription(), task.getStatus());
        }).collect(Collectors.toList());
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return subTasks.values().stream().map(subTask -> {
            return new SubTask(subTask.getNameOfTask(), subTask.getDescription(), subTask.getStatus(), subTask.getEpic());
        }).collect(Collectors.toList());
    }

    @Override
    public List<Epic> getAllEpics() {
        return epics.values().stream().map(epic -> {
            return new Epic(epic.getNameOfTask(), epic.getDescription(), epic.getStatus());
        }).collect(Collectors.toList());
    }

    @Override
    public Task getOfIdTask(Task t) {
        long id = t.getId();
        Task findTask = tasks.values().stream().filter(task ->
                task.getId() == id
        ).findFirst().orElse(null);
        if (findTask == null) return null;
        else {
            historyManager.add(findTask);
            return findTask;
        }
    }

    @Override
    public SubTask getOfIdSubTask(SubTask s) {
        long id = s.getId();
        SubTask findSubTask = subTasks.values().stream().filter(subTask ->
                subTask.getId() == id
        ).findFirst().orElse(null);
        if (findSubTask == null) return null;
        else {
            historyManager.add(findSubTask);
            return findSubTask;
        }
    }

    @Override
    public Epic getOfIdEpic(Epic e) {
        long id = e.getId();
        Epic findEpic = epics.values().stream().filter(epic ->
                epic.getId() == id
        ).findFirst().orElse(null);
        if (findEpic == null) return null;
        else {
            historyManager.add(findEpic);
            return findEpic;
        }
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
        if (task.getStartTime() != null) prioritizedTasks.add(task);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        if (subTask.getStartTime() != null) prioritizedTasks.add(subTask);
        subTask.getEpic().statusCheck();
    }

    @Override
    public void updateEpic(Epic epic) {
        tasks.put(epic.getId(), epic);
        if (epic.getStartTime() != null) prioritizedTasks.add(epic);
        epic.statusCheck();
    }

    @Override
    public void deleteOfIdTask(Task t) {
        long id = t.getId();
        Optional<Task> findTask = tasks.values().stream().map(task -> {
            if (task.getId() == id)
                return task;
            return null;
        }).findFirst();
        findTask.ifPresent(task -> {
            tasks.remove(task);
            historyManager.remove(t.getId());
            Node node = new Node(task);
            historyManager.removeNode(node);
        });
    }

    @Override
    public void deleteOfIdSubTask(SubTask s) {
        long id = s.getId();
        Optional<SubTask> findSubTask = subTasks.values().stream().filter(subTask ->
                subTask.getId() == id
        ).findFirst();
        findSubTask.ifPresent(subTask -> {
            Epic epic = subTask.getEpic();
            epic.removeSubTask(s);
            subTask.getEpic().statusCheck();
            subTasks.remove(id);
            historyManager.remove(s.getId());
            Node node = new Node(subTask);
            historyManager.removeNode(node);
        });
    }

    @Override
    public boolean intersectionOfTime(Task task) {
        Optional<Task> findTask = this.prioritizedTasks.stream().filter(task1 ->
                task.getStartTime().isAfter(task1.getStartTime())
                        && task.getStartTime().isBefore(task1.getEndTime())
                        && task.getEndTime().isAfter(task1.getEndTime())
                        || task.getEndTime().isAfter(task1.getStartTime()) &&
                        task.getEndTime().isBefore(task1.getEndTime()) &&
                        task.getStartTime().isBefore(task1.getStartTime())
                        || task.getStartTime().isAfter(task1.getStartTime()) &&
                        task.getStartTime().isBefore(task1.getEndTime()) &&
                        task.getEndTime().isBefore(task1.getEndTime()) &&
                        task.getEndTime().isAfter(task1.getStartTime())
        ).findFirst();
        return findTask.isPresent();
    }

    @Override
    public void deleteOfIdEpic(Epic e) {
        long id = e.getId();
        Optional<Epic> epicFind = epics.values().stream().filter(epic -> epic.getId() == id).findFirst();
        epicFind.ifPresent(epic -> {
            epics.remove(id);
            Node node = new Node(epic);
            historyManager.removeNode(node);
            historyManager.remove(e.getId());
        });
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return this.prioritizedTasks;
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
        Optional<Epic> findEpic = epics.values().stream().filter(epic -> epic.getId() == id).findFirst();
        if (findEpic.isPresent()) {
            findEpic.get().getSubTasks();
        }
        return null;
    }
}

