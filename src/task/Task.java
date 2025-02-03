package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import status.Status;

public class Task {
    protected String nameOfTask;
    protected Status status;
    protected final long id;
    protected String description;
    protected Duration duration = Duration.ZERO;
    protected LocalDateTime startTime = LocalDateTime.now();


    public LocalDateTime getStartTime() {
        return this.startTime;
    }


    public Task(Task task) {
        this.nameOfTask = task.nameOfTask;
        this.status = task.status;
        this.description = task.description;
        if (!task.startTime.equals(null)) {
            this.startTime = task.startTime;
            this.duration = task.duration;
        }
        this.id = hashCode();
    }

    public Task(String nameOfTask, String description, Status status, LocalDateTime startTime, Duration duration) {
        this.nameOfTask = nameOfTask;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        id = hashCode();
    }

    public Task(String nameOfTask, String description, Status status) {
        this.nameOfTask = nameOfTask;
        this.description = description;
        this.status = status;
        id = hashCode();
    }

    public String getDescription() {
        return this.description;
    }

    public String getNameOfTask() {
        return this.nameOfTask;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        if (startTime != null) {
            return nameOfTask + "," + description + "," + status + ","
                    + "," + startTime + "," + startTime.plus(duration) + ",";
        }
        return nameOfTask + "," + description + "," + status + ",";
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfTask, status, description);
    }

}
