package com.appfire.taskmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Sessions")
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Session {
    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "TimeCreated")
    private LocalDateTime timeCreated;

    @OneToOne
    @JoinColumn(name = "UserId",referencedColumnName = "Id")
    private User user;

    public Session() {
        id = UUID.randomUUID().toString();
    }
}
