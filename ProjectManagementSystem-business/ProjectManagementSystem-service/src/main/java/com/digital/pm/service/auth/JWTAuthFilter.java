package com.digital.pm.service.auth;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final CustomUserDetailService customUserDetailService;
    private final Gson gson;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isAuthorizationRequest(request)) {
            filterChain.doFilter(request,response);
            return;
        }
        String token = request.getHeader("auth-token") == null ?
                (request.getHeader("authorization") == null ?
                        null : request.getHeader("authorization"))
                : request.getHeader("auth-token");
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring("Bearer ".length());
        if (!jwtService.isTokenExpired(token)) {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", "Token is expired.Get a new token at /auth/login");

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            var gsonMap = gson.toJson(errorDetails);

            response.getWriter().write(gsonMap);
            return;
        }

        if (jwtService.isTokenValid(token)) {
            var userDetail = customUserDetailService.loadUserByUsername(jwtService.extractUserAccount(token));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetail.getUsername(),
                    userDetail.getPassword(), userDetail.getAuthorities()));
            if (isAuthorizationRequest(request)) {
                return;
            }
            filterChain.doFilter(request, response);

        }
    }

    public boolean isAuthorizationRequest(HttpServletRequest request) {
        return request.getRequestURI().equals("/auth/login");
    }


}
