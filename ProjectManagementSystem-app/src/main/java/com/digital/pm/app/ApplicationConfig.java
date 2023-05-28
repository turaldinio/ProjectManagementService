package com.digital.pm.app;

import com.digital.pm.service.mapping.EmployeeMapper;
import com.digital.pm.service.mapping.ProjectMapper;
import com.digital.pm.service.mapping.TaskMapper;
import com.digital.pm.service.mapping.TeamMapper;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("com.digital")
public class ApplicationConfig {
    @Bean
    public Gson gson() {
        return new Gson();
    }

}
