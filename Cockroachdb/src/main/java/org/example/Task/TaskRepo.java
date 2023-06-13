package org.example.Task;

import org.example.Task.Task;
import org.example.Task.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task,Integer> {
    List<Task> findAll();
    List<Task> findByUserid(User user);
}
