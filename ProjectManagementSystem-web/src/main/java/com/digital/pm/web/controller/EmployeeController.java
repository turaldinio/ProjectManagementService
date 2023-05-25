package com.digital.pm.web.controller;

import com.digital.pm.dto.employee.CreateEmployeeDto;

import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateEmployeeDto createEmployeeDto) {
        return employeeService.create(createEmployeeDto);


    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CreateEmployeeDto createEmployeeDto) {
        return employeeService.update(id, createEmployeeDto);


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return employeeService.getById(id);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return employeeService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return employeeService.deleteById(id);
    }

    @PostMapping("/find")
    public ResponseEntity<?> findAll(@RequestBody EmployeeFilter employeeFilter) {
        return employeeService.findAll(employeeFilter);
    }
}
