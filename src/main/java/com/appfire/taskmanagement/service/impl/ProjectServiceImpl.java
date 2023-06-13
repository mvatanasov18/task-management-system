package com.appfire.taskmanagement.service.impl;

import com.appfire.taskmanagement.exception.ProjectNotFoundException;
import com.appfire.taskmanagement.model.Project;
import com.appfire.taskmanagement.model.Status;
import com.appfire.taskmanagement.repository.ProjectRepository;
import com.appfire.taskmanagement.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    @Override
    public void updateById(String id, Project project) {
        repository.update(id,project.getName(),project.getDescription(),project.isDeleted());
    }


    @Override
    public Project save(Project entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Project entity) {
        entity.setDeleted(true);
        updateById(entity.getId(),entity);
    }

    @Override
    public Project findById(String id) {
        return repository.findById(id).orElseThrow(ProjectNotFoundException::new);
    }
}
