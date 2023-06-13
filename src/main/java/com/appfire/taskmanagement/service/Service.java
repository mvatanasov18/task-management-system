package com.appfire.taskmanagement.service;

import com.appfire.taskmanagement.model.Project;

public interface Service<T,ID> {
    T save(T entity);

    void delete(T entity);

    T findById(ID id);
}
