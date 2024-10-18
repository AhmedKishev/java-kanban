public class Main {

    public static void main(String[] args) {
        Task task = new Task("Доделать дз", "Что то там", Status.NEW);
        Task task1 = new Task("Пойти в магазин", "Тоже что то там", Status.NEW);
        Epic epic = new Epic("A", "A", Status.NEW);
        SubTask subTask = new SubTask("B", "B", Status.NEW, epic);
        SubTask subTask1 = new SubTask("C", "C", Status.NEW, epic);
        epic.addSubTask(subTask);
        epic.addSubTask(subTask1);
        Epic epic1 = new Epic("D", "D", Status.NEW);
        SubTask subTask2 = new SubTask("E", "E", Status.NEW, epic1);
        epic1.addSubTask(subTask2);
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task);
        taskManager.addTask(task1);
        taskManager.addEpic(epic);
        taskManager.addEpic(epic1);
        taskManager.addSubTasks(subTask);
        taskManager.addSubTasks(subTask1);
        taskManager.addSubTasks(subTask2);
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubTasks());
        System.out.println(taskManager.getAllEpics());
        task.setStatus(Status.DONE);
        task1.setStatus(Status.IN_PROGRESS);
        subTask.setStatus(Status.DONE);
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);
        taskManager.updateTask(task);
        taskManager.updateTask(task1);
        taskManager.updateSubTask(subTask);
        taskManager.updateSubTask(subTask1);
        taskManager.updateSubTask(subTask2);
        System.out.println();
        System.out.println();
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubTasks());
        System.out.println(taskManager.getAllEpics());
        taskManager.deleteOfIdTask(task);
        taskManager.deleteOfIdSubTask(subTask2);
        System.out.println();
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getOfIdTask(task1));
    }
}
