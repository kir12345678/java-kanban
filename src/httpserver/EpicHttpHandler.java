package httpserver;

import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import model.Epic;
import service.TaskManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class EpicHttpHandler extends BaseHttpHandler {
    public EpicHttpHandler(TaskManager taskManager) {
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
                    handleGetAllEpics(exchange);
                } else if (pathParts.length == 3) {
                    if (!query.isEmpty()) {
                        Integer id = Integer.parseInt(query.split("=")[1]);
                        Epic epic = taskManager.getEpic(id);
                        String jsonEpic = HttpTaskServer.getGson().toJson(epic);
                        writeResponse(exchange, jsonEpic, 200);
                    }
                }
                break;
            case "POST":
                if (pathParts.length == 2) {
                    Epic epic = getEpicFromRequestBody(exchange);
                    taskManager.save(epic);
                    writeResponse(exchange, "Создано", 201);
                } else if (pathParts.length == 3) {
                    if (!query.isEmpty()) {
                        Epic epic = getEpicFromRequestBody(exchange);
                        taskManager.updateEpic(epic);
                        String jsonEpic = HttpTaskServer.getGson().toJson(epic);
                        writeResponse(exchange, jsonEpic, 201);
                    }
                }
                break;
            case "DELETE":
                if (pathParts.length == 2) {
                    taskManager.delAllEpics();
                    writeResponse(exchange, "Удалены все задачи", 201);
                } else if (pathParts.length == 3) {
                    if (!query.isEmpty()) {
                        Integer id = Integer.parseInt(query.split("=")[1]);
                        taskManager.delEpic(id);
                        writeResponse(exchange, ("Удалена задача id=" + id), 201);
                    }
                }
                break;
            default:
                writeResponse(exchange, "Неверный запрос", 400);
        }
    }

    private Epic getEpicFromRequestBody(HttpExchange exchange) throws IOException {
        return HttpTaskServer.getGson().fromJson(getRequestBody(exchange), Epic.class);
    }

    private void handleGetAllEpics(HttpExchange exchange) {
        List<Epic> epicsList = taskManager.getAllEpics();
        Type listType = new TypeToken<List<Type>>() {}.getType();
        String listEpic = HttpTaskServer.getGson().toJson(epicsList, listType);
        try {
            writeResponse(exchange, listEpic, 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
