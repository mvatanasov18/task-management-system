package com.appfire.taskmanagement.model;

import com.appfire.taskmanagement.model.id.UserProjectId;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "UsersProjects")
@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserProject {
    @EmbeddedId
    private UserProjectId id;

    @Column(name = "Role")
    @NonNull
    private Role role;

    @Column(name = "IsDeleted")
    @NonNull
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "UserId")
    @ToString.Exclude
    @MapsId("userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ProjectId")
    @ToString.Exclude
    @MapsId("projectId")
    private Project project;
    public UserProject() {
        id = new UserProjectId();
        id.setProjectId("");
        id.setUserId("");
        role=Role.USER;
        isDeleted=false;
    }
}
