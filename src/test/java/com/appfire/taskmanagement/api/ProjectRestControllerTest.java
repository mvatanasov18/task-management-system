package com.appfire.taskmanagement.api;

import static org.junit.jupiter.api.Assertions.*;

import com.appfire.taskmanagement.model.Project;
import com.appfire.taskmanagement.model.Session;
import com.appfire.taskmanagement.model.User;
import com.appfire.taskmanagement.service.CookieService;
import com.appfire.taskmanagement.service.ProjectService;
import com.appfire.taskmanagement.service.SessionService;
import com.appfire.taskmanagement.service.UserProjectService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProjectRestControllerTest {

    @Mock
    private CookieService cookieService;

    @Mock
    private SessionService sessionService;

    @Mock
    private ProjectService projectService;

    @Mock
    private UserProjectService userProjectService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ProjectRestController projectRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProjectsFromSessionId() {
        // Arrange
        when(cookieService.getValue(any(Cookie[].class))).thenReturn("sessionId");

        when(sessionService.findById("sessionId")).thenReturn(createSession());
        when(userProjectService.findAllNotDeletedProjectsByUserId("userId")).thenReturn(createProjects());

        // Mock the behavior of the HttpServletRequest to include the session ID
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("sessionId", "sessionId")});

        // Act
        ResponseEntity<?> responseEntity = projectRestController.getAllProjectsFromSessionId(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof List<?>);
        List<?> projectList = (List<?>) responseEntity.getBody();
        assertEquals(2, projectList.size());
        // TODO: Add more assertions based on the expected project list
    }


    @Test
    void testGetProjectById() {
        // Arrange
        when(cookieService.getValue(any(Cookie[].class))).thenReturn("sessionId");

        when(sessionService.findById("sessionId")).thenReturn(createSession());
        when(userProjectService.checkUserIdAndProjectId("userId", "projectId")).thenReturn(true);
        when(projectService.findById("projectId")).thenReturn(createProject());

        // Mock the behavior of the HttpServletRequest to include the session ID
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("sessionId", "sessionId")});

        // Act
        ResponseEntity<?> responseEntity = projectRestController.getProjectById("projectId", request);

        // Assert
        Project project = createProject();
        Project response = (Project) responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(response);
        assertEquals(project.getId(), response.getId());
        assertEquals(project.getName(), response.getName());
        assertEquals(project.getDescription(), response.getDescription());
    }

    @Test
    void testGetProjectById_WhenProjectNotActiveOrUserNotPartOfProject() {
        // Arrange
        when(cookieService.getValue(any(Cookie[].class))).thenReturn("sessionId");

        when(sessionService.findById("sessionId")).thenReturn(createSession());
        when(userProjectService.checkUserIdAndProjectId("userId", "projectId")).thenReturn(false);
        // Mock the behavior of the HttpServletRequest to include the session ID
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("sessionId", "sessionId")});

        // Act
        ResponseEntity<?> responseEntity = projectRestController.getProjectById("projectId", request);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("Project not active or user not part of the project", responseEntity.getBody());
        // TODO: Add more assertions based on the expected error message
    }

    @Test
    void testCreateProject() {
        // Arrange
        when(cookieService.getValue(any(Cookie[].class))).thenReturn("sessionId");

        when(sessionService.findById("sessionId")).thenReturn(createSession());
        when(projectService.save(any(Project.class))).thenReturn(createProject());
        // Mock the behavior of the HttpServletRequest to include the session ID
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("sessionId", "sessionId")});

        // Act
        ResponseEntity<String> responseEntity = projectRestController.createProject(createProject(), request);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(createProject().getId(), responseEntity.getBody());
        // TODO: Add more assertions based on the expected project ID
    }

    // Helper methods to create mock objects

    private Session createSession() {
        User user = new User();
        user.setId("userId");
        return new Session("sessionId",LocalDateTime.now(),user);
    }

    private List<Project> createProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(createProject("projectId1", "Project 1", "Description 1"));
        projects.add(createProject("projectId2", "Project 2", "Description 2"));
        return projects;
    }

    private Project createProject() {
        return createProject("projectId", "Test Project", "Test Description");
    }

    private Project createProject(String id, String name, String description) {
        Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.setDescription(description);
        return project;
    }
}

