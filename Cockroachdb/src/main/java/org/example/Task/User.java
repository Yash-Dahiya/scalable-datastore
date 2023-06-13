package org.example.Task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Task.Task;

import java.util.List;

@Entity
@Data
@Table(name="user_table")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;
    // Additional constructor
    public User(int id) {
        this.id = id;
    }

    // Additional factory method
    public static User of(int id) {
        return new User(id);
    }

    @OneToMany(mappedBy = "userid", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Task> tasks;
}


