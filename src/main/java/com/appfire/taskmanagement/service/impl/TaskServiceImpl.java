package com.appfire.taskmanagement.service.impl;

import com.appfire.taskmanagement.exception.TaskNotFoundException;
import com.appfire.taskmanagement.model.Project;
import com.appfire.taskmanagement.model.Status;
import com.appfire.taskmanagement.model.Task;
import com.appfire.taskmanagement.dto.TaskDTO;
import com.appfire.taskmanagement.repository.TaskRepository;
import com.appfire.taskmanagement.repository.UserProjectRepository;
import com.appfire.taskmanagement.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserProjectRepository userProjectRepository;

    @Override
    public Task save(Task entity) {
        return taskRepository.save(entity);
    }

    @Override
    public void delete(Task entity) {
        entity.setDeleted(true);
        taskRepository.update(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                Status.DONE.toString(),
                entity.isDeleted());
    }

    @Override
    public Task findById(String s) {
        return taskRepository.findById(s).orElseThrow(TaskNotFoundException::new);
    }

    @Override
    public boolean checkTaskByIdAndUserId(String id, String userId) {
        return userProjectRepository
                .findALlUsersByProjectId(findById(id).getProject().getId())
                .stream()
                .noneMatch(userProject -> userProject.getUser().getId().equals(userId));
    }

    @Override
    public List<Task> findAllByProjectIdAndNotDeleted(String projectId) {
        return taskRepository.findAllByProjectIdAndIsDeleted(projectId,false);
    }

    @Override
    public void updateById(String id, TaskDTO task) {
        taskRepository.update(id,task.name(),task.description(),task.status().toString(),task.isDeleted());
    }

    @Override
    public String save(TaskDTO task,Project project) {
        Task taskToSave = new Task();
        taskToSave.setName(task.name());
        taskToSave.setDescription(task.description());
        taskToSave.setProject(project);
        taskToSave.setStatus(Status.IDEA);
        return taskRepository.save(taskToSave).getId();
    }


}
