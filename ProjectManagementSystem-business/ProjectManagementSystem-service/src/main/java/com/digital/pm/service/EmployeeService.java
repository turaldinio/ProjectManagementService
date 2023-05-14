package com.digital.pm.service;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto create(CreateEmployeeDto createEmployeeDto);

    EmployeeDto update(int employeeId, CreateEmployeeDto createEmployeeDto);

    EmployeeDto getById(int id) ;

    List<EmployeeDto> getAll();

}
