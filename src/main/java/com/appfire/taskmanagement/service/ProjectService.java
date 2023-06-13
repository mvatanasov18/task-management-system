package com.appfire.taskmanagement.service;


import com.appfire.taskmanagement.model.Project;

public interface ProjectService extends Service<Project,String> {
    void updateById(String id, Project entity);
}
