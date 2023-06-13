package org.example.Task;

import org.example.Task.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {
    List<User> findAll();
}
