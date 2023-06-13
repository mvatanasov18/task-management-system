package com.appfire.taskmanagement.mapper;

import com.appfire.taskmanagement.dto.TaskDTO;
import com.appfire.taskmanagement.model.Task;

public class TaskMapper {
    public static TaskDTO mapTaskToTaskDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getProject().getId(),
                task.getStatus(),
                task.isDeleted());
    }
}
