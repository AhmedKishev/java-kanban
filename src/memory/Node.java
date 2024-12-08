package memory;

import task.Task;

public class Node {
    Node next;
    Node prev;
    Task count;

    public Node(Task count) {
        this.count = count;
    }
}
