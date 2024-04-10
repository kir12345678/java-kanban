package model;

import java.util.HashMap;

public class Epic extends Task{
    private HashMap<Integer, SubTask> subTasks;

    public Epic(String name, String description, int id, Status status, HashMap<Integer, SubTask> subTasks) {
        super(name, description, id, status);
        this.subTasks = subTasks;
    }
}
