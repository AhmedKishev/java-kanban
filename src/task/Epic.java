package task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import status.Status;

public class Epic extends Task {
    private ArrayList<SubTask> subTasks = new ArrayList<>();
    private LocalDateTime endTime = LocalDateTime.now();

    public Epic(String nameOfTask, String description, Status status) {
        super(nameOfTask, description, status);
    }

    public Epic(Epic epic) {
        super(epic.nameOfTask, epic.description, epic.status, epic.startTime, epic.duration);
        endTime = startTime.plus(duration);
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

    public String addSubTask(Object subTask) {
        String a = subTask.getClass().toString();
        if (!subTask.getClass().toString().equals("class task.SubTask")) {
            return "error";
        } else {
            SubTask subTask1 = (SubTask) subTask;
            subTasks.add(subTask1);
            if (subTask1.getStartTime() != null) {
                findEndTime();
                findStartTime();
            }
            statusCheck();
            return "not error";
        }
    }

    public void findEndTime() {
        Comparator<SubTask> comparatorForSubTask = (o1, o2) -> {
            if (o1.getStartTime().isBefore(o2.getStartTime())) return 1;
            return -1;
        };
        List<SubTask> subTasksWithTime = subTasks.stream().filter(
                subTask -> subTask.getStartTime() != null
        ).collect(Collectors.toList());
        subTasksWithTime.sort(comparatorForSubTask);
        endTime = subTasksWithTime.getFirst().getEndTime();
    }


    public void findStartTime() {
        Comparator<SubTask> comparatorForSubTask = (o1, o2) -> {
            if (o2.getStartTime().isBefore(o1.getStartTime())) return 1;
            else return -1;
        };
        List<SubTask> subTasksWithTime = subTasks.stream().filter(
                subTask -> subTask.getStartTime() != null
        ).collect(Collectors.toList());
        subTasksWithTime.sort(comparatorForSubTask);
        startTime = subTasksWithTime.getFirst().getStartTime();
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        if (startTime != null) {
            return nameOfTask + "," + status + "," + description + "," + startTime + "," + endTime;
        }
        return nameOfTask + "," + status + "," + description;
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public void removeSubTask(SubTask subTask) {
        subTasks.remove(subTask);
    }

}
