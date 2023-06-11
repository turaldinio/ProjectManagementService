package com.digital.pm.service.auth.impl;

import com.digital.pm.service.auth.AuthorizationService;
import com.digital.pm.service.auth.AuthorizationRequest;
import com.digital.pm.service.auth.AuthorizationResponse;
import com.digital.pm.service.auth.config.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    @Override
    public AuthorizationResponse authorize(AuthorizationRequest authorizationRequest) {
        log.info(String.format("proceed to authorizing an employee with the %s login", authorizationRequest.getLogin()));
        var auth = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(authorizationRequest.getLogin(), authorizationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        log.info(String.format("the employee with  %s login is logged in", authorizationRequest.getLogin()));

        var token = jwtService.generateToken(auth);

        log.info("the token  was successfully created");

        return AuthorizationResponse.
                builder().
                token(token).
                build();
    }
}
