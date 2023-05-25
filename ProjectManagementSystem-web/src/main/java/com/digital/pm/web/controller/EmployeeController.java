package com.digital.pm.web.controller;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.service.EmployeeService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create")
    public EmployeeDto create(@RequestBody CreateEmployeeDto createEmployeeDto) {
        return employeeService.create(createEmployeeDto);


    }

    @PutMapping("/update/{id}")
    public EmployeeDto update(@PathVariable Long id, @RequestBody CreateEmployeeDto createEmployeeDto) {
        return employeeService.update(id, createEmployeeDto);


    }

    @GetMapping("/{id}")
    public EmployeeDto getById(@PathVariable Long id) {
        return employeeService.getById(id);
    }

    @GetMapping("/")
    public List<EmployeeDto> getAll() {
        return employeeService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

    @GetMapping("/find")
    public EmployeeDto findOne(EmployeeFilter employeeFilter) {
        return employeeService.findOne(employeeFilter);
    }
}
