package com.appfire.taskmanagement.api;

import com.appfire.taskmanagement.mapper.TaskMapper;
import com.appfire.taskmanagement.model.Task;
import com.appfire.taskmanagement.dto.TaskDTO;
import com.appfire.taskmanagement.model.User;
import com.appfire.taskmanagement.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/v1/task")
@AllArgsConstructor
public class TaskRestController {

    private final CookieService cookieService;
    private final SessionService sessionService;
    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserProjectService userProjectService;

    @GetMapping(value = "/all/{projectId}")
    public ResponseEntity<?> getAllTasksByProjectId(@PathVariable String projectId, HttpServletRequest request){
        String sessionToken = cookieService.getValue(request.getCookies());
        User user = sessionService.findById(sessionToken).getUser();
        if(!userProjectService.checkUserIdAndProjectId(user.getId(),projectId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Project not active or user not part of the project");

        }
        return ResponseEntity
                .ok()
                .body(taskService.findAllByProjectIdAndNotDeleted(projectId)
                        .stream()
                        .map(TaskMapper::mapTaskToTaskDTO)
                        .toList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id,HttpServletRequest request){
        String sessionToken = cookieService.getValue(request.getCookies());
        String userId = sessionService.findById(sessionToken).getUser().getId();
        if(taskService.checkTaskByIdAndUserId(id, userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Project not active or user not part of the project");

        }
        return ResponseEntity.ok().body(TaskMapper.mapTaskToTaskDTO(taskService.findById(id)));
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Validated @ModelAttribute TaskDTO task, HttpServletRequest request){
        String sessionToken = cookieService.getValue(request.getCookies());
        String userId = sessionService.findById(sessionToken).getUser().getId();

        if(!userProjectService.checkUserIdAndProjectId(userId,task.projectId())){
            throw new PermissionDeniedDataAccessException("User not part of the given project",new RuntimeException());
        }

        taskService.save(task, projectService.findById(task.projectId()));
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void update(@Valid @RequestBody TaskDTO taskToUpdate, HttpServletRequest request ){
        validate(request.getCookies(),taskToUpdate.projectId(),taskToUpdate.id());

        taskService.updateById(taskToUpdate.id(),taskToUpdate);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(HttpServletRequest request, @PathVariable String id){
        Task task = taskService.findById(id);
        validate(request.getCookies(),task.getProject().getId(),id);

        taskService.delete(task);
    }


    private void validate(Cookie[] cookies, String projectId, String taskId){
        String sessionToken = cookieService.getValue(cookies);
        String userId = sessionService.findById(sessionToken).getUser().getId();

        if(!userProjectService.checkUserIdAndProjectId(userId,projectId)){
            throw new PermissionDeniedDataAccessException("User not part of the given project",new RuntimeException());
        }
        if(taskService.checkTaskByIdAndUserId(taskId, userId)){
            throw new PermissionDeniedDataAccessException("User not owning the given task",new RuntimeException());
        }
    }
}
