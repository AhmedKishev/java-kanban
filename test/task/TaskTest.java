package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private static Task task=new Task("a","b", Status.NEW);
@Test
    public void matchesTask1AndTask2() {
    Task task1=new Task("a","b",Status.NEW);

    assertTrue(task1.equals(task));
}
@Test
    public void notMatchesTask1AndTask2() {
    Task task1=new Task("c","d",Status.IN_PROGRESS);

    assertFalse(task1.equals(task));
}
}