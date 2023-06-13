package com.appfire.taskmanagement.controller;

import com.appfire.taskmanagement.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/task_menu/{id}")
public class TaskController {

    private final CookieService cookieService;
    private final NavbarService navbarService;

    @GetMapping
    public ModelAndView getTaskView(@PathVariable String id, HttpServletRequest request){
        String sessionToken = cookieService.getValue(request.getCookies());

        return new ModelAndView("task-edit")
                .addObject("id",id)
                .addObject("navElements", navbarService.getNavbar(sessionToken));
    }
}
