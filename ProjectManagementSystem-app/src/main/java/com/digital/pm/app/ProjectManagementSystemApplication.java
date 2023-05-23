package com.digital.pm.app;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.web.controller.EmployeeController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor


public class ProjectManagementSystemApplication implements CommandLineRunner {
    private final EmployeeController employeeController;


    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSystemApplication.class, args);

    }

    @Override
    public void run(String... args) {
        System.out.println(employeeController.
                findOne(EmployeeFilter.
                        builder().
                        status(EmployeeStatus.ACTIVE).
                        build()));

    }


}
