package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final HashMap<Integer, Node> historyMap = new HashMap<>();

    private static class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Node prev, Task newTask, Node next) {
            this.prev = prev;
            this.task = newTask;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;
    private int size = 0;

    private void linkLast(Task element) {
        final Node oldTail = tail;
        final Node newNode = new Node(oldTail, element, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
        size++;
        historyMap.put(newNode.task.getId(), newNode);
    }

    private List<Task> getTasks() {
        List<Task> listTask = new ArrayList<>();
        Node currentNode = head;
        while (currentNode != null) {
            listTask.add(currentNode.task);
            currentNode = currentNode.next;
        }
        return listTask;
    }

    private void removeNode(Node delNode) {
        if (size != 0) {
            Node prevNode = delNode.prev;
            Node nextNode = delNode.next;
            if (prevNode == null) {
                head = nextNode;
            } else {
                prevNode.next = nextNode;
                delNode.prev = null;
            }
            if (nextNode == null) {
                tail = prevNode;
            } else {
                nextNode.prev = prevNode;
                delNode.next = null;
            }
            delNode.task = null;
            size--;
        }
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
            historyMap.remove(id);
        }
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            remove(task.getId());
            linkLast(task);

        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}

