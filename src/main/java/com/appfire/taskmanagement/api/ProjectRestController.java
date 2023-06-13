package com.appfire.taskmanagement.api;

import com.appfire.taskmanagement.dto.ProjectDTO;
import com.appfire.taskmanagement.mapper.ProjectMapper;
import com.appfire.taskmanagement.model.Project;
import com.appfire.taskmanagement.model.Role;
import com.appfire.taskmanagement.model.User;
import com.appfire.taskmanagement.service.CookieService;
import com.appfire.taskmanagement.service.ProjectService;
import com.appfire.taskmanagement.service.SessionService;
import com.appfire.taskmanagement.service.UserProjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/project")
public class ProjectRestController {

    private final CookieService cookieService;
    private final SessionService sessionService;
    private final ProjectService projectService;
    private final UserProjectService userProjectService;

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<?> getAllProjectsFromSessionId(HttpServletRequest request) {
            String sessionToken = cookieService.getValue(request.getCookies());
            String userId = sessionService.findById(sessionToken).getUser().getId();

            return ResponseEntity
                    .ok()
                    .body(userProjectService
                            .findAllNotDeletedProjectsByUserId(userId)
                            .stream()
                            .map(ProjectMapper::mapToProjectDTO)
                            .toList());

    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> getProjectById(@PathVariable String id, HttpServletRequest request) {
        String sessionToken = cookieService.getValue(request.getCookies());
        String userId = sessionService.findById(sessionToken).getUser().getId();
        if (!userProjectService.checkUserIdAndProjectId(userId, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Project not active or user not part of the project");
        }

        return ResponseEntity.ok().body(projectService.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> createProject(@Valid @ModelAttribute Project project, HttpServletRequest request) {
        String sessionToken = cookieService.getValue(request.getCookies());
        User user = sessionService.findById(sessionToken).getUser();
        System.out.println(project);
        Project savedProject = projectService.save(project);
        if (savedProject.getId().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred when saving the project");
        }
        userProjectService.save(user, savedProject, Role.OWNER);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject.getId());
    }
}
