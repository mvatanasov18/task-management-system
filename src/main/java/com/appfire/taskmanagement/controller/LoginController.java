package com.appfire.taskmanagement.controller;

import com.appfire.taskmanagement.dto.LoginRequestDto;
import com.appfire.taskmanagement.service.CookieService;
import com.appfire.taskmanagement.service.NavbarService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@AllArgsConstructor
@RequestMapping(value = "/login")
public class LoginController {

    private final CookieService cookieService;
    private final NavbarService navbarService;

    @GetMapping
    public ModelAndView getLoginPage(HttpServletRequest request) {
        String sessionToken = "";
        if (cookieService.isSessionPresent(request.getCookies())) {
            return new ModelAndView("error/custom-error")
                    .addObject("message", "Access denied");
        }

        return new ModelAndView("login")
                .addObject("user",new LoginRequestDto("",""))
                .addObject("navElements",
                        navbarService.getNavbar(sessionToken));
    }
}
