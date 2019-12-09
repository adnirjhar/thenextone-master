package com.thenextone.core.exceptions;

import com.google.gson.JsonObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@ControllerAdvice
public class CoreErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = GroupNotFoundException.class)
    public ResponseEntity<Object> groupNotFoundException(RuntimeException e, HttpServletRequest request) {
        return error(e.getMessage(),request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedException(RuntimeException e, HttpServletRequest request) {
        return error(e.getMessage(),request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    public ResponseEntity<Object> unAuthorizedException(RuntimeException e, HttpServletRequest request) {
        return error(e.getMessage(),request, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<Object> error(String message, HttpServletRequest request, HttpStatus status) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type","application/json;charset=UTF-8");

        return new ResponseEntity<Object>(
                buildExceptionPayload(message,request,status).toString(),
                buildHeaders(),
                status
        );
    }

    public static JsonObject buildExceptionPayload(String message, HttpServletRequest request, HttpStatus status) {
        JsonObject error403 = new JsonObject();
        error403.addProperty("status", status.value());
        error403.addProperty("timestamp", LocalDateTime.now().toString());
        error403.addProperty("error", status.name());
        error403.addProperty("message", message);
        error403.addProperty("path", request.getServletPath());
        return error403;
    }

    public static HttpHeaders buildHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type","application/json;charset=UTF-8");
        return responseHeaders;
    }

    public static void writeExceptionToResponse(
            String message,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            HttpStatus status) throws IOException {

        httpServletResponse.setStatus(status.value());
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        JsonObject error = buildExceptionPayload(message, httpServletRequest,status);
        httpServletResponse.getWriter().write(error.toString());
    }
}
