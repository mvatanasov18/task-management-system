package com.appfire.taskmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;
@Table(name = "Users")
@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Username")
    @NotEmpty(message = "Username may not be empty")
    @Size(min = 1 ,max = 100,message = "Username must be between 1 and 100 characters long")
    private String username;

    @Column(name = "Password")
    @NotEmpty(message = "Password may not be empty")
    @Size(min = 1 ,max = 100,message = "Password must be between 1 and 100 characters long")
    private String password;

    @Column(name = "FirstName")
    @NotEmpty(message = "First name may not be empty")
    @Size(min = 1 ,max = 100,message = "First name must be between 1 and 100 characters long")
    private String firstName;

    @Column(name = "LastName")
    @NotEmpty(message = "Last name may not be empty")
    @Size(min = 1 ,max = 100,message = "Last name must be between 1 and 100 characters long")
    private String lastName;

    @Column(name = "Salt")
    private byte[] salt;

    @Column(name = "IsDeleted")
    @NonNull
    private boolean isDeleted;

    public User() {
        this.id = UUID.randomUUID().toString();
        username = "";
        password="";
        firstName="";
        lastName="";
        salt=new byte[16];
        isDeleted=false;
    }
}
