package com.appfire.taskmanagement.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.appfire.taskmanagement.dto.TaskDTO;
import com.appfire.taskmanagement.exception.TaskNotFoundException;
import com.appfire.taskmanagement.model.*;
import com.appfire.taskmanagement.repository.TaskRepository;
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

class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserProjectRepository userProjectRepository;

    private Task testTask;
    private Project testProject;
    private TaskDTO testTaskDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testProject = new Project();
        testProject.setId(UUID.randomUUID().toString());
        testProject.setName("Test Project");
        testProject.setDescription("Test project description");

        testTask = new Task();
        testTask.setId(UUID.randomUUID().toString());
        testTask.setName("Test Task");
        testTask.setDescription("Test task description");
        testTask.setStatus(Status.IDEA);
        testTask.setProject(testProject);
        testTask.setDeleted(false);

        testTaskDTO = new TaskDTO(
                UUID.randomUUID().toString(),
                "Test Task",
                "Test task description",
                testProject.getId(),
                Status.IDEA,
                false
        );
    }

    @Test
    void testSave_ValidTask_CallsRepositorySaveAndReturnsSavedTask() {
        // Arrange
        when(taskRepository.save(testTask)).thenReturn(testTask);

        // Act
        Task savedTask = taskService.save(testTask);

        // Assert
        verify(taskRepository, times(1)).save(testTask);
        assertEquals(testTask, savedTask);
    }

    @Test
    void testDelete_ValidTask_CallsRepositoryUpdateAndSetsDeletedFlag() {

        // Act
        taskService.delete(testTask);

        // Assert
        verify(taskRepository, times(1)).update(
                testTask.getId(),
                testTask.getName(),
                testTask.getDescription(),
                Status.DONE.toString(),
                true);
        assertTrue(testTask.isDeleted());
    }

    @Test
    void testFindById_WithValidId_ReturnsTask() {
        // Arrange
        String id = testTask.getId();
        when(taskRepository.findById(id)).thenReturn(Optional.of(testTask));

        // Act
        Task foundTask = taskService.findById(id);

        // Assert
        assertEquals(testTask, foundTask);
    }

    @Test
    void testFindById_WithInvalidId_ThrowsTaskNotFoundException() {
        // Arrange
        String invalidId = UUID.randomUUID().toString();
        when(taskRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.findById(invalidId));
    }

    @Test
    void testCheckTaskByIdAndUserId_WithValidIdAndUserId_ReturnsTrue() {
        // Arrange
        String taskId = testTask.getId();
        String userId = UUID.randomUUID().toString();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(testTask));
        when(userProjectRepository.findALlUsersByProjectId(testProject.getId())).thenReturn(new ArrayList<>());

        // Act
        boolean result = taskService.checkTaskByIdAndUserId(taskId, userId);

        // Assert
        assertTrue(result);
    }

    @Test
    void testCheckTaskByIdAndUserId_WithValidIdAndInvalidUserId_ReturnsFalse() {
        // Arrange
        String taskId = testTask.getId();
        String userId = UUID.randomUUID().toString();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(testTask));

        List<UserProject> userProjects = new ArrayList<>();
        UserProject userProject = new UserProject();
        User user = new User();
        user.setId(userId);
        userProject.setUser(user);
        userProjects.add(userProject);

        when(userProjectRepository.findALlUsersByProjectId(testProject.getId())).thenReturn(userProjects);

        // Act
        boolean result = taskService.checkTaskByIdAndUserId(taskId, userId);

        // Assert
        assertFalse(result);
    }

    @Test
    void testFindAllByProjectIdAndNotDeleted_WithValidProjectId_ReturnsTaskList() {
        // Arrange
        String projectId = testProject.getId();
        List<Task> expectedTasks = List.of(testTask);
        when(taskRepository.findAllByProjectIdAndIsDeleted(projectId, false)).thenReturn(expectedTasks);

        // Act
        List<Task> foundTasks = taskService.findAllByProjectIdAndNotDeleted(projectId);

        // Assert
        assertEquals(expectedTasks, foundTasks);
    }

    @Test
    void testUpdateById_ValidIdAndTaskDTO_CallsRepositoryUpdate() {
        // Arrange
        String taskId = testTask.getId();
        TaskDTO updatedTaskDTO = new TaskDTO(
                taskId,
                "Updated Task",
                "Updated task description",
                testProject.getId(),
                Status.IN_PROGRESS,
                false
        );

        // Act
        taskService.updateById(taskId, updatedTaskDTO);

        // Assert
        verify(taskRepository, times(1)).update(
                taskId,
                updatedTaskDTO.name(),
                updatedTaskDTO.description(),
                updatedTaskDTO.status().toString(),
                updatedTaskDTO.isDeleted());
    }

    @Test
    void testSave_TaskDTOAndProject_CreatesTaskAndReturnsId() {
        // Arrange
        Task taskToSave = new Task();
        taskToSave.setId(testTaskDTO.id());
        taskToSave.setName(testTaskDTO.name());
        taskToSave.setDescription(testTaskDTO.description());
        taskToSave.setProject(testProject);
        taskToSave.setStatus(Status.IDEA);
        taskToSave.setDeleted(false);

        when(taskRepository.save(any(Task.class))).thenReturn(taskToSave);

        // Act
        String savedTaskId = taskService.save(testTaskDTO, testProject);

        // Assert
        verify(taskRepository, times(1)).save(any(Task.class));
        assertEquals(taskToSave.getId(), savedTaskId);
    }

}
