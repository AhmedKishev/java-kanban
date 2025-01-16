package main;

import memory.FileBackedTaskManager;
import status.Status;
import task.Epic;
import task.SubTask;
import task.Task;


import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(1);
        LocalDateTime localDateTime1 = LocalDateTime.now().plusMinutes(6);
        LocalDateTime localDateTime2 = LocalDateTime.now();
        LocalDateTime localDateTime3 = LocalDateTime.now().plusMinutes(6);
        Duration duration = Duration.ofMinutes(4);
        Task task = new Task("a", "b", Status.NEW, localDateTime, duration);
        Task task1 = new Task("b", "c", Status.NEW, localDateTime1, duration);
        Epic epic = new Epic("a", "b", Status.IN_PROGRESS);
        SubTask subTask = new SubTask("c", "d", Status.DONE, epic, duration, localDateTime2);
        SubTask subTask1 = new SubTask("d", "g", Status.NEW, epic, duration, localDateTime3);
        FileBackedTaskManager inMemoryTaskManager = new FileBackedTaskManager();
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addTask(task1);
    }

}