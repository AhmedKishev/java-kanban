package server;

import com.google.gson.Gson;
import interfaces.TaskManager;
import main.HttpTaskServer;
import memory.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import task.Epic;
import task.SubTask;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpTaskServerTestSubTasks {
    private static HttpTaskServer httpTaskServer;
    private static TaskManager inMemoryTaskManager;
    private static URI url = URI.create("http://localhost:8080/subtasks");
    private static Gson gson;

    @BeforeEach
    public void createServer() throws IOException {
        inMemoryTaskManager = new InMemoryTaskManager();
        httpTaskServer = new HttpTaskServer(inMemoryTaskManager);
        gson = HttpTaskServer.getGson();
    }

    @AfterEach
    public void stopServer() {
        httpTaskServer.stop();
    }

    @Test
    public void testAddSubTask() throws IOException, InterruptedException {
        Epic epicForSubTask = new Epic("a", "b", Status.NEW);
        SubTask subTaskForRequest = new SubTask("Test 2", "Testing task 2",
                Status.NEW, epicForSubTask);
        String taskJson = gson.toJson(subTaskForRequest);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        List<SubTask> tasksFromManager = inMemoryTaskManager.getAllSubTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.get(0).getNameOfTask(), "Некорректное имя задачи");
    }

    @Test
    public void getAllSubTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    public void deleteOfIdSubTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Epic epicForSubTask = new Epic("a", "b", Status.NEW);
        SubTask subTaskForRequest = new SubTask("Test 2", "Testing task 2",
                Status.NEW, epicForSubTask, LocalDateTime.now(), Duration.ofMinutes(5));
        inMemoryTaskManager.addSubTasks(subTaskForRequest);
        String taskJson = gson.toJson(subTaskForRequest);
        HttpRequest request = HttpRequest.newBuilder().uri(url).method("DELETE", HttpRequest.
                BodyPublishers.ofString(taskJson, StandardCharsets.UTF_8)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }
}
