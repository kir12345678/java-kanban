package service;

import java.util.ArrayList;
import java.util.HashMap;

import model.Status;
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
        subTasks = new HashMap<>();
        epics = new HashMap<>();
    }
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
        /*subTask.setId(genId());
        subTasks.put(subTask.getId(), subTask);
        return subTask;*/
        Epic epic = epics.get(subTask.getEpic().getId());
        epic.AddSubTask(subTask);
        //epic.updateStatus();
        subTasks.put(subTask.getId(), subTask);
        return subTask;
    }

    public Epic save(Epic epic) {
        epic.setId(genId());
        epics.put(epic.getId(), epic);
        return epic;
    }
    //---------

    public HashMap<Integer, Task> getAllTasks() {
        return this.tasks;
    }
    public HashMap<Integer, SubTask> getAllSubTasks() {
        return this.subTasks;
    }
    public HashMap<Integer, Epic> getAllEpics() {
        return this.epics;
    }

    public Task getTask(int id) {
        return this.tasks.get(id);
    }
    public SubTask getSubTask(int id) {
        return this.subTasks.get(id);
    }
    public Epic getEpic(int id) {
        return this.epics.get(id);
    }

    public void delAllTasks() {
        this.tasks.clear();
    }

    public void delAllSubTasks()
    {
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
        subTask.getEpic().DelSubTask(subTask);
        this.subTasks.remove(id);
    }
    public void delEpic(int id) {
        Epic epic = epics.get(id);
        ArrayList<SubTask> subTasks = epic.getSubTasks();
        for (SubTask subTask : subTasks) {
            this.subTasks.remove(subTask.getId());
        }
        epic.DelAllSubTask();
        this.epics.remove(id);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

}
