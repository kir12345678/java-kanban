package httpserver;

import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import model.Task;
import service.TaskManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class TaskHttpHandler extends BaseHttpHandler {

    public TaskHttpHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery();
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        switch (method) {
            case "GET":
                if (pathParts.length == 2) {
                    handleGetAllTasks(exchange);
                } else if (pathParts.length == 3) {
                    if (!query.isEmpty()) {
                        Integer id = Integer.parseInt(query.split("=")[1]);
                        Task task = taskManager.getTask(id);
                        String jsonTask = HttpTaskServer.getGson().toJson(task);
                        writeResponse(exchange, jsonTask, 200);
                    }
                }
                break;
            case "POST":
                if (pathParts.length == 2) {
                    Task task = getTaskFromRequestBody(exchange);
                    taskManager.save(task);
                    writeResponse(exchange, "Saved", 201);
                } else if (pathParts.length == 3) {
                    if (!query.isEmpty()) {
                        Task task = getTaskFromRequestBody(exchange);
                        taskManager.updateTask(task);
                        String jsonTask = HttpTaskServer.getGson().toJson(task);
                        writeResponse(exchange, jsonTask, 201);
                    }
                }
                break;
            case "DELETE":
                if (pathParts.length == 2) {
                    taskManager.delAllTasks();
                    writeResponse(exchange, "delete all tasks", 201);
                } else if (pathParts.length == 3) {
                    if (!query.isEmpty()) {
                        Integer id = Integer.parseInt(query.split("=")[1]);
                        taskManager.delTask(id);
                        writeResponse(exchange, ("delete task id=" + id), 201);
                    }
                }
                break;
            default:
                writeResponse(exchange, "incorrect query", 400);
        }

    }

    private Task getTaskFromRequestBody(HttpExchange exchange) throws IOException {
        return HttpTaskServer.getGson().fromJson(getRequestBody(exchange), Task.class);
    }

    private void handleGetAllTasks(HttpExchange exchange) {
        List<Task> tasksList = taskManager.getAllTasks();
        Type listType = new TypeToken<List<Type>>() {}.getType();
        String listTask = HttpTaskServer.getGson().toJson(tasksList, listType);
        try {
            writeResponse(exchange, listTask, 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
