package com.digital.pm.service.exceptions.controller;

import com.digital.pm.service.exceptions.BadRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionController {
    private final Gson gson;


    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<?> badRequest(BadRequest badRequest) {
        log.warn(badRequest.getMessage());
        return ResponseEntity.
                badRequest().
                body(gson.
                        toJson(Map.of("message", badRequest.getMessage())));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception exception) {
        log.error(exception.getMessage());
        return ResponseEntity.
                badRequest().
                body(gson.
                        toJson(Map.of("message", exception.getMessage())));

    }

}
