package com.appfire.taskmanagement.service.impl;

import com.appfire.taskmanagement.exception.SessionNotFoundException;
import com.appfire.taskmanagement.model.Session;
import com.appfire.taskmanagement.repository.SessionRepository;
import com.appfire.taskmanagement.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository repository;

    @Override
    public Session save(Session entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Session entity) {
        repository.delete(entity);
    }

    @Override
    public Session findById(String id) {
        return repository.findById(id).orElseThrow(SessionNotFoundException::new);
    }

    @Override
    public Session findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
