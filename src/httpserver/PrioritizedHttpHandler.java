package httpserver;

import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import model.Task;
import service.TaskManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PrioritizedHttpHandler extends BaseHttpHandler {
    public PrioritizedHttpHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] pathParts = exchange.getRequestURI().getPath().split("/");

        if (method.equals("GET") && pathParts[1].equals("prioritized")) {
            List<Task> prioritizedList = taskManager.getPrioritizedTask();
            Type listType = new TypeToken<List<Type>>() {}.getType();
            String listPrioritized = HttpTaskServer.getGson().toJson(prioritizedList, listType);
            writeResponse(exchange, listPrioritized, 200);
        } else {
            writeResponse(exchange, "�������� ������", 400);
        }
    }
}
