package com.digital.pm.service;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto create(CreateEmployeeDto createEmployeeDto);

    EmployeeDto update(long employeeId, CreateEmployeeDto createEmployeeDto);

    EmployeeDto getById(long id) ;

    List<EmployeeDto> getAll();

    EmployeeDto deleteById(long id);
}
