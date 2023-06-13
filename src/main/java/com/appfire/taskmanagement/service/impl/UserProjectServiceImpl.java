package com.appfire.taskmanagement.service.impl;

import ch.qos.logback.core.joran.action.AppenderRefAction;
import com.appfire.taskmanagement.exception.UserProjectNotFoundException;
import com.appfire.taskmanagement.model.Project;
import com.appfire.taskmanagement.model.Role;
import com.appfire.taskmanagement.model.User;
import com.appfire.taskmanagement.model.UserProject;
import com.appfire.taskmanagement.model.id.UserProjectId;
import com.appfire.taskmanagement.repository.UserProjectRepository;
import com.appfire.taskmanagement.service.UserProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserProjectServiceImpl implements UserProjectService {
    private final UserProjectRepository repository;

    @Override
    public UserProject save(UserProject entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(UserProject entity) {
        repository.delete(entity);
    }

    @Override
    public UserProject findById(UserProjectId id) {
        return repository.findById(id).orElseThrow(UserProjectNotFoundException::new);
    }

    @Override
    public List<Project> findAllNotDeletedProjectsByUserId(String userId) {
        return repository
                .findAllByUserId(userId)
                .stream()
                .filter(project -> !project.isDeleted())
                .map(UserProject::getProject)
                .toList();
    }

    @Override
    public String save(User user, Project project, Role role) {

        UserProject userProject = new UserProject();
        userProject.setId(new UserProjectId(user.getId(),project.getId()));
        userProject.setUser(user);
        userProject.setProject(project);
        userProject.setRole(role);
        return repository.save(userProject).getId().getProjectId();
    }

    @Override
    public List<User> findALlUsersByProjectId(String id){
        return repository.findALlUsersByProjectId(id)
                .stream()
                .filter(userProject -> !userProject.isDeleted())
                .map(UserProject::getUser)
                .toList();
    }

    @Override
    public boolean checkUserIdAndProjectId(String userId, String projectId) {

        return findALlUsersByProjectId(projectId)
                .stream()
                .anyMatch(user -> user.getId().equals(userId));
    }
}
