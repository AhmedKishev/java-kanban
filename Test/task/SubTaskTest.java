package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    public static Epic epic;
    public static SubTask subTask;
    @BeforeEach
    public void createEpicAndSubTask1() {
        epic=new Epic("a","b", Status.IN_PROGRESS);
        subTask=new SubTask("c","d",Status.NEW,epic);
    }
@Test
public void notMatchesSubTask1AndSubTask2() {
    SubTask subTask1 = new SubTask("e", "f", Status.NEW, epic);

    assertFalse(subTask1.equals(subTask));
}
@Test
    public void matchesSubTask1AndSubTask2() {
    SubTask subTask1 = new SubTask("c", "d", Status.NEW, epic);

    assertTrue(subTask1.equals(subTask));
}
@Test
    public void SubTaskInConstuctorSubTask() {
        SubTask subTask1=new SubTask("a","b",Status.NEW,subTask);
        assertNull(subTask1.getEpic());
}
}