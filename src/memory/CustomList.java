package memory;

import task.Task;

import java.util.ArrayList;


public class CustomList {
    private Node head;
    private Node tail;


    private Node checkTaskInList(Node newTask) {
        Node It = head;
        while (It != null) {
            if (It.count == newTask.count) return It;
            It = It.next;
        }
        return null;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node It = head;
        while (It != null) {
            tasks.add(It.count);
            It = It.next;
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
        Node It = head;
        while (It != null) {
            if (node.count == It.count) {
                Node left = It.prev;
                Node right = It.next;
                if (left != null) {
                    left.next = right;
                }
                if (right != null) {
                    right.prev = left;
                }
                return;
            }
            It = It.next;
        }
    }
}
