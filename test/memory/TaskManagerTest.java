package memory;

import interfaces.TaskManager;
import org.junit.jupiter.api.Test;

public abstract class TaskManagerTest<T extends TaskManager> {
    @Test
    public abstract void addTask();

    @Test
    public abstract void addSubTask();

    @Test
    public abstract void addEpic();

    @Test
    public abstract void updateTask();

    @Test
    public abstract void updateSubTask();

    @Test
    public abstract void updateEpic();

    @Test
    public abstract void existsSubTaskEpic();

    @Test
    public abstract void IntersectionsOfIntervalsTime();
}
