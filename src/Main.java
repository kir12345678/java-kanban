import model.Status;
import service.TaskManger;
import model.Task;

import java.util.HashMap;

//import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        //System.out.println("Поехали!");
        TaskManger taskManager = new TaskManger();
        taskManager.create(new Task("Зад1","первая задача", taskManager.genId(), Status.NEW));
        taskManager.create(new Task("Зад2","вторая задача", taskManager.genId(), Status.NEW));
        System.out.println(taskManager.getTask(1).getName());

        HashMap<Integer, Task> tasksTest /*= new HashMap<>()*/;
        tasksTest = taskManager.getAllTasks();
        for (Task task: tasksTest.values()) {
            System.out.println(task.getId() + " " + task.getName());
        }



    }
}
