package com.digital.pm.service;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.common.filters.EmployeeFilter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeService {
    @Transactional
    EmployeeDto create(CreateEmployeeDto createEmployeeDto);

    @Transactional
    EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto);

    EmployeeDto getById(Long id);

    List<EmployeeDto> findAll();

    @Transactional
    EmployeeDto deleteById(Long id);

    List<EmployeeDto> findAll(EmployeeFilter filterEmployee);

    EmployeeDto findByAccount(String account);


    Boolean existsById(Long executorId);
}
