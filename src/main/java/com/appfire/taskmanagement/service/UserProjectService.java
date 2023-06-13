package com.appfire.taskmanagement.service;

import com.appfire.taskmanagement.model.Project;
import com.appfire.taskmanagement.model.Role;
import com.appfire.taskmanagement.model.User;
import com.appfire.taskmanagement.model.UserProject;
import com.appfire.taskmanagement.model.id.UserProjectId;

import java.util.List;

public interface UserProjectService extends Service<UserProject, UserProjectId> {
    List<Project> findAllNotDeletedProjectsByUserId(String id);
    String save(User user, Project project, Role role);
    List<User> findALlUsersByProjectId(String projectId);

    //used to check if a user is part of a given project
    //requirements: the project must be NOT be marked as deleted
    //returns true - the user is part of an active project
    //everything else: false
    boolean checkUserIdAndProjectId(String userId, String id);
}
