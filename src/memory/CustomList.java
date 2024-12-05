package memory;

import task.Task;

import java.util.ArrayList;


public class CustomList {
    private Node head;
    private Node tail;


    private Node checkTaskInList(Node newTask) {
        Node it = head;
        while (it != null) {
            if (it.count == newTask.count) return it;
            it = it.next;
        }
        return null;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node it = head;
        while (it != null) {
            tasks.add(it.count);
            it = it.next;
        }
        return tasks;
    }

    public void linkLast(Node newTask) {
        if (head == null) {
            head = newTask;
            tail = head;
            head.prev = null;
            tail.prev = null;
        } else {
            Node isTask = checkTaskInList(newTask);
            if (isTask != null) {
                Node leftTask = isTask.prev;
                Node rightTask = isTask.next;
                if (leftTask != null) {
                    leftTask.next = rightTask;
                }
                if (rightTask != null) {
                    rightTask.prev = leftTask;
                }

                isTask.prev = tail;
                if (isTask == head) {
                    head = isTask.next;
                    tail.next = isTask;
                    tail = isTask;
                    tail.next = null;
                }
                if (leftTask == head) {
                    head.next = isTask;
                    isTask.prev = head;
                }
            } else {
                newTask.prev = tail;
                tail.next = newTask;
                tail = newTask;
            }
        }
    }

    public void remove(Node node) {
        Node it = head;
        while (it != null) {
            if (node.count == it.count) {
                Node left = it.prev;
                Node right = it.next;
                if (left != null) {
                    left.next = right;
                }
                if (right != null) {
                    right.prev = left;
                }
                return;
            }
            it = it.next;
        }
    }
}
