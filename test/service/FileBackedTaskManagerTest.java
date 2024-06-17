package service;
import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest {
    @Test
    void ShouldBeTaskSaveToFileEqual1() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(inMemoryHistoryManager, "resources\\task.csv");

        fileBackedTaskManager.save(new Task("Task1","Task1"));

        Epic epic1 = new Epic("Epic1","Epic1");
        SubTask subTask1 = new SubTask("SubTask1","SubTask1", epic1);

        fileBackedTaskManager.save(subTask1);
        fileBackedTaskManager.save(epic1);

        assertEquals(1, fileBackedTaskManager.getAllTasks().size());
    }
    @Test
    void ShouldBeSubTaskSaveToFileEqual1() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(inMemoryHistoryManager, "resources\\task.csv");

        fileBackedTaskManager.save(new Task("Task1","Task1"));

        Epic epic1 = new Epic("Epic1","Epic1");
        SubTask subTask1 = new SubTask("SubTask1","SubTask1", epic1);

        fileBackedTaskManager.save(subTask1);
        fileBackedTaskManager.save(epic1);

        assertEquals(1, fileBackedTaskManager.getAllSubTasks().size());
    }
    @Test
    void ShouldBeEpicSaveToFileEqual1() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(inMemoryHistoryManager, "resources\\task.csv");

        fileBackedTaskManager.save(new Task("Task1","Task1"));

        Epic epic1 = new Epic("Epic1","Epic1");
        SubTask subTask1 = new SubTask("SubTask1","SubTask1", epic1);

        fileBackedTaskManager.save(subTask1);
        fileBackedTaskManager.save(epic1);

        assertEquals(1, fileBackedTaskManager.getAllEpics().size());
    }
}
