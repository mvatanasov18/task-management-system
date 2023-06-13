package com.appfire.taskmanagement.service;

import com.appfire.taskmanagement.model.Navbar;
import org.springframework.stereotype.Service;

import java.util.Map;
public interface NavbarService {
    Navbar getNavbar(String sessionToken);
}
