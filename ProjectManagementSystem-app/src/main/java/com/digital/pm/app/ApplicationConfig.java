package com.digital.pm.app;

import com.digital.pm.service.impl.*;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {
    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public Logger employeeLogger() {
        return LogManager.getLogger(EmployeeServiceImpl.class);
    }

    @Bean
    public Logger credentialLogger() {
        return LogManager.getLogger(CredentialServiceImpl.class);
    }

    @Bean
    public Logger projectLogger() {
        return LogManager.getLogger(ProjectServiceImpl.class);
    }

    @Bean
    public Logger taskLogger() {
        return LogManager.getLogger(TaskServiceImpl.class);
    }

    @Bean
    public Logger teamLogger() {
        return LogManager.getLogger(TeamServiceImpl.class);

    }
    @Bean
    public Logger exceptionLogger() {
        return LogManager.getLogger("com.digital.pm.service.ExceptionLogger");

    }


}
