package com.appfire.taskmanagement.mapper;

import com.appfire.taskmanagement.dto.ProjectDTO;
import com.appfire.taskmanagement.model.Project;
import com.appfire.taskmanagement.model.Task;

public class ProjectMapper {
    public static ProjectDTO mapToProjectDTO(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getTasks()
                        .stream()
                        .map(Task::getId)
                        .toList()
        );
    }
}