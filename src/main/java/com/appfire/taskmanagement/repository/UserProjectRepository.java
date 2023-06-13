package com.appfire.taskmanagement.repository;

import com.appfire.taskmanagement.model.Project;
import com.appfire.taskmanagement.model.User;
import com.appfire.taskmanagement.model.UserProject;
import com.appfire.taskmanagement.model.id.UserProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectId> {
    List<UserProject> findAllByUserId(String userId);


    List<UserProject> findALlUsersByProjectId(String id);
}
