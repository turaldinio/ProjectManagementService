package com.digital.pm.service.impl;


import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.repository.spec.EmployeeSpecification;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.mapping.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {

        var employee = EmployeeMapper.create(createEmployeeDto);

        employeeRepository.save(employee);
        return EmployeeMapper.map(employee);
    }

    @Override
    public EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        var currentEmployee = employeeRepository.findById(employeeId).orElseThrow();


        currentEmployee = EmployeeMapper.create(createEmployeeDto);
        employeeRepository.save(currentEmployee);

        return EmployeeMapper.map(currentEmployee);
    }

    @Override
    public EmployeeDto getById(Long id) {
        var result = employeeRepository.findById(id).orElseThrow();
        return EmployeeMapper.map(result);
    }

    public void deleteById(Long id) {
        var currentEmployee = employeeRepository.
                findById(id).
                orElseThrow();
        currentEmployee.setStatus(EmployeeStatus.REMOTE);
    }


    public List<EmployeeDto> getAll() {
        return EmployeeMapper.map(employeeRepository.findAll());
    }

    public EmployeeDto findOne(EmployeeFilter employeeFilter) {

        var result = employeeRepository.
                findOne(EmployeeSpecification.getSpec(employeeFilter)).
                orElseThrow();

        return EmployeeMapper.map(result);
    }

}
