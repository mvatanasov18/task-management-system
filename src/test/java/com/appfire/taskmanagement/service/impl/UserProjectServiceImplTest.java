package com.appfire.taskmanagement.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.appfire.taskmanagement.exception.UserProjectNotFoundException;
import com.appfire.taskmanagement.model.*;
import com.appfire.taskmanagement.model.id.UserProjectId;
import com.appfire.taskmanagement.repository.UserProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class UserProjectServiceImplTest {

    @InjectMocks
    private UserProjectServiceImpl userProjectService;

    @Mock
    private UserProjectRepository userProjectRepository;

    private UserProject testUserProject;
    private UserProjectId testUserProjectId;
    private User testUser;
    private Project testProject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(UUID.randomUUID().toString());
        testUser.setUsername("testuser");

        testProject = new Project();
        testProject.setId(UUID.randomUUID().toString());
        testProject.setName("Test Project");
        testProject.setDescription("Test project description");

        testUserProjectId = new UserProjectId(testUser.getId(), testProject.getId());

        testUserProject = new UserProject();
        testUserProject.setId(testUserProjectId);
        testUserProject.setRole(Role.USER);
        testUserProject.setDeleted(false);
        testUserProject.setUser(testUser);
        testUserProject.setProject(testProject);
    }

    @Test
    void testSave_ValidUserProject_CallsRepositorySaveAndReturnsSavedUserProject() {
        // Arrange
        when(userProjectRepository.save(testUserProject)).thenReturn(testUserProject);

        // Act
        UserProject savedUserProject = userProjectService.save(testUserProject);

        // Assert
        verify(userProjectRepository, times(1)).save(testUserProject);
        assertEquals(testUserProject, savedUserProject);
    }

    @Test
    void testDelete_ValidUserProject_CallsRepositoryDelete() {
        // Arrange
        // No need to mock the repository call as it doesn't return anything

        // Act
        userProjectService.delete(testUserProject);

        // Assert
        verify(userProjectRepository, times(1)).delete(testUserProject);
    }

    @Test
    void testFindById_WithValidId_ReturnsUserProject() {
        // Arrange
        when(userProjectRepository.findById(testUserProjectId)).thenReturn(Optional.of(testUserProject));

        // Act
        UserProject foundUserProject = userProjectService.findById(testUserProjectId);

        // Assert
        assertEquals(testUserProject, foundUserProject);
    }

    @Test
    void testFindById_WithInvalidId_ThrowsUserProjectNotFoundException() {
        // Arrange
        when(userProjectRepository.findById(testUserProjectId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserProjectNotFoundException.class, () -> userProjectService.findById(testUserProjectId));
    }

    @Test
    void testFindAllNotDeletedProjectsByUserId_WithValidUserId_ReturnsProjectList() {
        // Arrange
        List<UserProject> userProjects = List.of(testUserProject);
        when(userProjectRepository.findAllByUserId(testUser.getId())).thenReturn(userProjects);

        // Act
        List<Project> foundProjects = userProjectService.findAllNotDeletedProjectsByUserId(testUser.getId());

        // Assert
        assertEquals(List.of(testProject), foundProjects);
    }

    @Test
    void testSave_UserProjectDetails_CreatesUserProjectAndReturnsProjectId() {
        // Arrange
        when(userProjectRepository.save(any(UserProject.class))).thenReturn(testUserProject);

        // Act
        String savedProjectId = userProjectService.save(testUser, testProject, Role.OWNER);

        // Assert
        verify(userProjectRepository, times(1)).save(any(UserProject.class));
        assertEquals(testProject.getId(), savedProjectId);
    }


    @Test
    void testFindALlUsersByProjectId_WithValidProjectId_ReturnsUserList() {
        // Arrange
        List<UserProject> userProjects = List.of(testUserProject);
        when(userProjectRepository.findALlUsersByProjectId(testProject.getId())).thenReturn(userProjects);

        // Act
        List<User> foundUsers = userProjectService.findALlUsersByProjectId(testProject.getId());

        // Assert
        assertEquals(List.of(testUser), foundUsers);
    }

    @Test
    void testCheckUserIdAndProjectId_WithValidUserIdAndProjectId_ReturnsTrue() {
        // Arrange
        when(userProjectRepository.findALlUsersByProjectId(testProject.getId())).thenReturn(List.of(testUserProject));

        // Act
        boolean result = userProjectService.checkUserIdAndProjectId(testUser.getId(), testProject.getId());

        // Assert
        assertTrue(result);
    }

    @Test
    void testCheckUserIdAndProjectId_WithInvalidUserId_ReturnsFalse() {
        // Arrange
        when(userProjectRepository.findALlUsersByProjectId(testProject.getId())).thenReturn(List.of(testUserProject));

        // Act
        boolean result = userProjectService.checkUserIdAndProjectId(UUID.randomUUID().toString(), testProject.getId());

        // Assert
        assertFalse(result);
    }

    @Test
    void testCheckUserIdAndProjectId_WithInvalidProjectId_ReturnsFalse() {
        // Arrange
        when(userProjectRepository.findALlUsersByProjectId(testProject.getId())).thenReturn(List.of(testUserProject));

        // Act
        boolean result = userProjectService.checkUserIdAndProjectId(testUser.getId(), UUID.randomUUID().toString());

        // Assert
        assertFalse(result);
    }
}
