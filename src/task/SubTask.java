package task;
import status.Status;


public class SubTask extends Task {
    private Epic epic;


    public SubTask(String nameOfTask, String description, Status status, Epic epic) {
        super(nameOfTask, description, status);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "nameOfTask='" + nameOfTask + '\'' +
                ", status=" + status +
                ", id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
