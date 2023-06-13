package org.example.taskUUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TaskRepo taskRepo;
    public List<Task> getTasksByUser(Integer userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return taskRepo.findByUserid(user);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public User saveDetails(User user){ return userRepo.save(user);}
    public List<User> getAllUsers(){return userRepo.findAll();}
    public User updateUser(Integer id, User updatedUser) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Update the task properties with the updatedTask values
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());

            return userRepo.save(user);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }
    public void deleteUser(Integer id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepo.delete(user);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    public Task updateTaskByUser(Integer userId, UUID taskId, Task updatedTask) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Task> tasks = user.getTasks();
            Optional<Task> optionalTask = tasks.stream().filter(task -> task.getId() == taskId).findFirst();
            if (optionalTask.isPresent()) {
                Task task = optionalTask.get();
                // Update the task properties with the updatedTask values
                task.setName(updatedTask.getName());
                task.setDescription(updatedTask.getDescription());
                task.setStatus(updatedTask.getStatus());
                task.setTags(updatedTask.getTags());

                return taskRepo.save(task);
            } else {
                throw new RuntimeException("Task not found with ID: " + taskId);
            }
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public void deleteTaskByUser(Integer userId, UUID taskId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Task> tasks = user.getTasks();
            Optional<Task> optionalTask = tasks.stream().filter(task -> task.getId() == taskId).findFirst();
            if (optionalTask.isPresent()) {
                Task task = optionalTask.get();
                tasks.remove(task);
                taskRepo.delete(task);
            } else {
                throw new RuntimeException("Task not found with ID: " + taskId);
            }
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
    public Task addTaskByUser(Integer userId, Task task) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            task.setUserid(user);
            return taskRepo.save(task);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
    public List<User> saveMultipleUsers(List<User> users) {
        return userRepo.saveAll(users);
    }

    public List<Task> addTasksByUser(Integer userId, List<Task> tasks) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            tasks.forEach(task -> task.setUserid(user));
            return taskRepo.saveAll(tasks);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
    public boolean checkUserExists(Integer userId) {

        Optional<User> user = userRepo.findById(userId);
        return user != null;
    }
}
