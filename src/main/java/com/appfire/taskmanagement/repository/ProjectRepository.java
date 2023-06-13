package com.appfire.taskmanagement.repository;

import com.appfire.taskmanagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    @Transactional
    @Modifying
    @Query(value = """
    UPDATE Projects
    SET Name = :name,
    Description = :description,
    IsDeleted = :isDeleted
    WHERE Id = :id
    """,nativeQuery = true)
    void update(String id, String name,String description,boolean isDeleted);
}
