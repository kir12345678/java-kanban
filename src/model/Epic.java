package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

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

    @Override
    public LocalDateTime getStartTime() {
        if (!subTasks.isEmpty()) {
            subTasks.sort(Comparator.comparing(Task::getStartTime));
            return subTasks.get(0).getStartTime();
        } else {
            return LocalDateTime.of(3000, 1, 1, 0, 0);
        }
    }

    @Override
    public LocalDateTime getEndTime() {
        if (!subTasks.isEmpty()) {
            subTasks.sort(Comparator.comparing(Task::getStartTime));
            return subTasks.get(subTasks.size() - 1).getEndTime();
        } else {
            return null;
        }
    }

    @Override
    public Duration getDuration() {
        Duration totalDuration = Duration.ofMinutes(0);
        for (SubTask subTask : subTasks) {
            totalDuration = totalDuration.plus(subTask.getDuration());
        }
        return totalDuration;
    }
}
