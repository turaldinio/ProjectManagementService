package com.digital.pm.service.Impl;


import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.model.employee.EmployeeSpecification;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.mapping.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {
        EmployeeMapper employeeMapper = new EmployeeMapper();

        var employee = employeeMapper.create(createEmployeeDto);

        employeeRepository.save(employee);
        return employeeMapper.map(employee);
    }

    @Override
    public EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        return null;
    }

    @Override
    public EmployeeDto getById(Long id) {
        var result=employeeRepository.findById(id).orElseThrow();
        EmployeeMapper employeeMapper=new EmployeeMapper();
        return employeeMapper.map(result);
    }

    public void delete(CreateEmployeeDto createEmployeeDto) {
        var employee = new EmployeeMapper().create(createEmployeeDto);
        employeeRepository.delete(employee);
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeDto getEmployeeById(Long id) {
        var employee = employeeRepository.findById(id).orElseThrow();
        EmployeeMapper employeeMapper = new EmployeeMapper();
        return employeeMapper.map(employee);
    }

    public List<EmployeeDto> getAll() {
        EmployeeMapper employeeMapper = new EmployeeMapper();
        return employeeMapper.map(employeeRepository.findAll());
    }

    public EmployeeDto findOne(EmployeeFilter employeeFilter) {
        EmployeeMapper employeeMapper = new EmployeeMapper();

        var result = employeeRepository.
                findOne(EmployeeSpecification.getSpec(employeeFilter)).
                orElseThrow();

        return employeeMapper.map(result);
    }

}
