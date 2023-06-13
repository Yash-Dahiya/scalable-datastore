package org.example.Task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Objects;
import java.util.UUID;


@Entity
@Data
@Table(name="task")
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @JsonIgnoreProperties("tasks")
    private User userid;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;


    @Column(name = "status")
    private String status;

    @Column(name="tags")
    private String[] tags;

}
