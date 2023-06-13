package tasksCrud.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/tasks")
public class TaskController {

    private static final Logger logger = LogManager.getLogger(TaskController.class);

    private final UserRepository userRepository;
    private final JdbcTaskRepository taskRepository;

    @Autowired
    public TaskController(UserRepository userRepository, JdbcTaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@PathVariable int userId, @RequestBody Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", userId);
                    return new ResourceNotFoundException("User not found with id: " + userId);
                });

        task.setUser(user);
        logger.info("Creating a new task: {}", task.getName());

        Task createdTask = taskRepository.createTask(userId, task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasksForUser(@PathVariable int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", userId);
                    return new ResourceNotFoundException("User not found with id: " + userId);
                });

        List<Task> tasks = taskRepository.getAllTasksForUser(userId);
        logger.info("Retrieved {} tasks for user with id: {}", tasks.size(), userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable int userId, @PathVariable int taskId,
                                           @RequestBody Task updatedTask) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", userId);
                    return new ResourceNotFoundException("User not found with id: " + userId);
                });

        Task task = taskRepository.getAllTasksForUser(userId).stream()
                .filter(t -> t.getId() == taskId)
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Task not found with id: {}", taskId);
                    return new ResourceNotFoundException("Task not found with id: " + taskId);
                });

        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setTags(updatedTask.getTags());

        Task updatedTaskObj = taskRepository.updateTask(userId, task);
        logger.info("Updated task with id: {}", taskId);
        return new ResponseEntity<>(updatedTaskObj, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable int userId, @PathVariable int taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", userId);
                    return new ResourceNotFoundException("User not found with id: " + userId);
                });

        Task task = taskRepository.getAllTasksForUser(userId).stream()
                .filter(t -> t.getId() == taskId)
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Task not found with id: {}", taskId);
                    return new ResourceNotFoundException("Task not found with id: " + taskId);
                });

        taskRepository.deleteTask(userId, taskId);
        logger.info("Deleted task with id: {}", taskId);
        return ResponseEntity.noContent().build();
    }
}











//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/users/{userId}/tasks")
//public class TaskController {
//
//    private final UserRepository userRepository;
//    private final JdbcTaskRepository taskRepository;
////    private static final Logger logger = LogManager.getLogger(TaskController.class);
//
//
//    @Autowired
//    public TaskController(UserRepository userRepository, JdbcTaskRepository taskRepository) {
//        this.userRepository = userRepository;
//        this.taskRepository = taskRepository;
//    }
//
//    @PostMapping
//    public ResponseEntity<Task> createTask(@PathVariable int userId, @RequestBody Task task) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
//
//        task.setUser(user);
//        System.out.println("st");
//        System.out.println(task.getTags());
//        Task createdTask = taskRepository.createTask(userId, task);
//        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Task>> getAllTasksForUser(@PathVariable int userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
//
//        List<Task> tasks = taskRepository.getAllTasksForUser(userId);
//        return new ResponseEntity<>(tasks, HttpStatus.OK);
//    }
//
//    @PutMapping("/{taskId}")
//    public ResponseEntity<Task> updateTask(@PathVariable int userId, @PathVariable int taskId,
//                                           @RequestBody Task updatedTask) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
//
//        Task task = taskRepository.getAllTasksForUser(userId).stream()
//                .filter(t -> t.getId() == taskId)
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
//
//        task.setName(updatedTask.getName());
//        task.setDescription(updatedTask.getDescription());
//        task.setStatus(updatedTask.getStatus());
//        task.setTags(updatedTask.getTags());
//
//        Task updatedTaskObj = taskRepository.updateTask(userId, task);
//        return new ResponseEntity<>(updatedTaskObj, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{taskId}")
//    public ResponseEntity<Void> deleteTask(@PathVariable int userId, @PathVariable int taskId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
//
//        Task task = taskRepository.getAllTasksForUser(userId).stream()
//                .filter(t -> t.getId() == taskId)
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
//
//        taskRepository.deleteTask(userId, taskId);
//        return ResponseEntity.noContent().build();
//    }
//}
//
/**
 * 1. Integrate with log4j
 * 2. Check why 500 is coming
 * 3. Check the query cache is enabled in the postgre.
 * 4. Create 10K --> 25k -> 50K -> 100k records
 * 5. Measure the the disk size occupied for the records and indexes
 * 6. Run get with default confg and run the same with disabled query cache
 * 7. Update query , run 5 updates for each record
 * 8. repeat 5
 * 9.pg user statistic index
 * 10.amazon aws rds: deault behaviour of autoVaccum
 * 11.memory usage by the database before and after running put reqest.
 *
 *
 */
//
//
//
//
//
//
//
