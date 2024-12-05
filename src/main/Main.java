package main;

import interfaces.HistoryManager;
import managers.Managers;
import memory.InMemoryTaskManager;
import status.Status;
import task.Epic;
import task.SubTask;
import task.Task;

public class Main {
    public static void main(String[] args) {
        Task task = new Task("a", "b", Status.NEW);
        Task task1 = new Task("c", "d", Status.NEW);
        Epic epic = new Epic("a", "b", Status.IN_PROGRESS);
        SubTask subTask = new SubTask("c", "d", Status.DONE, epic);
        epic.addSubTask(subTask);
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.getOfIdTask(task);
        inMemoryTaskManager.getOfIdTask(task1);
        inMemoryTaskManager.getOfIdTask(task);
        inMemoryTaskManager.getOfIdEpic(epic);
        inMemoryTaskManager.getOfIdTask(task1);
        inMemoryTaskManager.deleteOfIdEpic(epic);
        Managers managers = new Managers(inMemoryTaskManager);
        HistoryManager inMemoryHistoryManager = managers.getDefaultHistory();
        for (int i = 0; i < inMemoryHistoryManager.getHistory().size(); i++) {
            System.out.println(inMemoryHistoryManager.getHistory().get(i));
        }
    }

}