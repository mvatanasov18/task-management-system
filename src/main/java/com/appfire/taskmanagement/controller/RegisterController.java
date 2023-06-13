package com.appfire.taskmanagement.controller;

import com.appfire.taskmanagement.dto.UserDTO;
import com.appfire.taskmanagement.service.CookieService;
import com.appfire.taskmanagement.service.NavbarService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@AllArgsConstructor
@RequestMapping(value = "/register")
public class RegisterController {

    private final CookieService cookieService;
    private final NavbarService navbarService;

    @GetMapping
    public ModelAndView getRegisterPage(HttpServletRequest request) {
        String sessionToken = "";
        if (cookieService.isSessionPresent(request.getCookies())) {
            return new ModelAndView("error/custom-error")
                    .addObject("message", "Access denied");
        }
        return new ModelAndView("register")
                .addObject("user", new UserDTO("","","",""))
                .addObject("navElements",
                        navbarService.getNavbar(sessionToken));
    }
}
