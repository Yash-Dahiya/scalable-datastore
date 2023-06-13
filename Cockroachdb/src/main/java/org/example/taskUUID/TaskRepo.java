package org.example.taskUUID;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepo extends JpaRepository<Task, UUID> {
    List<Task> findAll();
    List<Task> findByUserid(User user);
}
