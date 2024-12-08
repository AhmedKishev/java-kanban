package task;

import org.junit.jupiter.api.Test;
import status.Status;


import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private static Task original = new Task("a", "b", Status.NEW);


    @Test
    public void matchesTask1AndTask2() {
        Task comparable = new Task("a", "b", Status.NEW);
        assertTrue(comparable.equals(original));
    }

    @Test
    public void notMatchesTask1AndTask2() {
        Task comparable = new Task("c", "d", Status.IN_PROGRESS);
        assertFalse(comparable.equals(original));
    }
}