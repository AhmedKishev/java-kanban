package task;
import java.util.ArrayList;
import status.Status;
public class Epic extends Task {
    private ArrayList<SubTask> subTasks = new ArrayList<>();


    public Epic(String nameOfTask, String description, Status status) {
        super(nameOfTask, description, status);

    }

    public void statusCheck() {
        boolean isNew = true;
        boolean isDone = true;
        for (SubTask tmp : subTasks) {
            if (tmp.getStatus() != status.NEW) isNew = false;
            if (tmp.getStatus() != status.DONE) isDone = false;
        }
        if (isNew && !subTasks.isEmpty()) {
            this.status = status.NEW;
        } else if (isDone && !subTasks.isEmpty()) {
            this.status = status.DONE;
        } else this.status = status.IN_PROGRESS;
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
        statusCheck();
    }

    @Override
    public String toString() {
        return "Epic{" + "subTasks=" + subTasks +
                ", nameOfTask='" + nameOfTask + '\'' +
                ", status=" + status + ", id=" + id +
                ", description='" + description + '\'' + '}';
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }
}
