package memory;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class FileBackedTaskManagerTest {

    @Test
    public void testException() {
        assertDoesNotThrow(() -> {
            File filePath = new File("Save");
            filePath.mkdir();
            File file = new File(filePath + "\\test.txt");
            file.createNewFile();
            InMemoryTaskManager inMemoryTaskManager = FileBackedTaskManager.loadFromFile(file);
        }, "Не удалось открыть файл");
    }

}
