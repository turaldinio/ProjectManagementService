package com.digital.pm.service.exceptions.controller;

import com.digital.pm.service.exceptions.BadRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.UnknownNamedQueryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    private final Gson gson;

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<?> badRequest(BadRequest badRequest) {
        return ResponseEntity.
                badRequest().
                body(gson.
                        toJson(Map.of("message", badRequest.getMessage())));

    }

}
