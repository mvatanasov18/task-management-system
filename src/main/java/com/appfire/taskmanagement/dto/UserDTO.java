package com.appfire.taskmanagement.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotEmpty(message = "Username may not be empty")
        @Size(min = 1 ,max = 100,message = "Username must be between 1 and 100 characters long")
        String username,
        @NotEmpty(message = "Password may not be empty")
        @Size(min = 1 ,max = 100,message = "Password must be between 1 and 100 characters long")
        String password,
        @NotEmpty(message = "First name may not be empty")
        @Size(min = 1 ,max = 100,message = "First name must be between 1 and 100 characters long")
        String firstName,
        @NotEmpty(message = "Last name may not be empty")
        @Size(min = 1 ,max = 100,message = "Last name must be between 1 and 100 characters long")
        String lastName
) { }