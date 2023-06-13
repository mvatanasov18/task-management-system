package com.appfire.taskmanagement.api;

import com.appfire.taskmanagement.dto.LoginRequestDto;
import com.appfire.taskmanagement.model.Session;
import com.appfire.taskmanagement.model.User;
import com.appfire.taskmanagement.service.CookieService;
import com.appfire.taskmanagement.service.SessionService;
import com.appfire.taskmanagement.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginRestControllerTest {

    @Mock
    private CookieService cookieService;

    @Mock
    private SessionService sessionService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginRestController loginRestController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostLogin_WhenUserNotLoggedInAndValidCredentials_thenReturnOk() {
        // Arrange
        String username = "testuser";
        String password = "testpassword";
        String sessionId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(password);
        LoginRequestDto loginRequestDto = new LoginRequestDto(username, password);

        when(cookieService.isSessionPresent(any(Cookie[].class))).thenReturn(false);
        when(userService.findByUsername(username)).thenReturn(user);
        when(userService.checkPassword(user, password)).thenReturn(true);
        when(sessionService.findByUserId(user.getId())).thenReturn(null);
        when(sessionService.save(any(Session.class))).thenReturn(new Session(sessionId, now, user));
        when(cookieService.getValue(any(Cookie[].class))).thenReturn(sessionId);

        // Act
        ResponseEntity<String> responseEntity = loginRestController.postLogin(loginRequestDto, response, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(sessionService).save(any(Session.class));
        verify(response).addCookie(any(Cookie.class));
    }

    @Test
    void testPostLogin_WhenUserAlreadyLoggedIn_thenReturnForbidden() {
        // Arrange
        String username = "testuser";
        String password = "testpassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        LoginRequestDto loginRequestDto = new LoginRequestDto(username, password);

        Cookie cookie = new Cookie("session", "sessionId");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        when(cookieService.isSessionPresent(any(Cookie[].class))).thenReturn(true);

        // Act
        ResponseEntity<String> responseEntity = loginRestController.postLogin(loginRequestDto, response, request);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("User is logged in", responseEntity.getBody());
        verifyNoInteractions(userService, sessionService, response);
    }


    @Test
    void testPostLogin_WhenInvalidCredentials_thenReturnBadRequest() {
        // Arrange
        String username = "testuser";
        String password = "testpassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword("wrongpassword");
        LoginRequestDto loginRequestDto = new LoginRequestDto(username, password);

        when(cookieService.isSessionPresent(any(Cookie[].class))).thenReturn(false);
        when(userService.findByUsername(username)).thenReturn(user);
        when(userService.checkPassword(user, password)).thenReturn(false);

        // Act
        ResponseEntity<String> responseEntity = loginRestController.postLogin(loginRequestDto, response, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid credentials", responseEntity.getBody());
        verifyNoInteractions(sessionService, response);
        verify(response, never()).addCookie(any(Cookie.class));
    }
}
