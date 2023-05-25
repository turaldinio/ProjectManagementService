package com.digital.pm.service.impl;


import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.repository.spec.EmployeeSpecification;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.mapping.EmployeeMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {

        var employee = employeeMapper.create(createEmployeeDto);

        employeeRepository.save(employee);
        return employeeMapper.map(employee);
    }

    @Override
    public EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        if (employeeRepository.existsById(employeeId)) {
            var newEmployee = employeeMapper.create(createEmployeeDto);
            newEmployee.setId(employeeId);

            employeeRepository.save(newEmployee);

            return employeeMapper.map(newEmployee);
        } else {
            return null;
        }
    }

    @Override
    public EmployeeDto getById(Long id) {
        var result = employeeRepository.findById(id).orElseThrow();
        return employeeMapper.map(result);
    }

    public void deleteById(Long id) {
        var currentEmployee = employeeRepository.
                findById(id).
                orElseThrow();
        currentEmployee.setStatus(EmployeeStatus.REMOTE);
    }


    public List<EmployeeDto> getAll() {
        return employeeMapper.map(employeeRepository.findAll());
    }

    public List<EmployeeDto> findAll(EmployeeFilter employeeFilter) {

        var result = employeeRepository.
                findAll(EmployeeSpecification.getSpec(employeeFilter));

        return employeeMapper.map(result);
    }

}
