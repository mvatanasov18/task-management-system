package com.appfire.taskmanagement.api;

import com.appfire.taskmanagement.dto.UserDTO;
import com.appfire.taskmanagement.service.CookieService;
import com.appfire.taskmanagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class UserRestController {

    private final CookieService cookieService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @ModelAttribute UserDTO userRequest, HttpServletRequest request){
        if (cookieService.isSessionPresent(request.getCookies())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is logged in");
        }
        String id = userService.save(userRequest);
        if(id.equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken/length too small");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}
