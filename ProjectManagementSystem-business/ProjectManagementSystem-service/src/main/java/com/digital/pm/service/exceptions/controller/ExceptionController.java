package com.digital.pm.service.exceptions.controller;

import com.digital.pm.service.exceptions.BadRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    private final Gson gson;

    @Autowired
    @Qualifier("exceptionLogger")
    private Logger logger;

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<?> badRequest(BadRequest badRequest) {
        logger.warn(badRequest.getMessage());
        return ResponseEntity.
                badRequest().
                body(gson.
                        toJson(Map.of("message", badRequest.getMessage())));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.
                badRequest().
                body(gson.
                        toJson(Map.of("message", exception.getMessage())));

    }

}
