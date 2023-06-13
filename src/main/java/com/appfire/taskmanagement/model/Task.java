package com.appfire.taskmanagement.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Tasks")
@AllArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Name")
    @NotEmpty(message = "Name may not be empty")
    @Size(min = 1 ,max = 100,message = "Name must be between 1 and 100 characters long")
    private String name;

    @Column(name = "Description")
    @NotEmpty(message = "Description may not be empty")
    @Size(min = 1 ,max = 500,message = "Description must be between 1 and 500 characters long")
    private String description;

    @Column(name = "Status", columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "ProjectId")
    private Project project;

    @Column(name = "IsDeleted")
    @NotNull
    private boolean isDeleted;
    public Task() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
