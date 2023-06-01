package com.digital.pm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.digital.pm.repository")
@EntityScan(basePackages = "com.digital.pm.model")
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:security.properties")
})
@ComponentScan(value = "com.digital")
public class ProjectManagementSystemApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSystemApplication.class, args);

    }

}
