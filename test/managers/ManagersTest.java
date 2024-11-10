package managers;

import memory.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
@Test
    public void notNull() {
    InMemoryTaskManager inMemoryTaskManager=new InMemoryTaskManager();
    Managers managers=new Managers(inMemoryTaskManager);
    assertNotNull(managers);
}
}