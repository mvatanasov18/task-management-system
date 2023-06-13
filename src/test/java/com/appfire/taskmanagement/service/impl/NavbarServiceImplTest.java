package com.appfire.taskmanagement.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.appfire.taskmanagement.model.Navbar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.LinkedHashMap;
import java.util.Map;

class NavbarServiceImplTest {

    @InjectMocks
    private NavbarServiceImpl navbarService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetLinksForLoggedUser_ReturnsExpectedMap() {
        // Arrange
        Map<String, String> expectedElements = new LinkedHashMap<>();
        expectedElements.put("/", "Home");
        expectedElements.put("/projects_menu", "Projects");
        expectedElements.put("/profile", "Profile");
        expectedElements.put("/login/logout", "Logout");

        // Act
        Map<String, String> elements = navbarService.getLinksForLoggedUser();

        // Assert
        assertEquals(expectedElements, elements);
    }

    @Test
    void testGetLinksForNotLoggedUser_ReturnsExpectedMap() {
        // Arrange
        Map<String, String> expectedElements = new LinkedHashMap<>();
        expectedElements.put("/", "Home");
        expectedElements.put("/register", "Register");
        expectedElements.put("/login", "Login");

        // Act
        Map<String, String> elements = navbarService.getLinksForNotLoggedUser();

        // Assert
        assertEquals(expectedElements, elements);
    }

    @Test
    void testGetNavbar_WithEmptySessionId_ReturnsNavbarWithLinksForNotLoggedUser() {
        // Arrange
        String sessionId = "";

        // Act
        Navbar navbar = navbarService.getNavbar(sessionId);

        // Assert
        assertEquals(navbarService.getLinksForNotLoggedUser(), navbar.getElements());
    }

    @Test
    void testGetNavbar_WithNonEmptySessionId_ReturnsNavbarWithLinksForLoggedUser() {
        // Arrange
        String sessionId = "sessionId";

        // Act
        Navbar navbar = navbarService.getNavbar(sessionId);

        // Assert
        assertEquals(navbarService.getLinksForLoggedUser(), navbar.getElements());
    }
}
