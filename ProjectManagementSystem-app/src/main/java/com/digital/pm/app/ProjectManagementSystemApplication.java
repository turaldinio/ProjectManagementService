package com.digital.pm.app;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.digital"})
public class ProjectManagementSystemApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSystemApplication.class, args);


    }

}
