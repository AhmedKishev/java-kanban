package task;

import java.util.Objects;

import status.Status;

public class Task {
    protected String nameOfTask;
    protected Status status;
    protected final long id;
    protected String description;


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

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return id + ",Task," + nameOfTask + "," + status + ",Description:" + description + ",";
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
