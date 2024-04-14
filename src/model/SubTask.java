package model;

public class SubTask extends Task{
    Epic epic;

    public SubTask(String name, String description, int id, Epic epic) {
        super(name, description, id);
        this.epic = epic;
    }

    public Epic getEpic() {
        return this.epic;
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
        this.epic.updateStatus();
    }
}
