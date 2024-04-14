package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Task;
import model.SubTask;
import model.Epic;

public class TaskManger {
    private int seq = 0;

    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, SubTask> subTasks = new HashMap<>();
    private Map<Integer, Epic> epics= new HashMap<>();

    public int genId() {
        return seq++;
    }
    //--------------------
    public Task save(Task task) {
        task.setId(genId());
        tasks.put(task.getId(), task);
        return task;
    }

    public SubTask save(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpic().getId());
        epic.addSubTask(subTask);
        subTasks.put(subTask.getId(), subTask);
        return subTask;
    }

    public Epic save(Epic epic) {
        epic.setId(genId());
        epics.put(epic.getId(), epic);
        return epic;
    }
    //---------

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public Task getTask(int id) {
        return this.tasks.get(id);
    }

    public SubTask getSubTask(int id) {
        return this.subTasks.get(id);
    }

    public ArrayList<SubTask> getEpicSubTasks(Epic epic) {
        return epic.getSubTasks();
    }

    public Epic getEpic(int id) {
        return this.epics.get(id);
    }

    public void delAllTasks() {
        this.tasks.clear();
    }

    public void delAllSubTasks() {
        for (Epic epic : epics.values()) {
            epic.delAllSubTask();
        }
        this.subTasks.clear();
    }

    public void delAllEpics() {
        delAllSubTasks();
        this.epics.clear();
    }

    public void delTask(int id) {
        this.tasks.remove(id);
    }

    public void delSubTask(int id) {
        SubTask subTask = this.subTasks.get(id);
        subTask.getEpic().delSubTask(subTask);
        this.subTasks.remove(id);
    }
    public void delEpic(int id) {
        Epic epic = epics.get(id);
        ArrayList<SubTask> subTasks = epic.getSubTasks();
        for (SubTask subTask : subTasks) {
            this.subTasks.remove(subTask.getId());
        }
        epic.delAllSubTask();
        this.epics.remove(id);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        subTask.getEpic().updateStatus();
    }
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

}
