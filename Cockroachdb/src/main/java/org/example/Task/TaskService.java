package org.example.Task;

import org.example.Task.Task;
import org.example.Task.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;

    public Task saveDetails(Task task) {
        return taskRepo.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public Task updateTask(Integer id, Task updatedTask) {
        Optional<Task> optionalTask = taskRepo.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            // Update the task properties with the updatedTask values
            task.setName(updatedTask.getName());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            task.setTags(updatedTask.getTags());

            return taskRepo.save(task);
        } else {
            throw new RuntimeException("Task not found with ID: " + id);
        }
    }

    public void deleteTask(Integer id) {
        Optional<Task> optionalTask = taskRepo.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            taskRepo.delete(task);
        } else {
            throw new RuntimeException("Task not found with ID: " + id);
        }
    }
}
