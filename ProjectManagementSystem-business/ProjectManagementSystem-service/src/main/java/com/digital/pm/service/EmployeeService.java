package com.digital.pm.service;

import com.digital.pm.common.filters.employee.EmployeeDtoFilter;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto create(CreateEmployeeDto createEmployeeDto);

    EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto);

    EmployeeDto getById(Long id);

    List<EmployeeDto> findAll();

    EmployeeDto deleteById(Long id);

    List<EmployeeDto> findAll(EmployeeDtoFilter filterEmployee);

    EmployeeDto findByAccount(String account);


    Boolean existsById(Long executorId);
}
