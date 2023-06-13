package com.appfire.taskmanagement.api;

import com.appfire.taskmanagement.api.TaskRestController;
import com.appfire.taskmanagement.dto.TaskDTO;
import com.appfire.taskmanagement.model.*;
import com.appfire.taskmanagement.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TaskRestControllerTest {

    @Mock
    private CookieService cookieService;

    @Mock
    private SessionService sessionService;

    @Mock
    private TaskService taskService;

    @Mock
    private ProjectService projectService;

    @Mock
    private UserProjectService userProjectService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private TaskRestController taskRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock the behavior of the HttpServletRequest to include the session ID
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("sessionId", "sessionId")});
    }

    @Test
    void testGetAllTasksByProjectId() {
        // Arrange
        String projectId = "projectId";
        String sessionToken = "sessionToken";
        User user = new User();
        user.setId("userId");

        when(cookieService.getValue(any(Cookie[].class))).thenReturn(sessionToken);
        when(sessionService.findById(sessionToken)).thenReturn(createSession(user));
        when(userProjectService.checkUserIdAndProjectId(user.getId(), projectId)).thenReturn(true);
        when(taskService.findAllByProjectIdAndNotDeleted(projectId)).thenReturn(createTasks());

        // Act
        ResponseEntity<?> responseEntity = taskRestController.getAllTasksByProjectId(projectId, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        List<?> taskList = (List<?>) responseEntity.getBody();
        assertEquals(2, taskList.size());
        // TODO: Add more assertions based on the expected task list
    }

    @Test
    void testGetAllTasksByProjectId_WhenProjectNotActiveOrUserNotPartOfProject() {
        // Arrange
        String projectId = "projectId";
        String sessionToken = "sessionToken";
        User user = new User();
        user.setId("userId");

        when(cookieService.getValue(any(Cookie[].class))).thenReturn(sessionToken);
        when(sessionService.findById(sessionToken)).thenReturn(createSession(user));
        when(userProjectService.checkUserIdAndProjectId(user.getId(), projectId)).thenReturn(false);

        // Act
        ResponseEntity<?> responseEntity = taskRestController.getAllTasksByProjectId(projectId, request);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("Project not active or user not part of the project", responseEntity.getBody());
        // TODO: Add more assertions based on the expected error message
    }

    @Test
    void testGetTaskById() {
        // Arrange
        String taskId = "taskId";
        String sessionToken = "sessionToken";
        String userId = "userId";

        when(cookieService.getValue(any(Cookie[].class))).thenReturn(sessionToken);
        when(sessionService.findById(sessionToken)).thenReturn(createSession(userId));
        when(taskService.checkTaskByIdAndUserId(taskId, userId)).thenReturn(false);
        when(taskService.findById(taskId)).thenReturn(createTask());

        // Act
        ResponseEntity<?> responseEntity = taskRestController.getTaskById(taskId, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        // TODO: Add more assertions based on the expected task
    }

    @Test
    void testGetTaskById_WhenTaskNotBelongingToUserOrProject() {
        // Arrange
        String taskId = "taskId";
        String sessionToken = "sessionToken";
        String userId = "userId";

        when(cookieService.getValue(any(Cookie[].class))).thenReturn(sessionToken);
        when(sessionService.findById(sessionToken)).thenReturn(createSession(userId));
        when(taskService.checkTaskByIdAndUserId(taskId, userId)).thenReturn(true);

        // Act
        ResponseEntity<?> responseEntity = taskRestController.getTaskById(taskId, request);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("Project not active or user not part of the project", responseEntity.getBody());
        // TODO: Add more assertions based on the expected error message
    }

    @Test
    void testCreate() {
        // Arrange
        TaskDTO taskDTO = createTaskDTO();
        String sessionToken = "sessionToken";
        String userId = "userId";
        when(cookieService.getValue(any(Cookie[].class))).thenReturn(sessionToken);
        when(sessionService.findById(sessionToken)).thenReturn(createSession(userId));
        when(userProjectService.checkUserIdAndProjectId(userId, taskDTO.projectId())).thenReturn(true);

        // Act
        taskRestController.create(taskDTO, request);

        // Assert
        verify(taskService, times(1)).save(eq(taskDTO), any());
    }

    @Test
    void testCreate_WhenUserNotPartOfProject() {
        // Arrange
        TaskDTO taskDTO = createTaskDTO();
        String sessionToken = "sessionToken";
        String userId = "userId";
        when(cookieService.getValue(any(Cookie[].class))).thenReturn(sessionToken);
        when(sessionService.findById(sessionToken)).thenReturn(createSession(userId));
        when(userProjectService.checkUserIdAndProjectId(userId, taskDTO.projectId())).thenReturn(false);

        // Act & Assert
        PermissionDeniedDataAccessException exception = assertThrows(PermissionDeniedDataAccessException.class,
                () -> taskRestController.create(taskDTO, request));
        assertEquals("User not part of the given project", exception.getMessage());
    }

    // TODO: Add tests for other endpoints (update, delete)

    // Helper methods to create test data

    private Session createSession(User user) {
        return new Session("sessionId", LocalDateTime.now(), user);
    }

    private Session createSession(String userId) {
        User user = new User();
        user.setId(userId);
        return new Session("sessionId", LocalDateTime.now(), user);
    }

    private Project createProject(){
        return new Project("project1","name","description", false,new ArrayList<>());
    }
    private List<Task> createTasks() {
        Task task1 = new Task("taskId1", "Task 1", "Description 1", Status.IDEA, createProject(), false);
        Task task2 = new Task("taskId2", "Task 2", "Description 2", Status.IN_PROGRESS,  createProject(), false);
        return Arrays.asList(task1, task2);
    }

    private Task createTask() {
        return new Task("taskId", "Task 1", "Description 1", Status.IDEA,  createProject(), false);
    }

    private TaskDTO createTaskDTO() {
        return new TaskDTO("taskId", "Task 1", "Description 1", "projectId", Status.IDEA, false);
    }
}
