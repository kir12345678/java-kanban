package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task{
    private ArrayList<SubTask> subTasks;

    public Epic(String name, String description, int id) {
        super(name, description, id);
        subTasks = new ArrayList<>();
        //this.subTasks = subTasks;
    }
    public void AddSubTask(SubTask subTask) {
        subTasks.add(subTask);
        updateStatus();
    }
    public void DelSubTask(SubTask subTask) {
        subTasks.remove(subTask);
        updateStatus();
    }

    public void DelAllSubTask() {
        subTasks.clear();
        updateStatus();
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public Status getStatus() {
        return super.getStatus();
    }

    public void updateStatus() {
        int countNew = 0;
        int countDone = 0;

        for (SubTask subTask : subTasks) {
            if (subTask.getStatus() == Status.NEW) {
                countNew++;
            } else if (subTask.getStatus() == Status.DONE) {
                countDone++;
            }
        }

        if (countNew == subTasks.size()) {
            setStatus(Status.NEW);
        } else if (countDone == subTasks.size()) {
            setStatus(Status.DONE);
        } else {
            setStatus(Status.IN_PROGRESS);
        }
    }


}
