package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dateadapter.DurationAdapter;
import dateadapter.LocalDateAdapter;
import interfaces.HistoryManager;
import interfaces.TaskManager;
import managers.Managers;
import task.Epic;
import task.SubTask;
import task.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public class HttpTaskServer {
    private static final int PORT = 8080;
    private static Managers managers;
    private static GsonBuilder gsonBuilder = new GsonBuilder();
    private static HttpServer httpServer;
    private static Gson gson;
    private static BaseHttpHandler baseHttpHandler = new BaseHttpHandler();

    public HttpTaskServer(TaskManager inMemoryTaskManager) throws IOException {
        managers = new Managers(inMemoryTaskManager);
        start();
    }

    public static Gson getGson() {
        return gson;
    }

    public static void start() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.createContext("/epics", new EpicHandler());
        httpServer.createContext("/subtasks", new SubTaskHandler());
        httpServer.createContext("/prioritized", new HistoryHandler());
        httpServer.createContext("/history", new PrioritizedHandler());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter());
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gson = gsonBuilder.create();
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(1);
    }

    static class TaskHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET": {
                    List<Task> list = managers.getDefault().getAllTasks();
                    baseHttpHandler.sendText(exchange, "Tasks успешно получены", 200);
                    break;
                }
                case "POST": {
                    InputStream inputStream = exchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    Task task = null;
                    try {
                        task = new Task(gson.fromJson(body, Task.class));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    boolean isAdd = managers.getDefault().addTask(task);
                    if (isAdd) {
                        baseHttpHandler.sendText(exchange, "Задача успешно добавлена!", 201);
                    } else {
                        baseHttpHandler.sendText(exchange, "Данная задача пересекается с другой!", 406);
                    }
                    break;
                }
                case "DELETE": {
                    InputStream inputStream = exchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    Task task = null;
                    try {
                        task = new Task(gson.fromJson(body, Task.class));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    int beforeSize = managers.getDefault().getAllTasks().size();
                    managers.getDefault().deleteOfIdTask(task);
                    int afterSize = managers.getDefault().getAllTasks().size();
                    if ((afterSize + 1) == beforeSize) {
                        baseHttpHandler.sendText(exchange, "Задача удалена!", 200);
                    } else {
                        baseHttpHandler.sendText(exchange, "Не удалось удалить задачу", 404);
                    }
                    break;
                }
                default:
                    baseHttpHandler.sendText(exchange, "Ошибка обработки запроса", 404);
            }
        }
    }

    static class EpicHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET": {
                    String[] path = exchange.getRequestURI().getPath().split("/");
                    if (path.length == 2) {
                        baseHttpHandler.sendText(exchange, "Epics успешно получены", 200);
                    } else if (path.length == 3 && path[2].equals("subtasks")) {
                        InputStream inputStream = exchange.getRequestBody();
                        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                        Epic epic = null;
                        try {
                            epic = new Epic(gson.fromJson(body, Epic.class));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        List<SubTask> subTasksOfEpic = managers.getDefault().getAllSubTasksOfEpic(epic);
                        if (!subTasksOfEpic.isEmpty()) {
                            baseHttpHandler.sendText(exchange, "SubTasks получены", 200);
                        } else baseHttpHandler.sendText(exchange, "Эпика нет", 404);
                    } else baseHttpHandler.sendText(exchange, "Not Found", 404);
                    break;
                }
                case "POST": {
                    InputStream inputStream = exchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    Epic epic = null;
                    try {
                        epic = new Epic(gson.fromJson(body, Epic.class));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    boolean isAdd = managers.getDefault().addEpic(epic);
                    if (isAdd) {
                        baseHttpHandler.sendText(exchange, "Задача успешно добавлена!", 201);
                    } else {
                        baseHttpHandler.sendText(exchange, "Данная задача пересекается с другой!", 406);
                    }
                    break;
                }
                case "DELETE": {
                    InputStream inputStream = exchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    Epic epic = null;
                    try {
                        epic = new Epic(gson.fromJson(body, Epic.class));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    int beforeSize = managers.getDefault().getAllEpics().size();
                    managers.getDefault().deleteOfIdEpic(epic);
                    int afterSize = managers.getDefault().getAllSubTasks().size();
                    if ((afterSize + 1) == beforeSize) {
                        baseHttpHandler.sendText(exchange, "Задача удалена!", 200);
                    } else {
                        baseHttpHandler.sendText(exchange, "Не удалось удалить задачу", 404);
                    }
                    break;
                }
            }
        }
    }

    static class SubTaskHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET": {
                    baseHttpHandler.sendText(exchange, "SubTasks успешно получены", 200);
                    break;
                }
                case "POST": {
                    InputStream inputStream = exchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    SubTask subTask = null;
                    try {
                        subTask = new SubTask(gson.fromJson(body, SubTask.class));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    boolean isAdd = managers.getDefault().addSubTasks(subTask);
                    if (isAdd) {
                        baseHttpHandler.sendText(exchange, "Задача успешно добавлена!", 201);
                    } else {
                        baseHttpHandler.sendText(exchange, "Данная задача пересекается с другой!", 406);
                    }
                    break;
                }
                case "DELETE": {
                    InputStream inputStream = exchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    SubTask subTask = null;
                    try {
                        subTask = new SubTask(gson.fromJson(body, SubTask.class));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    int beforeSize = managers.getDefault().getAllSubTasks().size();
                    managers.getDefault().deleteOfIdSubTask(subTask);
                    int afterSize = managers.getDefault().getAllSubTasks().size();
                    if ((afterSize + 1) == beforeSize) {
                        baseHttpHandler.sendText(exchange, "Задача удалена!", 200);
                    } else {
                        baseHttpHandler.sendText(exchange, "Не удалось удалить задачу", 404);
                    }
                    break;
                }
                default:
                    baseHttpHandler.sendText(exchange, "Ошибка обработки запроса", 404);
            }
        }
    }

    static class HistoryHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET": {
                    HistoryManager historyManager = managers.getDefault().getHistoryManager();
                    baseHttpHandler.sendText(exchange, "История получена", 200);
                    break;
                }
                default:
                    baseHttpHandler.sendText(exchange, "Not Found", 404);
            }
        }
    }


    static class PrioritizedHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET": {
                    Set<Task> prioritizedTasks = managers.getDefault().getPrioritizedTasks();
                    baseHttpHandler.sendText(exchange, "Задачи с приоритетами получены", 200);
                    break;
                }
                default:
                    baseHttpHandler.sendText(exchange, "Not Found", 404);
            }
        }
    }
}
