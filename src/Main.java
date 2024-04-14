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
        //System.out.println("�������!");
        // ---------------- ������� � �������� 2 ������ -------------------
       /*
        TaskManger taskManager = new TaskManger();
        taskManager.save(new Task("������1","������1", taskManager.genId()));
        taskManager.save(new Task("������2","������2", taskManager.genId()));
        //System.out.println(taskManager.getTask(1).getName());

        //HashMap<Integer, Task> tasksTest;
        ArrayList<Task> tasksTest;
        tasksTest = taskManager.getAllTasks();
        for (Task task: tasksTest) {
            System.out.println(task.getId() + " " + task.getName());
        }


        // ------ ���� � 1 ���������� � ���� � 2�� ����������� ----------------------
        Epic epic1 = new Epic("����1","����1", taskManager.genId());
        SubTask subTask1 = new SubTask("���������1", "���������1", taskManager.genId(), epic1);
        Epic epic2 = new Epic("����2","����2", taskManager.genId());
        SubTask subTask2 = new SubTask("���������2", "���������2", taskManager.genId(), epic2);
        SubTask subTask3 = new SubTask("���������3", "���������3", taskManager.genId(), epic2);
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

        //-------------- ������� ---------------
        subTask2.setStatus(Status.DONE);
        subTask3.setStatus(Status.DONE);
        System.out.println(epic2.getStatus());

        //----------- �������� ---------------
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
