package managers;

import interfaces.HistoryManager;
import interfaces.TaskManager;

public class Managers<T extends TaskManager> {
    private static TaskManager taskManager;


    public Managers(T n) {
        this.taskManager = n;
    }

    public TaskManager getDefault() {
        return this.taskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return taskManager.getHistoryManager();
    }
}