package server;

import com.google.gson.Gson;
import interfaces.TaskManager;
import main.HttpTaskServer;
import memory.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskServerTestPrioritized {
    private static HttpTaskServer httpTaskServer;
    private static TaskManager inMemoryTaskManager;
    private static URI url = URI.create("http://localhost:8080/tasks");
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
    public void getPrioritized() throws IOException, InterruptedException {
        Task taskForPrioritizedA = new Task("a", "b", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(5));
        Task taskForPrioritizedC = new Task("c", "d", Status.NEW, LocalDateTime.now().plusMinutes(10), Duration.ofMinutes(5));
        String taskJson = gson.toJson(taskForPrioritizedA);
        String taskJson1 = gson.toJson(taskForPrioritizedC);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson1)).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        client.send(request1, HttpResponse.BodyHandlers.ofString());

        assertEquals(2, inMemoryTaskManager.getPrioritizedTasks().size());
    }
}
