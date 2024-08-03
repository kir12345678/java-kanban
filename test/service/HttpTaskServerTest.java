package service;

import com.google.gson.Gson;
import httpserver.HttpTaskServer;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static model.Status.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskServerTest {
    TaskManager taskManager = Managers.getDefault();

    private HttpTaskServer taskServer = new HttpTaskServer(taskManager);
    private Gson gson = HttpTaskServer.getGson();

    public HttpTaskServerTest() throws IOException {
    }

    @BeforeEach
    public void setUp() throws IOException {
        //taskManager = new InMemoryTaskManager();
        taskManager.delAllTasks();
        taskManager.delAllSubTasks();
        taskManager.delAllEpics();

        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void shouldGetAllTask() throws IOException, InterruptedException {
        // создаём задачу
        LocalDateTime startDate1 = LocalDateTime.of(2024, 6, 23, 15, 33);
        Duration duration1 = Duration.ofMinutes(15);

        LocalDateTime startDate2 = LocalDateTime.parse("00:00 21.06.2024", DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
        Duration duration2 = Duration.ofMinutes(15);

        taskManager.save(new Task("Task1", "Task1", startDate1, duration1));
        taskManager.save(new Task("Task2", "Task2", startDate2, duration2));

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        assertEquals(200, response.statusCode());

        // почему-то ругается, может быть адаптер
        //Type listType = new TypeToken<ArrayList<Type>>() {}.getType();
        //final List<Task> parsedListTask = HttpTaskServer.getGson().fromJson(response.body(), listType/*new TypeToken<ArrayList<Type>>(){}.getType()*/);
        final Task[] parsedListTask = HttpTaskServer.getGson().fromJson(response.body(), Task[].class);
        assertEquals("Task1", parsedListTask[0].getName(), "Неверное имя 1ой задачи");
    }

    @Test
    public void shouldGetTask() throws IOException, InterruptedException {
        // создаём задачу
        LocalDateTime startDate1 = LocalDateTime.of(2021, 6, 23, 15, 33);
        Duration duration1 = Duration.ofMinutes(15);

        LocalDateTime startDate2 = LocalDateTime.parse("00:00 21.06.2022", DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
        Duration duration2 = Duration.ofMinutes(15);

        taskManager.save(new Task("Task1", "Task1", startDate1, duration1));
        taskManager.save(new Task("Task2", "Task2", startDate2, duration2));

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        //System.out.println("1");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        assertEquals(200, response.statusCode());

        final Task parsedTask = gson.fromJson(response.body(), Task.class);
        assertEquals("Task2", parsedTask.getName(), "Неверное имя 1ой задачи");
    }

    @Test
    public void shouldBeAddTask() throws IOException, InterruptedException {
        Task task = new Task("Task1", "Task1",
                LocalDateTime.now().plusMinutes(10), Duration.ofMinutes(60));

        String taskJson = HttpTaskServer.getGson().toJson(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals("Task1", taskManager.getTask(0).getName(), "Некорректное имя задачи");

    }
    @Test
    public void shouldBeUpdateTask() throws IOException, InterruptedException {
        Task task = new Task("Task1", "Task1",
                LocalDateTime.now().plusMinutes(10), Duration.ofMinutes(60));
        taskManager.save(task);
        String taskJson = HttpTaskServer.getGson().toJson(task);
        System.out.println(taskJson);

        Task task1 = new Task(0,"Task12345", "Task1", NEW, LocalDateTime.now().plusMinutes(10), Duration.ofMinutes(60));

        String taskJson1 = HttpTaskServer.getGson().toJson(task1);
        System.out.println(taskJson1);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson1)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Task task0 = taskManager.getTask(0);
        String taskJson0 = HttpTaskServer.getGson().toJson(task0);
        System.out.println(taskJson0);

        assertEquals(201, response.statusCode());
        assertEquals("Task12345", taskManager.getTask(0).getName(), "Некорректное имя задачи");

    }
    @Test
    public void shouldBeDeleteTask() throws IOException, InterruptedException {
        Task task1 = new Task("Task1", "Task1",
                LocalDateTime.now().plusMinutes(10), Duration.ofMinutes(10));
        Task task2 = new Task("Task2", "Task2",
                LocalDateTime.now().plusMinutes(30), Duration.ofMinutes(30));

        taskManager.save(task1);
        taskManager.save(task2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task?id=0");

        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertEquals(1, taskManager.getAllTasks().size(), "Список не пуст");

    }

}
