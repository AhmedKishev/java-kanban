package CustomExceptions;

import java.io.IOException;

public class ManagerSaveException extends IOException {
    public ManagerSaveException(String exc) {
        super(exc);
    }
}
