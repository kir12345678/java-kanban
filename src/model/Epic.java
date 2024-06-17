package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> subTasks;

    public Epic(String name, String description) {
        super(name, description);
        subTasks = new ArrayList<>();
    }

    public Epic(int id, String name, String description, Status status, int epicId) {
        super(id, name, description, status);
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
        updateStatus();
    }

    public void delSubTask(SubTask subTask) {
        subTasks.remove(subTask);
        updateStatus();
    }

    public void delAllSubTask() {
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

    @Override
    public Integer getEpicId() {
        return this.getId();
    }

    @Override
    public TypeTask getTypeTask() {
        return TypeTask.EPIC;
    }
}
