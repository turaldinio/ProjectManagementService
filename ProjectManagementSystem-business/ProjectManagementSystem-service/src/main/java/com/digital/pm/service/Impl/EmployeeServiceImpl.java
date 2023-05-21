package com.digital.pm.service.Impl;


import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.model.Employee;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.mapping.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl {
    private final EmployeeRepository employeeRepository;

    public Employee create(CreateEmployeeDto createEmployeeDto) {
        var employee = new EmployeeMapper().create(createEmployeeDto);
        return employeeRepository.save(employee);
    }

    public void delete(CreateEmployeeDto createEmployeeDto) {
        var employee = new EmployeeMapper().create(createEmployeeDto);
        employeeRepository.delete(employee);
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

}
