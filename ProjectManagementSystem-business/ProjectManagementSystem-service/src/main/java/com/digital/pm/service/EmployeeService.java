package com.digital.pm.service;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.employee.FilterEmployee;

import java.util.List;

public interface EmployeeService {
    EmployeeDto create(CreateEmployeeDto createEmployeeDto);

    EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto);

    EmployeeDto getById(Long id) ;

    List<EmployeeDto> getAll();

    EmployeeDto deleteById(Long id);

    List<EmployeeDto> searchWithFilter(FilterEmployee filterEmployee);
}
