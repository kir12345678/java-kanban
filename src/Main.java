import model.Epic;
import model.Status;
import service.TaskManger;
import model.Task;
import model.SubTask;

import java.util.ArrayList;
import java.util.HashMap;

//import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        //System.out.println("Поехали!");
        // ---------------- создаем и печатаем 2 задачи -------------------
       /*
        TaskManger taskManager = new TaskManger();
        taskManager.save(new Task("Задача1","Задача1", taskManager.genId()));
        taskManager.save(new Task("Задача2","Задача2", taskManager.genId()));
        //System.out.println(taskManager.getTask(1).getName());

        //HashMap<Integer, Task> tasksTest;
        ArrayList<Task> tasksTest;
        tasksTest = taskManager.getAllTasks();
        for (Task task: tasksTest) {
            System.out.println(task.getId() + " " + task.getName());
        }


        // ------ Эпик с 1 подзадачей и эпик с 2мя подзадачами ----------------------
        Epic epic1 = new Epic("Эпик1","Эпик1", taskManager.genId());
        SubTask subTask1 = new SubTask("Подзадача1", "Подзадача1", taskManager.genId(), epic1);
        Epic epic2 = new Epic("Эпик2","Эпик2", taskManager.genId());
        SubTask subTask2 = new SubTask("Подзадача2", "Подзадача2", taskManager.genId(), epic2);
        SubTask subTask3 = new SubTask("Подзадача3", "Подзадача3", taskManager.genId(), epic2);
        taskManager.save(epic1);
        taskManager.save(epic2);
        taskManager.save(subTask1);
        taskManager.save(subTask2);
        taskManager.save(subTask3);

        HashMap<Integer, Epic> allEpics = taskManager.getAllEpics();
        for (Epic epic: allEpics.values()) {
            System.out.println(epic.getId() + " " + epic.getName());
        }
        HashMap<Integer, SubTask> allSubTasks = taskManager.getAllSubTasks();
        for (SubTask subTask: allSubTasks.values()) {
            System.out.println(subTask.getId() + " " + subTask.getName());
        }

        //-------------- статусы ---------------
        subTask2.setStatus(Status.DONE);
        subTask3.setStatus(Status.DONE);
        System.out.println(epic2.getStatus());

        //----------- удаление ---------------
        taskManager.delTask(3);

        for (Task task: tasksTest.values()) {
            System.out.println(task.getId() + " " + task.getName());
        }

        taskManager.delEpic(10);

        for (Epic epic: allEpics.values()) {
            System.out.println(epic.getId() + " " + epic.getName());
        }

        for (SubTask subTask: allSubTasks.values()) {
            System.out.println(subTask.getId() + " " + subTask.getName());
        }
      */

    }
}
