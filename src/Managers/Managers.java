package Managers;

import HistoryManagerInterface.HistoryManager;
import MemoryManager.InMemoryHistoryManager;
import interfaceManager.TaskManager;

public class Managers <T extends TaskManager> {
    private static TaskManager taskManager;
    public Managers (T n) {
        this.taskManager=n;
    }
    public TaskManager getDefault() {
       return this.taskManager;
    }
    public static HistoryManager getDefaultHistory () {
        return taskManager.getHistoryManager();
    }
}
