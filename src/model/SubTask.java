package model;

public class SubTask extends Task{
    Epic epic;
    // А если epic пустой ?
    public SubTask(String name, String description, int id, Epic epic) {
        super(name, description, id);
        this.epic = epic;
    }

    public Epic getEpic() {
        return this.epic;
    }

    /*public void setEpic(Epic epic) {
        this.epic = epic;
    }*/

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
        this.epic.updateStatus();
    }
}
