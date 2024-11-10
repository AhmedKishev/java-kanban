package task;

import org.junit.jupiter.api.Test;
import status.Status;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    public static Epic epic=new Epic("a","b", Status.NEW);

 @Test
 public void notMatchesEpic1AndEpic2() {
   Epic epic1=new Epic("c","d",Status.NEW);

   assertFalse(epic1.equals(epic));
}
@Test
    public void matchesEpic1AndEpic2() {
     Epic epic1=new Epic("a","b",Status.NEW);

     assertTrue(epic1.equals(epic));
}
    @Test
    public void addSubTaskNotError() {
     SubTask subTask1=new SubTask("a","b",Status.NEW,epic);

        assertEquals("not error",epic.addSubTask(subTask1));
    }
    @Test
    public void addSubTaskError() {
        assertEquals("error",epic.addSubTask(epic));
    }
}