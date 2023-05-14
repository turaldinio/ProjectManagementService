package com.digital.pm.web.controller;

import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.Impl.EmployeeServiceImpl;

public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController() {
        this.employeeService = new EmployeeServiceImpl();

    }

    EmployeeDto create() {
        employeeService.create();

        return null;
    }


}
