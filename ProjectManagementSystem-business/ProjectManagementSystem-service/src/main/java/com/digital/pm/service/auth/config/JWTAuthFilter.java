package com.digital.pm.service.auth.config;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.auth.impl.CustomUserDetailService;
import com.digital.pm.service.exceptions.BadRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final CustomUserDetailService customUserDetailService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!isPrivateRequest(request)) {//если запрос не на закрытый ресур, пропускаем его без проверки токена
                log.info(String.format("the request %s for a open resource was accepted", request.getRequestURI()));
                filterChain.doFilter(request, response);
                return;
            }

            log.info(String.format("the request %s for a closed resource was accepted", request.getRequestURI()));

            String token = request.getHeader("auth-token") == null ?//запрос на приватный адрес.получаем токен
                    (request.getHeader("authorization") == null ?
                            null : request.getHeader("authorization"))
                    : request.getHeader("auth-token");

            log.info("checking the availability of a token");
            if (token == null) {//если токена нет- ошибка
                throw new BadRequest("no token provided");
            }

            if (token.startsWith("Bearer ")) {
                token = token.substring("Bearer ".length());
            }

            log.info("checking the validity of the token");
            if (!jwtService.isTokenValid(token)) {//токен валидный?
                resolver.resolveException(request, response, null, new BadRequest("invalid token"));
            }

            log.info("checking that the token is not outdated");
            if (jwtService.isTokenExpired(token)) {//токен не истек?
                resolver.resolveException(request, response, null, new BadRequest("token is expired.Get a new token at /auth/login"));
            }
            log.info("the request contains a valid token");

            var userDetail = customUserDetailService.loadUserByUsername(jwtService.extractUserAccount(token));

            var currentEmployee = employeeRepository.findByCredential_Login(jwtService.extractUserAccount(token)).get();

            log.info("verification: is the employee active?");

            if (currentEmployee.getStatus().equals(EmployeeStatus.REMOTE)) {//если тек пользователь удален - ошибка
                resolver.resolveException(request, response, null, new BadRequest("Remote user cannot be authorized"));
            }

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetail.getUsername(),
                    userDetail.getPassword(), userDetail.getAuthorities()));//авторизовываем

            log.info("the request was successfully authorized");

            filterChain.doFilter(request, response);//передаем запрос дальше по фильтрам
        } catch (Exception e) {
            resolver.resolveException(request, response, null, new AuthenticationException(e.getMessage()));
        }
    }

    public boolean isPrivateRequest(HttpServletRequest request) {//запрос на приватный адрес?
        return request.getRequestURI().contains("/private");
    }


}
