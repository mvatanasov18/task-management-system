package com.appfire.taskmanagement.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.appfire.taskmanagement.exception.ProjectNotFoundException;
import com.appfire.taskmanagement.model.Project;
import com.appfire.taskmanagement.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    private Project testProject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testProject = new Project();
        testProject.setId(UUID.randomUUID().toString());
        testProject.setName("Test Project");
        testProject.setDescription("Test Description");
        testProject.setDeleted(false);
    }

    @Test
    void testUpdateById_ValidIdAndProject_CallsRepositoryUpdate() {
        // Arrange
        String id = testProject.getId();
        Project updatedProject = new Project();
        updatedProject.setName("Updated Project");
        updatedProject.setDescription("Updated Description");
        updatedProject.setDeleted(true);

        // Act
        projectService.updateById(id, updatedProject);

        // Assert
        verify(projectRepository, times(1)).update(id, updatedProject.getName(), updatedProject.getDescription(), updatedProject.isDeleted());
    }

    @Test
    void testSave_ValidProject_CallsRepositorySaveAndReturnsSavedProject() {
        // Arrange
        when(projectRepository.save(testProject)).thenReturn(testProject);

        // Act
        Project savedProject = projectService.save(testProject);

        // Assert
        verify(projectRepository, times(1)).save(testProject);
        assertEquals(testProject, savedProject);
    }

    @Test
    void testDelete_ValidProject_SetsDeletedFlagAndCallsUpdateById() {
        // Arrange
        String id = testProject.getId();
        testProject.setDeleted(false);
        when(projectRepository.findById(id)).thenReturn(Optional.of(testProject));

        // Act
        projectService.delete(testProject);

        // Assert
        assertTrue(testProject.isDeleted());
        verify(projectRepository, times(1)).update(id, testProject.getName(), testProject.getDescription(), true);
    }

    @Test
    void testFindById_WithValidId_ReturnsProject() {
        // Arrange
        String id = testProject.getId();
        when(projectRepository.findById(id)).thenReturn(Optional.of(testProject));

        // Act
        Project foundProject = projectService.findById(id);

        // Assert
        assertEquals(testProject, foundProject);
    }

    @Test
    void testFindById_WithInvalidId_ThrowsProjectNotFoundException() {
        // Arrange
        String invalidId = UUID.randomUUID().toString();
        when(projectRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProjectNotFoundException.class, () -> projectService.findById(invalidId));
    }
}
