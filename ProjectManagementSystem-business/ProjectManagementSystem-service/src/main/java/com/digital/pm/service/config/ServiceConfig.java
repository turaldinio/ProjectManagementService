package com.digital.pm.service.config;

import com.digital.pm.service.*;
import com.digital.pm.service.amqp.MessageConsume;
import com.digital.pm.service.amqp.MessageProduce;
import com.digital.pm.service.amqp.config.RabbitMqConfig;
import com.digital.pm.service.auth.AuthorizationService;
import com.digital.pm.service.CredentialService;
import com.digital.pm.service.auth.config.JWTAuthFilter;
import com.digital.pm.service.auth.config.JWTService;
import com.digital.pm.service.exceptions.controller.ExceptionController;
import com.digital.pm.service.mail.TestMailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.digital.pm.repository")
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

    @Bean
    public Logger rabbitLogger() {
        return LogManager.getLogger(RabbitMqConfig.class);

    }

    @Bean
    public Logger consumeLogger() {
        return LogManager.getLogger(MessageConsume.class);

    }

    @Bean
    public Logger produceLogger() {
        return LogManager.getLogger(MessageProduce.class);

    }

    @Bean
    public Logger emailLogger() {
        return LogManager.getLogger(TestMailSender.class);

    }


}
