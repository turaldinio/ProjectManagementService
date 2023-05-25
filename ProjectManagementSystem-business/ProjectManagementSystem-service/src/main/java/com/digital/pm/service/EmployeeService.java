package com.digital.pm.service;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.common.filters.EmployeeFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

public interface EmployeeService {
    ResponseEntity<?> create(CreateEmployeeDto createEmployeeDto);

    ResponseEntity<?> update(Long employeeId, CreateEmployeeDto createEmployeeDto);

    ResponseEntity<?> getById(Long id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> deleteById(Long id);

    ResponseEntity<?> findAll(EmployeeFilter filterEmployee);

    boolean existsById(Long executorId);
}
