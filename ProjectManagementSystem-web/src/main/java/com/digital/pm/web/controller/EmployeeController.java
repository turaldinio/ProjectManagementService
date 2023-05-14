package com.digital.pm.web.controller;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.Impl.EmployeeServiceImpl;

public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController() {
        this.employeeService = new EmployeeServiceImpl();

    }

    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {
        return employeeService.create(createEmployeeDto);


    }


}
