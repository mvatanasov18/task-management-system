package com.appfire.taskmanagement.service;

import com.appfire.taskmanagement.model.Project;
import com.appfire.taskmanagement.model.Task;
import com.appfire.taskmanagement.dto.TaskDTO;

import java.util.List;

public interface TaskService extends Service<Task,String>{
    boolean checkTaskByIdAndUserId(String id, String userId);

    List<Task> findAllByProjectIdAndNotDeleted(String projectId);

    void updateById(String id, TaskDTO task);

    String save(TaskDTO task, Project project);
}
