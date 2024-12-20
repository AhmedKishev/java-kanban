package task;

import status.Status;


public class SubTask extends Task {
    private Epic epic;


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
        return id + ",SubTask," + nameOfTask + "," + status + "," + description + "," + epic.getNameOfTask();
    }
}
