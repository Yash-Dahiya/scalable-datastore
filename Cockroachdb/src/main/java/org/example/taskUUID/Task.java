package org.example.taskUUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Entity
@Data
@Table(name="tasks_uuid")
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

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
