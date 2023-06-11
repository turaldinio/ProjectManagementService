package com.digital.pm.app;

import com.digital.pm.repository.config.RepositoryConfiguration;
import com.digital.pm.service.config.ServiceConfig;
import com.digital.pm.web.controller.ControllerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication()
@Import(value = {ControllerConfig.class})
public class ProjectManagementSystemApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSystemApplication.class, args);


    }

}
