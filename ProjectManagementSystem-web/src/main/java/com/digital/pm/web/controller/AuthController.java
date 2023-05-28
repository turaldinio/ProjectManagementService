package com.digital.pm.web.controller;

import com.digital.pm.common.auth.AuthorizationRequest;
import com.digital.pm.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public String login(@RequestBody AuthorizationRequest authorizationRequest) {
        return authorizationService.authorize(authorizationRequest);
    }

}
