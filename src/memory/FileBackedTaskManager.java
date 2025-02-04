package memory;

import customexceptions.ManagerSaveException;
import interfaces.HistoryManager;
import status.Status;
import task.Epic;
import task.SubTask;
import task.Task;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private static final String pathFile = "C:/Users/Astemir/IdeaProjects/java-kanban/src/filesformanager/FileBackedTaskManager.txt";

    @Override
    public boolean addTask(Task task) {
        super.addTask(task);
        save();
        return true;
    }

    @Override
    public boolean addEpic(Epic epic) {
        super.addEpic(epic);
        save();
        return true;
    }

    @Override
    public HistoryManager getHistoryManager() {
        return super.getHistoryManager();
    }

    @Override
    public boolean addSubTasks(SubTask subTask) {
        super.addSubTasks(subTask);
        save();
        return true;
    }

    @Override
    public List<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return super.getAllSubTasks();
    }

    @Override
    public List<Epic> getAllEpics() {
        return super.getAllEpics();
    }

    @Override
    public Task getOfIdTask(Task t) {
        return super.getOfIdTask(t);
    }

    @Override
    public SubTask getOfIdSubTask(SubTask s) {
        return super.getOfIdSubTask(s);
    }

    @Override
    public Epic getOfIdEpic(Epic e) {
        return super.getOfIdEpic(e);
    }

    @Override
    public void updateTask(Task task) {
        super.getOfIdTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.getOfIdSubTask(subTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteOfIdTask(Task t) {
        super.deleteOfIdTask(t);
        save();
    }

    @Override
    public void deleteOfIdSubTask(SubTask s) {
        super.deleteOfIdSubTask(s);
        save();
    }

    @Override
    public void deleteOfIdEpic(Epic e) {
        super.getOfIdEpic(e);
        save();
    }

    @Override
    public void deleteOfAllTasks() {
        super.deleteOfAllTasks();
        save();
    }

    @Override
    public void deleteOfAllEpics() {
        super.deleteOfAllEpics();
        save();
    }

    @Override
    public void deleteOfAllSubTasks() {
        super.deleteOfAllSubTasks();
        save();
    }

    @Override
    public ArrayList<SubTask> getAllSubTasksOfEpic(Epic e) {
        return super.getAllSubTasksOfEpic(e);
    }

    public void save() {
        File fileForManager = new File(pathFile);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileForManager))) {
            for (Object taskId : tasks.keySet()) {
                bufferedWriter.write(tasks.get(taskId).toString() + "\n");
            }
            for (Object epicId : epics.keySet()) {
                bufferedWriter.write(epics.get(epicId).toString() + "\n");
            }
            for (Object subTaskId : subTasks.keySet()) {
                bufferedWriter.write(subTasks.get(subTaskId).toString() + "\n");
            }
            bufferedWriter.write(" ");
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Ошибка при записи данных");
            } catch (ManagerSaveException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            FileBackedTaskManager loadTasks = new FileBackedTaskManager();
            while ((bufferedReader.read() != ' ' && bufferedReader.ready())) {
                String[] line = bufferedReader.readLine().split(",");
                String type = line[1];
                String nameTask = line[2];
                String statusTask = line[3];
                Status status = Status.NEW;
                switch (statusTask) {
                    case "DONE":
                        status = Status.DONE;
                        break;
                    case "IN_PROGRESS":
                        status = Status.IN_PROGRESS;
                        break;
                    case "NEW":
                        status = Status.NEW;
                        break;
                }
                String discriptionTask = line[4];
                String epicName = null;
                if (line.length > 5) {
                    epicName = line[5];
                }
                if (type.equals("Epic")) {
                    Epic epicFromFile = new Epic(nameTask, discriptionTask, status);
                    loadTasks.addEpic(epicFromFile);
                } else if (type.equals("SubTask")) {
                    Epic epicForSubTask = null;
                    List<Epic> epics = loadTasks.getAllEpics();
                    for (int i = 0; i < epics.size(); i++) {
                        if (epics.get(i).getNameOfTask().equals(epicName)) {
                            epicForSubTask = epics.get(i);
                            break;
                        }
                    }
                    SubTask subTaskFromFile;
                    if (line.length == 8) {
                        LocalDateTime startTime = LocalDateTime.parse(line[6]);
                        LocalDateTime endTime = LocalDateTime.parse(line[7]);
                        Duration duration = Duration.between(startTime, endTime);
                        subTaskFromFile = new SubTask(nameTask, discriptionTask, status, epicForSubTask,startTime, duration);
                    } else subTaskFromFile = new SubTask(nameTask, discriptionTask, status, epicForSubTask);
                    epicForSubTask.addSubTask(subTaskFromFile);
                    loadTasks.addSubTasks(subTaskFromFile);
                } else if (type.equals("Task")) {
                    Task taskFromFile;
                    if (line.length == 8) {
                        LocalDateTime startTime = LocalDateTime.parse(line[6]);
                        LocalDateTime endTime = LocalDateTime.parse(line[7]);
                        Duration duration = Duration.between(startTime, endTime);
                        taskFromFile = new Task(nameTask, discriptionTask, status, startTime, duration);
                    } else taskFromFile = new Task(nameTask, discriptionTask, status);
                    loadTasks.addTask(taskFromFile);
                }
            }
            return loadTasks;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
