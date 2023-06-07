package com.digital.pm.app;

import com.digital.pm.service.impl.EmployeeServiceImpl;
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
    public Logger servicelogger() {
        return LogManager.getLogger(EmployeeServiceImpl.class);
    }



}
