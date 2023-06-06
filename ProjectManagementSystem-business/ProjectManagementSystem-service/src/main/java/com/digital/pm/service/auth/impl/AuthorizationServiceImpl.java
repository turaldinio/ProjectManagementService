package com.digital.pm.service.auth.impl;

import com.digital.pm.service.AuthorizationService;
import com.digital.pm.service.auth.AuthorizationRequest;
import com.digital.pm.service.auth.AuthorizationResponse;
import com.digital.pm.service.auth.config.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;

    private final JWTService jwtService;

    @Override
    public AuthorizationResponse authorize(AuthorizationRequest authorizationRequest) {
        var auth = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(authorizationRequest.getLogin(), authorizationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        var token = jwtService.generateToken(auth);

        return AuthorizationResponse.
                builder().
                token(token).
                build();
    }
}
