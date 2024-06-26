package service;

import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskMangerTest {
    static TaskManager taskManager;

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Subtasks:");
        for (Task task : manager.getAllTasks()) {
            System.out.println("ID = " + task.getId() + " " + task.getName());
        }

        System.out.println("Epics:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println("ID = " + epic.getId() + " " + epic.getName());

           for (SubTask subTask : manager.getEpicSubTasks(epic)) {
                System.out.println("--> " + "ID = " + subTask.getId() + " " + subTask.getName());
            }
        }

        System.out.println("Subtasks:");
        for (SubTask subTask : manager.getAllSubTasks()) {
            System.out.println("ID = " + subTask.getId() + " " + subTask.getName());
        }

        System.out.println("\n" + "History:");
        for (Task task : taskManager.getHistoryManager().getHistory()) {
            System.out.println(task.getId() + " " + task.getName());
        }
    }
    @BeforeAll
    static void  beforeAll() {
        taskManager = Managers.getDefault();

        taskManager.save(new Task("Task1","Task1"));
        taskManager.save(new Task("Task2","Task2"));
        taskManager.save(new Task("Task3","Task3"));

        Epic epic1 = new Epic("Epic1","Epic1");
        Epic epic2 = new Epic("Epic2","Epic2");
        SubTask subTask1 = new SubTask("SubTask1","SubTask1", epic1);
        SubTask subTask2 = new SubTask("SubTask2","SubTask2", epic1);
        taskManager.save(epic1);
        taskManager.save(epic2);
        taskManager.save(subTask1);
        taskManager.save(subTask2);

        taskManager.getTask(1);
        taskManager.getSubTask(5);
        taskManager.getEpic(3);
        taskManager.getTask(1);
        taskManager.getEpic(3);
        taskManager.getEpic(4);
        taskManager.getSubTask(6);
        taskManager.getSubTask(5);

    }
    @Test
    @DisplayName("look at the CMD")
    void shouldPrintAllTasks() {
        printAllTasks(taskManager);
    }
    @Test
    void shouldBehHistoryNotEmpty() {
        assertTrue(taskManager.getHistoryManager().getHistory().size() > 0 , "History is empty.");
    }
    @Test
    void shouldBeLengthHistoryEqual6() {
        assertEquals(taskManager.getHistoryManager().getHistory().size(), 5 , "There are duplicate tasks.");
    }
    @Test
    void shouldBeDeleteIdEqual3() {
        List<Task> history = taskManager.getHistoryManager().getHistory();
        taskManager.getHistoryManager().remove(3);
        for (Task task : taskManager.getHistoryManager().getHistory()) {
            assertFalse(task.getId() == 3 , "The view was not deleted from the history.");
        }
    }

}