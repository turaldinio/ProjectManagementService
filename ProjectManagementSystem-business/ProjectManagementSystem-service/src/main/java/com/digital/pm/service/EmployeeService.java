package com.digital.pm.service;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.common.filters.EmployeeFilter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeService {
    EmployeeDto create(CreateEmployeeDto createEmployeeDto);

    EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto);

    EmployeeDto getById(Long id);

    List<EmployeeDto> findAll();

    EmployeeDto deleteById(Long id);

    List<EmployeeDto> findAll(EmployeeFilter filterEmployee);

    EmployeeDto findByAccount(String account);


    Boolean existsById(Long executorId);
}
