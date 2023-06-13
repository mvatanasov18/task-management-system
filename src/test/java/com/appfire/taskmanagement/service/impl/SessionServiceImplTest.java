package com.appfire.taskmanagement.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.appfire.taskmanagement.exception.SessionNotFoundException;
import com.appfire.taskmanagement.model.Session;
import com.appfire.taskmanagement.repository.SessionRepository;
import com.appfire.taskmanagement.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

class SessionServiceImplTest {

    @InjectMocks
    private SessionServiceImpl sessionService;

    @Mock
    private SessionRepository sessionRepository;

    private Session testSession;
    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(UUID.randomUUID().toString());
        testUser.setUsername("testuser");

        testSession = new Session();
        testSession.setId(UUID.randomUUID().toString());
        testSession.setTimeCreated(LocalDateTime.now());
        testSession.setUser(testUser);
    }

    @Test
    void testSave_ValidSession_CallsRepositorySaveAndReturnsSavedSession() {
        // Arrange
        when(sessionRepository.save(testSession)).thenReturn(testSession);

        // Act
        Session savedSession = sessionService.save(testSession);

        // Assert
        verify(sessionRepository, times(1)).save(testSession);
        assertEquals(testSession, savedSession);
    }

    @Test
    void testDelete_ValidSession_CallsRepositoryDelete() {
        // Act
        sessionService.delete(testSession);

        // Assert
        verify(sessionRepository, times(1)).delete(testSession);
    }

    @Test
    void testFindById_WithValidId_ReturnsSession() {
        // Arrange
        String id = testSession.getId();
        when(sessionRepository.findById(id)).thenReturn(Optional.of(testSession));

        // Act
        Session foundSession = sessionService.findById(id);

        // Assert
        assertEquals(testSession, foundSession);
    }

    @Test
    void testFindById_WithInvalidId_ThrowsSessionNotFoundException() {
        // Arrange
        String invalidId = UUID.randomUUID().toString();
        when(sessionRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SessionNotFoundException.class, () -> sessionService.findById(invalidId));
    }

    @Test
    void testFindByUserId_WithValidUserId_ReturnsSession() {
        // Arrange
        String userId = testUser.getId();
        when(sessionRepository.findByUserId(userId)).thenReturn(testSession);

        // Act
        Session foundSession = sessionService.findByUserId(userId);

        // Assert
        assertEquals(testSession, foundSession);
    }

    @Test
    void testDeleteById_WithValidId_CallsRepositoryDeleteById() {
        // Arrange
        String id = testSession.getId();

        // Act
        sessionService.deleteById(id);

        // Assert
        verify(sessionRepository, times(1)).deleteById(id);
    }
}
