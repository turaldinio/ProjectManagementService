package com.digital.pm.service.impl;

import com.digital.pm.common.auth.AuthorizationRequest;
import com.digital.pm.service.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;

    private final JwtService jwtService;

    @Override
    public String authorize(AuthorizationRequest authorizationRequest) {
        var auth = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(authorizationRequest.getLogin(), authorizationRequest.getPassword(),
                        customUserDetailService.loadUserByUsername(authorizationRequest.getLogin()).getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        return jwtService.generateToken(auth);
    }
}
