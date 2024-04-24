package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;

public interface TaskManager {
    HistoryManager getHistoryManager();

    Task save(Task task);

    SubTask save(SubTask subTask);

    Epic save(Epic epic);

    List<Task> getAllTasks();

    List<SubTask> getAllSubTasks();

    List<Epic> getAllEpics();

    Task getTask(int id);

    SubTask getSubTask(int id);

    List<SubTask> getEpicSubTasks(Epic epic);

    Epic getEpic(int id);

    void delAllTasks();

    void delAllSubTasks();

    void delAllEpics();

    void delTask(int id);

    void delSubTask(int id);

    void delEpic(int id);

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void updateEpic(Epic epic);

}
