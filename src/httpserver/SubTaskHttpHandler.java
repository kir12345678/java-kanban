package httpserver;

import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import model.SubTask;
import service.TaskManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class SubTaskHttpHandler extends BaseHttpHandler {
    public SubTaskHttpHandler(TaskManager taskManager) {
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
                    handleGetAllSubTasks(exchange);
                } else if (pathParts.length == 3) {
                    if (!query.isEmpty()) {
                        Integer id = Integer.parseInt(query.split("=")[1]);
                        SubTask subTask = taskManager.getSubTask(id);
                        String jsonTask = HttpTaskServer.getGson().toJson(subTask);
                        writeResponse(exchange, jsonTask, 200);
                    }
                }
                break;
            case "POST":
                if (pathParts.length == 2) {
                    SubTask subTask = getSubTaskFromRequestBody(exchange);
                    taskManager.save(subTask);
                    writeResponse(exchange, "�������", 201);
                } else if (pathParts.length == 3) {
                    if (!query.isEmpty()) {
                        SubTask subTask = getSubTaskFromRequestBody(exchange);
                        taskManager.updateSubTask(subTask);
                        String jsonSubTask = HttpTaskServer.getGson().toJson(subTask);
                        writeResponse(exchange, jsonSubTask, 201);
                    }
                }
                break;
            case "DELETE":
                if (pathParts.length == 2) {
                    taskManager.delAllSubTasks();
                    writeResponse(exchange, "������� ��� ������", 201);
                } else if (pathParts.length == 3) {
                    if (!query.isEmpty()) {
                        Integer id = Integer.parseInt(query.split("=")[1]);
                        taskManager.delSubTask(id);
                        writeResponse(exchange, ("������� ������ id=" + id), 201);
                    }
                }
                break;
            default:
                writeResponse(exchange, "�������� ������", 400);
        }
    }

    private void handleGetAllSubTasks(HttpExchange exchange) {
        List<SubTask> subTasksList = taskManager.getAllSubTasks();
        Type listType = new TypeToken<List<Type>>() {}.getType();
        String listSubTask = HttpTaskServer.getGson().toJson(subTasksList, listType);
        try {
            writeResponse(exchange, listSubTask, 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SubTask getSubTaskFromRequestBody(HttpExchange exchange) throws IOException {
        return HttpTaskServer.getGson().fromJson(getRequestBody(exchange), SubTask.class);
    }
}
