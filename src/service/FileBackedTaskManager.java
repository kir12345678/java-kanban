package service;

import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTaskManager extends InMemoryTaskManager{
    private final String filename;

    private void createFile(String filename) {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new ManagerSaveException("Не удалось создать фвйл " + filename);
            }
        }
    }

    public FileBackedTaskManager(HistoryManager newHistoryManager, String newFilename) {
        super(newHistoryManager);
        this.filename = newFilename;

        createFile(filename);
    }

    public FileBackedTaskManager(String newFilename) {
        super(new InMemoryHistoryManager());
        this.filename = newFilename;

        createFile(filename);
    }

    public String toString(Task task) {
        return String.format("%d, %S, %s, %S, %s, %s", task.getId(), task.getTypeTask(), task.getName(),
                task.getStatus(), task.getDescription(), task.getEpicId());
    }

    public void save() throws ManagerSaveException {
        try (FileWriter writer = new FileWriter(filename, StandardCharsets.UTF_8)) {
            writer.append("id, type, name, status, description, epic" + "\n");
            for (Task task : getAllTasks()) {
                writer.write(toString(task) + "\n");
            }
            for (Task task : getAllEpics()) {
                writer.write(toString(task) + "\n");
            }
            for (Task task : getAllSubTasks()) {
                writer.write(toString(task) + "\n");
            }

        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    private void readFromFile() {
        int maxId = 0;
        try ( FileReader reader = new FileReader(filename, StandardCharsets.UTF_8);
              BufferedReader bufferedReader = new BufferedReader(reader) ) {
            bufferedReader.readLine();
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                Task task = fromString(line);

                int id = task.getId();
                if (task.getTypeTask()==TypeTask.TASK) {
                    tasks.put(id, task);
                } else if (task.getTypeTask()==TypeTask.SUBTASK) {
                    subTasks.put(id, (SubTask) task);
                } else if (task.getTypeTask()==TypeTask.EPIC) {
                    epics.put(id, (Epic) task);
                }
                if (maxId < id) {
                    maxId = id;
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
        seq = maxId;
    }

    public static FileBackedTaskManager loadFromFile(String filename) throws ManagerSaveException {
        FileBackedTaskManager fileBackedTasksManager = new FileBackedTaskManager(filename);

        fileBackedTasksManager.readFromFile();

        return fileBackedTasksManager;
    }

    public Task fromString(String str) {
        String[] arrayField = str.split(", ");

        int taskId = Integer.parseInt(arrayField[0]);
        TypeTask typeTask = TypeTask.valueOf(arrayField[1]);
        String taskName = arrayField[2];
        Status taskStatus = Status.valueOf(arrayField[3]);
        String taskDescription = arrayField[4];
        int taskEpicId = Integer.parseInt(arrayField[5]);

        Task task = null;
        switch (typeTask) {
            case TASK:
                task = new Task(taskId, taskName, taskDescription, taskStatus);
                break;
            case SUBTASK:
                task = new SubTask(taskId, taskName, taskDescription, taskStatus, taskEpicId);
                break;
            case EPIC:
                task = new Epic(taskId, taskName, taskDescription, taskStatus, taskEpicId);
                break;
        }
        return task;
    }

    @Override
    public Task save(Task task) {
       Task taskLoc = super.save(task);
       save();
       return taskLoc;
    }

    @Override
    public SubTask save(SubTask subTask) {
        SubTask subTaskLoc = super.save(subTask);
        save();
        return subTaskLoc;
    }

    @Override
    public Epic save(Epic epic) {
        Epic epicLoc = super.save(epic);
        save();
        return epicLoc;
    }

    @Override
    public void delTask(int id) {
        super.delTask(id);
        save();
    }

    @Override
    public void delSubTask(int id) {
        super.delSubTask(id);
        save();
    }

    @Override
    public void delEpic(int id) {
        super.delEpic(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

}
