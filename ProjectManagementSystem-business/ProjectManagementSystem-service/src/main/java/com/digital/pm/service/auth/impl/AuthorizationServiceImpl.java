package com.digital.pm.service.auth.impl;

import com.digital.pm.service.auth.AuthorizationService;
import com.digital.pm.service.auth.AuthorizationRequest;
import com.digital.pm.service.auth.AuthorizationResponse;
import com.digital.pm.service.auth.config.JWTService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Autowired
    @Qualifier("authLogger")
    private Logger logger;

    @Override
    public AuthorizationResponse authorize(AuthorizationRequest authorizationRequest) {
        logger.info(String.format("proceed to authorizing an employee with the %s login", authorizationRequest.getLogin()));
        var auth = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(authorizationRequest.getLogin(), authorizationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        logger.info(String.format("the employee with  %s login is logged in", authorizationRequest.getLogin()));

        var token = jwtService.generateToken(auth);

        logger.info("the token  was successfully created");

        return AuthorizationResponse.
                builder().
                token(token).
                build();
    }
}
