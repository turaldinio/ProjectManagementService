package com.digital.pm.service.exceptions.controller;

import com.digital.pm.service.exceptions.BadRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionController {
    private final Gson gson;

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badRequest(RuntimeException runtimeException) {
        log.warn(String.format("%s: %s", runtimeException.getClass().getSimpleName(), runtimeException.getMessage()));
        return ResponseEntity.
                status(HttpStatus.UNAUTHORIZED).
                body(gson.
                        toJson(Map.of("message", runtimeException.getMessage())));

    }


    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<?> badRequest(BadRequest badRequest) {
        log.warn(String.format("%s: %s", badRequest.getClass().getSimpleName(), badRequest.getMessage()));
        return ResponseEntity.
                badRequest().
                body(gson.
                        toJson(Map.of("message", badRequest.getMessage())));

    }
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<?> badRequest(AuthenticationException authenticationException) {
        log.warn(String.format("%s: %s", authenticationException.getClass().getSimpleName(), authenticationException.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                body(gson.
                        toJson(Map.of("message", authenticationException.getMessage())));

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception exception) {
        log.error(String.format("%s: %s",exception.getClass().getSimpleName(),exception.getMessage()));
        return ResponseEntity.
                badRequest().
                body(gson.
                        toJson(Map.of("message", exception.getMessage())));

    }

}
