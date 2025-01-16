package memory;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class FileBackedTaskManagerTest {

    @Test
    public void testException() {
        assertDoesNotThrow(() -> {
            File file = new File("C:/Users/Astemir/IdeaProjects/java-kanban/src/filesformanager/FileBackedTaskManager.txt");
            InMemoryTaskManager inMemoryTaskManager = FileBackedTaskManager.loadFromFile(file);
        }, "Не удалось открыть файл");
    }

}
