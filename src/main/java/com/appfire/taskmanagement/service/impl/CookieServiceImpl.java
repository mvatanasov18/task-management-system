package com.appfire.taskmanagement.service.impl;

import com.appfire.taskmanagement.exception.SessionNotFoundException;
import com.appfire.taskmanagement.service.CookieService;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

@Service
public class CookieServiceImpl implements CookieService {
    @Override
    public String getValue(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("session")) {
                    return cookie.getValue();
                }
            }
        }
        throw new SessionNotFoundException();
    }

    @Override
    public boolean isSessionPresent(Cookie[] cookies) {
        try {
            return !getValue(cookies).equals("");
        }catch (Exception e){
            return false;
        }
    }
}
