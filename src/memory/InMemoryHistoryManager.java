package memory;

import interfaces.HistoryManager;
import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private CustomList customList = new CustomList();
    private Map<Long, Node> tasks = new HashMap<>();

    @Override
    public void add(Task task) {
        Node node = new Node(task);
        customList.linkLast(node);
        if (tasks.containsKey(task.getId())) remove(task.getId());
        tasks.put(task.getId(), node);
    }

    @Override
    public ArrayList<Task> getHistory() {
        if (customList.getTasks().isEmpty()) return null;
        else {
            return customList.getTasks();
        }
    }

    @Override
    public void remove(Long id) {
        tasks.remove(id);

    }


    public void removeNode(Node node) {
        customList.remove(node);
    }


}
