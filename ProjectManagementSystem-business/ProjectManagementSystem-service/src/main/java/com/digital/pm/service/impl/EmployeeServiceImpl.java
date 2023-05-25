package com.digital.pm.service.impl;


import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.repository.spec.EmployeeSpecification;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.mapping.EmployeeMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public ResponseEntity<?> create(CreateEmployeeDto createEmployeeDto) {

        var employee = employeeMapper.create(createEmployeeDto);

        employeeRepository.save(employee);
        return ResponseEntity.ok(employeeMapper.map(employee));
    }

    @Override
    public ResponseEntity<?> update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        if (employeeRepository.existsById(employeeId)) {
            var newEmployee = employeeMapper.create(createEmployeeDto);
            newEmployee.setId(employeeId);

            employeeRepository.save(newEmployee);

            return ResponseEntity.ok(employeeMapper.map(newEmployee));
        } else {
            return new ResponseEntity<>(String.format("the user with %d id is not found", employeeId), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        var result = employeeRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(employeeMapper.map(result));
    }

    public ResponseEntity<?> deleteById(Long id) {
        var currentEmployee = employeeRepository.
                findById(id).orElse(null);

        if(currentEmployee==null){
            return  new ResponseEntity<>(String.format("the user with the %d id is not found ", id), HttpStatus.BAD_REQUEST);
        }
        if (currentEmployee.getStatus().equals(EmployeeStatus.REMOTE)) {
            return new ResponseEntity<>(String.format("the user with the %d id has already been deleted ", id), HttpStatus.BAD_REQUEST);
        }
        currentEmployee.setStatus(EmployeeStatus.REMOTE);

        var result = employeeRepository.save(currentEmployee);

        return ResponseEntity.ok(employeeMapper.map(result));
    }


    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(employeeMapper.map(employeeRepository.findAll()));
    }

    public ResponseEntity<?> findAll(EmployeeFilter employeeFilter) {

        var result = employeeRepository.
                findAll(EmployeeSpecification.getSpec(employeeFilter));

        return ResponseEntity.ok(employeeMapper.map(result));
    }

}
