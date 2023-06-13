package com.appfire.taskmanagement.controller;

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
@RequestMapping(value = "/")
public class IndexController {

    private final CookieService cookieService;
    private final NavbarService navbarService;

    @GetMapping
    public ModelAndView getIndexPage(HttpServletRequest request) {
        String sessionToken = "";
        if (cookieService.isSessionPresent(request.getCookies())) {
            sessionToken = cookieService.getValue(request.getCookies());
        }
        return new ModelAndView("index")
                .addObject("navElements", navbarService
                        .getNavbar(sessionToken));
    }
}
