package httpserver;

import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import model.Task;
import service.TaskManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class HistoryHttpHandler extends BaseHttpHandler {
    public HistoryHttpHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] pathParts = exchange.getRequestURI().getPath().split("/");

        if (method.equals("GET") && pathParts[1].equals("history")) {
            List<Task> history = taskManager.getHistoryManager().getHistory();
            Type listType = new TypeToken<List<Type>>() {}.getType();
            String listHistory = HttpTaskServer.getGson().toJson(history, listType);
            writeResponse(exchange, listHistory, 200);
        } else {
            writeResponse(exchange, "�������� ������", 400);
        }

    }
}
