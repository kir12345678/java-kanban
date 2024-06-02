package model;

public class SubTask extends Task {
    Epic epic;

    public SubTask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;
    }

    public SubTask(int id, String name, String description, Status status, int epicId) {
        super(id, name, description, status);
    }

    public Epic getEpic() {
        return this.epic;
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
        this.epic.updateStatus();
    }
    @Override
    public TypeTask getTypeTask() {
        return TypeTask.SUBTASK;
    }
    @Override
    public Integer getEpicId() {
        return epic.getId();
    }
}
