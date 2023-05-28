package com.digital.pm.web.controller;

import com.digital.pm.common.auth.AuthorizationRequest;
import com.digital.pm.service.AuthorizationService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthorizationService authorizationService;
    private final Gson gson;

    @PostMapping("/login")
    public String login(@RequestBody AuthorizationRequest authorizationRequest) {
        var token = authorizationService.authorize(authorizationRequest);
        return gson.toJson(Map.of("token", token));
    }

}
