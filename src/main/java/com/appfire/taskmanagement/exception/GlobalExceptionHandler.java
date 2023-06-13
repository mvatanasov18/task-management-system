package com.appfire.taskmanagement.exception;

import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleEmailAlreadyTakenException(UsernameNotFoundException exception) {
        return new ErrorResponse("Username not found",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SessionNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleSessionNotFoundException(SessionNotFoundException exception) {
        return new ErrorResponse("Session not found",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PermissionDeniedDataAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handlePermissionDeniedDataAccessException(PermissionDeniedDataAccessException exception) {
        return new ErrorResponse(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleOtherExceptions(RuntimeException exception) {
        exception.printStackTrace();
        return new ErrorResponse("internal error",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
