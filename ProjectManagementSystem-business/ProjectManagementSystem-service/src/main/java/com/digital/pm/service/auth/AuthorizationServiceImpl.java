package com.digital.pm.service.auth;

import com.digital.pm.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;

    private final JWTService jwtService;

    @Override
    public AuthorizationResponse authorize(AuthorizationRequest authorizationRequest) {
        var auth = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(authorizationRequest.getLogin(), authorizationRequest.getPassword(),
                        customUserDetailService.loadUserByUsername(authorizationRequest.getLogin()).getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        var token = jwtService.generateToken(auth);

        return AuthorizationResponse.
                builder().
                token(token).
                build();
    }
}
