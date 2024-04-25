package service;

import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskMangerTest {
    static TaskManager taskManager;

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println("ID = " + task.getId() + " " + task.getName());
        }

        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println("ID = " + epic.getId() + " " + epic.getName());

           for (SubTask subTask : manager.getEpicSubTasks(epic)) {
                System.out.println("--> " + "ID = " + subTask.getId() + " " + subTask.getName());
            }
        }

        System.out.println("Подзадачи:");
        for (SubTask subTask : manager.getAllSubTasks()) {
            System.out.println("ID = " + subTask.getId() + " " + subTask.getName());
        }

        System.out.println("История:");
        for (Task task : taskManager.getHistoryManager().getHistory()) {
            System.out.println(task.getName());
        }
    }
    @BeforeAll
    static void  beforeAll() {
        taskManager = Managers.getDefault();

        taskManager.save(new Task("Задача1","Задача1"));
        taskManager.save(new Task("Задача2","Задача2"));

        Epic epic1 = new Epic("Epic1","Epic1");
        SubTask subTask1 = new SubTask("SubTask1","SubTask1", epic1);
        SubTask subTask2 = new SubTask("SubTask2","SubTask2", epic1);
        taskManager.save(epic1);
        taskManager.save(subTask1);
        taskManager.save(subTask2);

        taskManager.getTask(1);
        taskManager.getSubTask(3);
        taskManager.getEpic(2);
        taskManager.getTask(1);
        taskManager.getEpic(2);
        taskManager.getSubTask(3);
        taskManager.getEpic(2);
        taskManager.getTask(1);
        taskManager.getEpic(2);
        taskManager.getEpic(2);
        taskManager.getEpic(2);


    }
    @Test
    @DisplayName("look at the CMD")
    void shouldPrintAllTasks() {
        printAllTasks(taskManager);
    }
    @Test
    void ShouldBeFillFullHistory(){
        assertEquals(taskManager.getHistoryManager().getHistory().size(), 10,"История не заполнена.");
    }
    @Test
    void shouldBeFirstTaskIsSubTask1() {
        List<Task> history = taskManager.getHistoryManager().getHistory();
        assertEquals(history.get(0).getName(), "SubTask1", "На первом месте не SubTask1. Не было сдвига в истории.");
    }
}