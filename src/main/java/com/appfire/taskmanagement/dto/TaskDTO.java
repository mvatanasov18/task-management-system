package com.appfire.taskmanagement.dto;

import com.appfire.taskmanagement.model.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record TaskDTO (
        String id,
        @NotEmpty(message = "Name may not be empty")
        @Size(min = 1 ,max = 100,message = "Name must be between 1 and 100 characters long")
        String name,
        @NotEmpty(message = "Description may not be empty")
        @Size(min = 1 ,max = 500,message = "Description must be between 1 and 500 characters long")
        String description,
        @NotEmpty(message = "ProjectId may not be empty")
        @NotNull
        @Size(min = 36 ,max=36, message = "ProjectId must be 36 characters long")
        String projectId,
        Status status,
        boolean isDeleted
){
}
