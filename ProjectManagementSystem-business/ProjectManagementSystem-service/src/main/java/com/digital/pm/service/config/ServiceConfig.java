package com.digital.pm.service.config;

import com.digital.pm.service.*;
import com.digital.pm.service.auth.AuthorizationService;
import com.digital.pm.service.CredentialService;
import com.digital.pm.service.auth.config.JWTAuthFilter;
import com.digital.pm.service.auth.config.JWTService;
import com.digital.pm.service.exceptions.controller.ExceptionController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:security.properties")

public class ServiceConfig {
    @Bean
    public Logger employeeLogger() {
        return LogManager.getLogger(EmployeeService.class);
    }

    @Bean
    public Logger credentialLogger() {
        return LogManager.getLogger(CredentialService.class);
    }

    @Bean
    public Logger projectLogger() {
        return LogManager.getLogger(ProjectService.class);
    }

    @Bean
    public Logger taskLogger() {
        return LogManager.getLogger(TaskService.class);
    }

    @Bean
    public Logger teamLogger() {
        return LogManager.getLogger(TeamService.class);

    }

    @Bean
    public Logger exceptionLogger() {
        return LogManager.getLogger(ExceptionController.class);

    }

    @Bean
    public Logger authLogger() {
        return LogManager.getLogger(AuthorizationService.class);

    }

    @Bean
    public Logger jwtLogger() {
        return LogManager.getLogger(JWTService.class);

    }
    @Bean
    public Logger jwtAuthFilterLogger() {
        return LogManager.getLogger(JWTAuthFilter.class);

    }

}
