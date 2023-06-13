package com.appfire.taskmanagement.api;

import static org.junit.jupiter.api.Assertions.*;

import com.appfire.taskmanagement.dto.UserDTO;
import com.appfire.taskmanagement.service.CookieService;
import com.appfire.taskmanagement.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserRestControllerTest {

    @Mock
    private CookieService cookieService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserRestController userRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_WithValidUserDTOAndNoActiveSession_ReturnsCreatedStatus() {
        // Arrange
        UserDTO userDTO = new UserDTO("username", "password", "John", "Doe");
        when(cookieService.isSessionPresent(any(Cookie[].class))).thenReturn(false);
        when(userService.save(userDTO)).thenReturn("userId");

        // Act
        ResponseEntity<String> responseEntity = userRestController.createUser(userDTO, request);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("userId", responseEntity.getBody());
    }

    @Test
    void testCreateUser_WithValidUserDTOAndActiveSession_ReturnsForbiddenStatus() {
        // Arrange
        UserDTO userDTO = new UserDTO("username", "password", "John", "Doe");
        Cookie cookie = new Cookie("session", "sessionId");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        when(cookieService.isSessionPresent(any(Cookie[].class))).thenReturn(true);

        // Act
        ResponseEntity<String> responseEntity = userRestController.createUser(userDTO, request);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("User is logged in", responseEntity.getBody());
    }


    @Test
    void testCreateUser_WithInvalidUserDTO_ReturnsBadRequestStatus() {
        // Arrange
        UserDTO userDTO = new UserDTO("", "password", "John", "Doe");
        when(cookieService.isSessionPresent(any(Cookie[].class))).thenReturn(false);
        when(userService.save(userDTO)).thenReturn("");

        // Act
        ResponseEntity<String> responseEntity = userRestController.createUser(userDTO, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Username already taken/length too small", responseEntity.getBody());
    }
}