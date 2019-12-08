package com.thenextone.core.exceptions;

import com.google.gson.JsonObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class CoreErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = GroupNotFoundException.class)
    public ResponseEntity<Object> groupNotFoundException(RuntimeException e, HttpServletRequest request) {
        return this.error(e.getMessage(),request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedException(RuntimeException e, HttpServletRequest request) {
        return this.error(e.getMessage(),request, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Object> error(String message, HttpServletRequest request, HttpStatus status) {
        JsonObject error403 = new JsonObject();
        error403.addProperty("status", status.value());
        error403.addProperty("timestamp", LocalDateTime.now().toString());
        error403.addProperty("error", status.name());
        error403.addProperty("message", message);
        error403.addProperty("path", request.getServletPath());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type","application/json;charset=UTF-8");

        return new ResponseEntity<Object>(error403.toString(), responseHeaders, status);
    }
}
