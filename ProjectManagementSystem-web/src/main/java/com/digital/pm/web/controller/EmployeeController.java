package com.digital.pm.web.controller;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.employee.FilterEmployee;
import com.digital.pm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
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

    public EmployeeDto deleteById(long id) {
        return employeeService.deleteById(id);
    }

    public List<EmployeeDto> searchByFilter(FilterEmployee filterEmployee) {
        return employeeService.searchWithFilter(filterEmployee);
    }
}
