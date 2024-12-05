package interfaces;

import task.Task;
import memory.Node;

import java.util.ArrayList;


public interface HistoryManager {
    public void add(Task task);

    public ArrayList<Task> getHistory();

    void remove(Long id);

    void removeNode(Node node);
}
