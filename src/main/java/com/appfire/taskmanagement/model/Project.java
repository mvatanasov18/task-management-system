package com.appfire.taskmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Projects")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Project {

    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Name")
    @NotEmpty(message = "Name may not be empty")
    @Size(min = 1 ,max = 100,message = "Name must be between 1 and 100 characters long")
    private String name;

    @Column(name = "Description")
    @NotEmpty(message = "Description may not be empty")
    @Size(min = 1 ,max = 500,message = "Name must be between 1 and 500 characters long")
    private String description;

    @Column(name = "IsDeleted")
    @NotNull
    private boolean isDeleted;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    public Project() {
        this.id = UUID.randomUUID().toString();
        name="";
        description = "";
        isDeleted=false;
        tasks = new ArrayList<>();
    }

}
