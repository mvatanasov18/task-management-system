package com.appfire.taskmanagement.repository;

import com.appfire.taskmanagement.model.Status;
import com.appfire.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    List<Task> findAllByProjectIdAndIsDeleted(String project_id, boolean deleted);

    @Modifying
    @Transactional
    @Query(value = """
UPDATE Tasks
SET Name = :name,
Description = :description,
Status = :status,
IsDeleted = :deleted
WHERE Id = :id
""",nativeQuery = true)
    void update(String id, String name, String description, String status, boolean deleted);
}
