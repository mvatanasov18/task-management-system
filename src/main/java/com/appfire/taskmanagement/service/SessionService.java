package com.appfire.taskmanagement.service;


import com.appfire.taskmanagement.model.Session;

public interface SessionService extends Service<Session,String> {
    Session findByUserId(String id);
    void deleteById(String id);
}
