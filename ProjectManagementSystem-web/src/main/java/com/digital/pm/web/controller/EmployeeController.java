package com.digital.pm.web.controller;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;


    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {
        return employeeService.create(createEmployeeDto);


    }

    public EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        return employeeService.update(employeeId, createEmployeeDto);


    }

    public EmployeeDto getById(Long id) {
        return employeeService.getById(id);
    }

    public List<EmployeeDto> getAll() {
        return employeeService.getAll();
    }

    public void deleteById(long id) {
        employeeService.deleteById(id);
    }

    public EmployeeDto findOne(EmployeeFilter filterEmployee) {
        return employeeService.findOne(filterEmployee);
    }
}
