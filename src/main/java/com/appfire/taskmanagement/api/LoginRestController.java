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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Controller
@AllArgsConstructor
@RequestMapping(value = "/api/v1/login")
public class LoginRestController {

    private final CookieService cookieService;
    private final SessionService sessionService;
    private final UserService userService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        sessionService.deleteById(cookieService.getValue(request.getCookies()));
        response.addCookie(createCookie("",0));
    }
    @PostMapping
    public ResponseEntity<String> postLogin(
           @Valid @ModelAttribute LoginRequestDto loginRequest,
            HttpServletResponse response,
            HttpServletRequest request) {

        if (cookieService.isSessionPresent(request.getCookies())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is logged in");
        }

        User user = userService.findByUsername(loginRequest.username());

        if (!userService.checkPassword(user, loginRequest.password())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");
        }
        String id = UUID.randomUUID().toString();
        Session temp = sessionService.findByUserId(user.getId());

        //check if user already has a session
        if (temp != null) {
            sessionService.delete(temp);
        }
        Session session = new Session(id, LocalDateTime.now(), user);
        sessionService.save(session);
        response.addCookie(createCookie(id,4*60*60));
        return ResponseEntity.ok().build();
    }

    private Cookie createCookie(String value, int age){
        Cookie cookie = new Cookie("session", value);
        cookie.setMaxAge(age);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
}
