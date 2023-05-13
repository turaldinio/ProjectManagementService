package com.digital.pm.web.controller;

import com.digital.pm.service.EmployeeService;

public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;

    }


}
