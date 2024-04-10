package service;

import java.util.HashMap;
import model.Task;
import model.SubTask;
import model.Epic;

public class TaskManger {
    private int seq = 0;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, SubTask> subTasks;
    private HashMap<Integer, Epic> epics;

    public TaskManger() {
        tasks =  new HashMap<>();
    }
    public int genId() {
        return seq++;
    }

    public Task create(Task task) {
        task.setId(genId());
        tasks.put(task.getId(), task);
        return task;
    }

    public Task getTask(int id) {
        return this.tasks.get(id);
    }

    public HashMap<Integer, Task> getAllTasks() {
        return this.tasks;
    }

    public void delAllTasks() {
        this.tasks.clear();
    }

    public void delTask(int id) {
        this.tasks.remove(id);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }
}
