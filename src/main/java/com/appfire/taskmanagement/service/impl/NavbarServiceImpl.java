package com.appfire.taskmanagement.service.impl;

import com.appfire.taskmanagement.model.Navbar;
import com.appfire.taskmanagement.service.NavbarService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
@Service
public class NavbarServiceImpl implements NavbarService {

    public Map<String, String> getLinksForLoggedUser(){
        Map<String, String> elements = new LinkedHashMap<>();
        elements.put("/", "Home");
        elements.put("/projects_menu", "Projects");
        elements.put("/profile", "Profile");
        elements.put("/login/logout", "Logout");
        return elements;
    }
    public Map<String, String> getLinksForNotLoggedUser(){
        Map<String, String> elements = new LinkedHashMap<>();
        elements.put("/", "Home");
        elements.put("/register", "Register");
        elements.put("/login", "Login");
        return elements;
    }

    @Override
    public Navbar getNavbar(String sessionId) {
        return new Navbar(sessionId.equals("") ? getLinksForNotLoggedUser() : getLinksForLoggedUser());
    }
}
