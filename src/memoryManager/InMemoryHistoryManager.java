package memoryManager;

import historyManagerInterface.HistoryManager;
import task.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private ArrayList<Task> history=new ArrayList<>();
    @Override
    public void add(Task task) {
   if (history.size()==11) {
   history.remove(10);
   }
   history.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        if (history.isEmpty()) return null;
        else {
            ArrayList<Task> tmp = new ArrayList<>();
            for (int i = 0; i < history.size(); i++) {
                if (i == 10) break;
                tmp.add(history.get(i));
            }
            return tmp;
        }
    }
}
