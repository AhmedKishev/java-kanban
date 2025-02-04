package server;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskServerTestHistory {
    private static HttpTaskServer httpTaskServer;
    private static TaskManager inMemoryTaskManager;
    private static URI url = URI.create("http://localhost:8080/history");

    @BeforeEach
    public void createServer() throws IOException {
        inMemoryTaskManager = new InMemoryTaskManager();
        httpTaskServer = new HttpTaskServer(inMemoryTaskManager);
    }

    @AfterEach
    public void stopServer() {
        httpTaskServer.stop();
    }

    @Test
    public void getHistory() throws IOException, InterruptedException {
        Task taskFromHistory = new Task("a", "b", Status.NEW);
        inMemoryTaskManager.addTask(taskFromHistory);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

}
