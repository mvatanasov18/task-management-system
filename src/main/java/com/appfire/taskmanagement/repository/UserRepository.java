package com.appfire.taskmanagement.repository;

import com.appfire.taskmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            UPDATE Users
            SET Username = :username,
            FirstName = :firstName,
            LastName = :lastName
            WHERE Id = :id
            """)
    void update(String id, String username, String firstName, String lastName);
}
