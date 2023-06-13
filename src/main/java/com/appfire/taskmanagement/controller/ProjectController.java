package com.appfire.taskmanagement.controller;

import com.appfire.taskmanagement.model.Project;
import com.appfire.taskmanagement.model.Status;
import com.appfire.taskmanagement.dto.TaskDTO;
import com.appfire.taskmanagement.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/projects_menu")
public class ProjectController {

    private final CookieService cookieService;
    private final NavbarService navbarService;

    @GetMapping
    public ModelAndView getProjects(HttpServletRequest request){
        String sessionToken = cookieService.getValue(request.getCookies());
        return new ModelAndView("projects")
                .addObject("project",new Project())
                .addObject("navElements", navbarService.getNavbar(sessionToken));
    }
    @GetMapping(value = "/{id}")
    public ModelAndView getProjectInfo(@PathVariable String id,HttpServletRequest request){
        String sessionToken = cookieService.getValue(request.getCookies());

        return new ModelAndView("tasks")
                .addObject("id",id)
                .addObject("task",new TaskDTO("","","","", Status.IDEA,false))
                .addObject("navElements", navbarService.getNavbar(sessionToken));
    }
}
