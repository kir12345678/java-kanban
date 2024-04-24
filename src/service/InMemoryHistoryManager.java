package service;

import model.Task;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_DEEP_HISTORY = 10;
    private LinkedList<Task> historyList = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (historyList.size() > MAX_DEEP_HISTORY - 1) {
            historyList.removeFirst();
        }
        historyList.addLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return new LinkedList<>(historyList);
    }
}
