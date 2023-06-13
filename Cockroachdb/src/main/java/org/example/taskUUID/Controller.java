package org.example.taskUUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class Controller {
    private static final Logger logger = LogManager.getLogger(Controller.class);
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public User postDetails(@RequestBody User user) {
        return userService.saveDetails(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @GetMapping("/users/{userId}/tasks")
    public List<Task> getTasksByUser(@PathVariable Integer userId) {
        return userService.getTasksByUser(userId);
    }

    @Autowired
    private TaskService taskService;

    @PostMapping("/addTask")
    public Task postDetails(@RequestBody Task task) {
        return taskService.saveDetails(task);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable UUID id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/users/{userId}/tasks/{taskId}")
    public Task updateTaskByUser(@PathVariable Integer userId, @PathVariable UUID taskId, @RequestBody Task updatedTask) {
        return userService.updateTaskByUser(userId, taskId, updatedTask);
    }

    @DeleteMapping("/users/{userId}/tasks/{taskId}")
    public void deleteTaskByUser(@PathVariable Integer userId, @PathVariable UUID taskId) {
        userService.deleteTaskByUser(userId, taskId);
    }
    @PostMapping("/users/{userId}/tasks")
    public Task addTaskByUser(@PathVariable Integer userId, @RequestBody Task task) {
        boolean userExists = userService.checkUserExists(userId);

        if (!userExists) {
            logger.error("User does not exist with userId: {}", userId);
            throw new IllegalArgumentException("User does not exist");
        }
        return userService.addTaskByUser(userId, task);
    }
    @PostMapping("/addUsers")
    public List<User> postMultipleUsers(@RequestBody List<User> users) {
        return userService.saveMultipleUsers(users);
    }

    @PostMapping("/users/{userId}/addTasks")
    public List<Task> addTasksByUser(@PathVariable Integer userId, @RequestBody List<Task> tasks) {
        return userService.addTasksByUser(userId, tasks);
    }

}
