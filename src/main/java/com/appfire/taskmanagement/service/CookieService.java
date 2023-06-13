package com.appfire.taskmanagement.service;
import jakarta.servlet.http.Cookie;

public interface CookieService {
    String getValue(Cookie[] cookies);

    boolean isSessionPresent(Cookie[] cookies);
}
