package com.digital.pm.service.auth.config;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.repository.CredentialRepository;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.auth.impl.CustomUserDetailService;
import com.digital.pm.service.exceptions.BadRequest;
import com.google.gson.Gson;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final CustomUserDetailService customUserDetailService;
    private final Gson gson;
    private final EmployeeRepository employeeRepository;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!isPrivateRequest(request)) {//если запрос не на закрытый ресур, пропускаем его без проверки токена
                filterChain.doFilter(request, response);
                return;
            }
            String token = request.getHeader("auth-token") == null ?//запрос на приватный адрес.получаем токен
                    (request.getHeader("authorization") == null ?
                            null : request.getHeader("authorization"))
                    : request.getHeader("auth-token");

            if (token == null) {//если токена нет- ошибка
                writeForbiddenResponse(response, "no token provided");
                return;
            }
            if (token.startsWith("Bearer ")) {
                token = token.substring("Bearer ".length());
            }

            if (jwtService.isTokenExpired(token)) {//токен не истек?
                writeForbiddenResponse(response, "Token is expired.Get a new token at /auth/login");
                return;
            }

            if (jwtService.isTokenValid(token)) {//токен валидный?
                var userDetail = customUserDetailService.loadUserByUsername(jwtService.extractUserAccount(token));

                var currentEmployee = employeeRepository.findByCredential_Login(jwtService.extractUserAccount(token)).get();

                if (currentEmployee.getStatus().equals(EmployeeStatus.REMOTE)) {//если тек пользователь удален - ошибка
                    resolver.resolveException(request, response, null, new BadRequest("Remote user cannot be authorized"));
                }


                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetail.getUsername(),
                        userDetail.getPassword(), userDetail.getAuthorities()));//авторизовываем

                filterChain.doFilter(request, response);//передаем запрос дальше по фильтрам
            } else {
                writeForbiddenResponse(response, "invalidate token");
            }
        } catch (MalformedJwtException e) {
            writeForbiddenResponse(response, "invalidate token");
        }
    }

    public boolean isPrivateRequest(HttpServletRequest request) {//запрос на приватный адрес?
        return request.getRequestURI().contains("/private");
    }

    public void writeForbiddenResponse(HttpServletResponse response, String message) throws IOException {//вернуть ответ с ошибкой
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", message);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        var gsonMap = gson.toJson(errorDetails);

        response.getWriter().write(gsonMap);

    }


}
