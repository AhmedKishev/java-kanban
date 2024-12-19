package interfaces;

import task.Task;
import memory.Node;

import java.util.ArrayList;

public interface HistoryManager {
    void add(Task task);

    ArrayList<Task> getHistory();

    void remove(Long id);

    void removeNode(Node node);
}
