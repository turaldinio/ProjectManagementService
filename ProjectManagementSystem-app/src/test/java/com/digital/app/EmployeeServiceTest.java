package com.digital.app;

import com.digital.pm.app.ProjectManagementSystemApplication;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.impl.EmployeeServiceImpl;
import com.digital.pm.service.mapping.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ProjectManagementSystemApplication.class)

public class EmployeeServiceTest {

    @Spy
    private EmployeeRepository employeeRepository;

    @Spy
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void a() {
        System.out.println(employeeService.findAll());
    }
}

