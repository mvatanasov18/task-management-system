package com.appfire.taskmanagement.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.appfire.taskmanagement.exception.SessionNotFoundException;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockCookie;

class CookieServiceImplTest {

    @InjectMocks
    private CookieServiceImpl cookieService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetValue_WithValidCookie_ReturnsCookieValue() {
        // Arrange
        MockCookie cookie = new MockCookie("session", "sessionId");
        Cookie[] cookies = {cookie};

        // Act
        String value = cookieService.getValue(cookies);

        // Assert
        assertEquals("sessionId", value);
    }

    @Test
    void testGetValue_WithNoCookies_ThrowsSessionNotFoundException() {
        // Arrange
        Cookie[] cookies = null;

        // Act & Assert
        assertThrows(SessionNotFoundException.class, () -> cookieService.getValue(cookies));
    }

    @Test
    void testGetValue_WithNoSessionCookie_ThrowsSessionNotFoundException() {
        // Arrange
        MockCookie cookie = new MockCookie("otherCookie", "otherValue");
        Cookie[] cookies = {cookie};

        // Act & Assert
        assertThrows(SessionNotFoundException.class, () -> cookieService.getValue(cookies));
    }

    @Test
    void testIsSessionPresent_WithValidCookie_ReturnsTrue() {
        // Arrange
        MockCookie cookie = new MockCookie("session", "sessionId");
        Cookie[] cookies = {cookie};

        // Act
        boolean isPresent = cookieService.isSessionPresent(cookies);

        // Assert
        assertTrue(isPresent);
    }

    @Test
    void testIsSessionPresent_WithNoCookies_ReturnsFalse() {
        // Arrange
        Cookie[] cookies = null;

        // Act
        boolean isPresent = cookieService.isSessionPresent(cookies);

        // Assert
        assertFalse(isPresent);
    }

    @Test
    void testIsSessionPresent_WithNoSessionCookie_ReturnsFalse() {
        // Arrange
        MockCookie cookie = new MockCookie("otherCookie", "otherValue");
        Cookie[] cookies = {cookie};

        // Act
        boolean isPresent = cookieService.isSessionPresent(cookies);

        // Assert
        assertFalse(isPresent);
    }
}
