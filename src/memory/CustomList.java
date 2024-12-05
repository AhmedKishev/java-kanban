package memory;

import task.Task;

import java.util.ArrayList;


public class CustomList {
    private Node head;
    private Node tail;


    private Node checkTaskInList(Node newTask) {
        Node tmp = head;
        while (tmp != null) {
            if (tmp.count == newTask.count) return tmp;
            tmp = tmp.next;
        }
        return null;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node tmp = head;
        while (tmp != null) {
            tasks.add(tmp.count);
            tmp = tmp.next;
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
        Node tmp = head;
        while (tmp != null) {
            if (node.count == tmp.count) {
                Node left = tmp.prev;
                Node right = tmp.next;
                if (left != null) {
                    left.next = right;
                }
                if (right != null) {
                    right.prev = left;
                }
                return;
            }
            tmp = tmp.next;
        }
    }
}
