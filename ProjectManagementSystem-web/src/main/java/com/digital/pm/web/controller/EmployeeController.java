package com.digital.pm.web.controller;

import com.digital.pm.dto.employee.CreateEmployeeDto;

import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/private/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<EmployeeDto> create( @RequestBody CreateEmployeeDto createEmployeeDto) {
        return ResponseEntity.ok(employeeService.create(createEmployeeDto));


    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable Long id, @Valid @RequestBody CreateEmployeeDto createEmployeeDto) {
        return ResponseEntity.ok(employeeService.update(id, createEmployeeDto));


    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EmployeeDto> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.deleteById(id));
    }

    @PostMapping("/find")
    public ResponseEntity<List<EmployeeDto>> findAll(@RequestBody EmployeeFilter employeeFilter) {
        return ResponseEntity.ok(employeeService.findAll(employeeFilter));
    }
}
