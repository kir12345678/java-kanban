package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected int seq = 0;

    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager;
    private final TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));


    public InMemoryTaskManager(HistoryManager newHistoryManager) {
        this.historyManager = newHistoryManager;
    }

    private int genId() {
        return seq++;
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public Task save(Task task) {
        task.setId(genId());
        tasks.put(task.getId(), task);
        checkTaskTime(task);
        if (task.getStartTime() != null) {
            prioritizedTasks.add(task);
        }
        return task;
    }

    @Override
    public SubTask save(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpic().getId());
        if (epic != null) {
            epic.addSubTask(subTask);
        }
        subTask.setId(genId());
        subTasks.put(subTask.getId(), subTask);
        checkTaskTime(subTask);
        if (subTask.getStartTime() != null) {
            prioritizedTasks.add(subTask);
        }
        return subTask;
    }

    @Override
    public Epic save(Epic epic) {
        epic.setId(genId());
        epics.put(epic.getId(), epic);
        if (epic.getStartTime() != null) {
            prioritizedTasks.add(epic);
        }
        return epic;
    }
    //---------

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Task getTask(int id) {
        historyManager.add(this.tasks.get(id));
        return this.tasks.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        historyManager.add(this.subTasks.get(id));
        return this.subTasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(this.epics.get(id));
        return this.epics.get(id);
    }

    @Override
    public ArrayList<SubTask> getEpicSubTasks(Epic epic) {
        return epic.getSubTasks();
    }

    @Override
    public void delAllTasks() {
        this.tasks.clear();
    }

    @Override
    public void delAllSubTasks() {
        for (Epic epic : epics.values()) {
            epic.delAllSubTask();
        }
        this.subTasks.clear();
    }

    @Override
    public void delAllEpics() {
        delAllSubTasks();
        this.epics.clear();
    }

    @Override
    public void delTask(int id) {
        this.tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void delSubTask(int id) {
        SubTask subTask = this.subTasks.get(id);
        subTask.getEpic().delSubTask(subTask);
        this.subTasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void delEpic(int id) {
        Epic epic = epics.get(id);
        ArrayList<SubTask> subTasks = epic.getSubTasks();
        for (SubTask subTask : subTasks) {
            this.subTasks.remove(subTask.getId());
            historyManager.remove(subTask.getId());
        }
        epic.delAllSubTask();
        this.epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void updateTask(Task task) {
        checkTaskTime(task);
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        checkTaskTime(subTask);
        subTasks.put(subTask.getId(), subTask);
        subTask.getEpic().updateStatus();
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public List<Task> getPrioritizedTask() {
        return new ArrayList<>(prioritizedTasks);
    }

    private void checkIntersection(Task taskFirst, Task taskSecond) {
        LocalDateTime beginTaskFirst = taskFirst.getStartTime();
        LocalDateTime endTaskFirst = taskFirst.getEndTime();
        LocalDateTime beginTaskSecond = taskSecond.getStartTime();
        LocalDateTime endTaskSecond = taskSecond.getEndTime();
        if (endTaskFirst == null) {
            endTaskFirst = LocalDateTime.MAX;
        }
        if (endTaskSecond == null) {
            endTaskSecond = LocalDateTime.MAX;
        }

        if (beginTaskFirst.isBefore(endTaskSecond) && beginTaskSecond.isBefore(endTaskFirst)) {
            throw new ValidationException("У задач ID = " + taskFirst.getId() + " и ID = " + taskSecond.getId()
                    + "пересекается время выполнения.");
        }
    }

    private void checkTaskTime(Task task) {
        if (task.getStartTime() != null) {
            for (Task tempTask : prioritizedTasks) {
                if (task.getId() == tempTask.getId()) {
                    continue;
                } else {
                    checkIntersection(task, tempTask);
                }
            }
        }
    }

}
