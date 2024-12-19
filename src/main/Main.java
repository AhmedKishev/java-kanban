package main;

import memory.FileBackedTaskManager;
import status.Status;
import task.Epic;
import task.SubTask;
import task.Task;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Task task = new Task("a", "b", Status.NEW);
        Task task1 = new Task("b", "c", Status.NEW);
        Epic epic = new Epic("a", "b", Status.IN_PROGRESS);
        SubTask subTask = new SubTask("c", "d", Status.DONE, epic);
        SubTask subTask1 = new SubTask("d", "g", Status.NEW, epic);
        epic.addSubTask(subTask);
        FileBackedTaskManager inMemoryTaskManager = new FileBackedTaskManager();
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.addSubTasks(subTask);
        inMemoryTaskManager.addSubTasks(subTask1);
    }

}