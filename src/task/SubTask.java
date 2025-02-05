package task;

import status.Status;

import java.time.Duration;
import java.time.LocalDateTime;


public class SubTask extends Task {
    private Epic epic;


    public SubTask(String nameOfTask, String description, Status status, Object object, Duration duration, LocalDateTime startTime) {
        super(nameOfTask, description, status, startTime, duration);
        String a = object.getClass().toString();
        if (a.equals("class task.Epic")) {
            this.epic = (Epic) object;
        }
    }


    public SubTask(String nameOfTask, String description, Status status, Object object) {
        super(nameOfTask, description, status);
        String a = object.getClass().toString();
        if (a.equals("class task.Epic")) {
            this.epic = (Epic) object;
        }
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Object o) {
        String a = o.getClass().toString();
        if (a.equals("class task.Epic")) {
            this.epic = epic;
        }

    }

    @Override
    public String toString() {
        if (this.startTime != null) {
            return id + ",SubTask," + nameOfTask + "," + status + "," + description + "," + epic.getNameOfTask()
                    + "," + startTime + "," + startTime.plus(duration) + ",";
        }
        return id + ",SubTask," + nameOfTask + "," + status + "," + description + "," + epic.getNameOfTask();
    }
}
