package com.digital.pm.service.impl;


import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.model.employee.Employee;
import com.digital.pm.repository.spec.EmployeeSpecification;
import com.digital.pm.repository.EmployeeRepository;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.exceptions.BadRequest;
import com.digital.pm.service.mapping.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {
        if (createEmployeeDto.getFirstName() == null ||
                createEmployeeDto.getLastName() == null ||
                createEmployeeDto.getLastName().isBlank() ||
                createEmployeeDto.getFirstName().isBlank()) {
            throw new BadRequest("employee firstname or lastname cannot be null or blank");
        }
        getEmployeeByAccount(createEmployeeDto.getAccount());

        var employee = employeeMapper.create(createEmployeeDto);
        employeeRepository.save(employee);

        return employeeMapper.map(employee);
    }


    @Override
    public EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        getEmployeeById(employeeId);
        getEmployeeByAccount(createEmployeeDto.getAccount());

        var newEmployee = employeeMapper.create(createEmployeeDto);
        newEmployee.setId(employeeId);

        employeeRepository.save(newEmployee);

        return employeeMapper.map(newEmployee);
    }

    @Override
    public EmployeeDto getById(Long id) {
        return employeeMapper.map(getEmployeeById(id));
    }

    @Override
    public EmployeeDto deleteById(Long id) {
        var currentEmployee = getEmployeeById(id);
        if (currentEmployee.getStatus().equals(EmployeeStatus.REMOTE)) {
            throw new BadRequest(String.format("the user with %d id has already been deleted",id));
        }

        currentEmployee.setStatus(EmployeeStatus.REMOTE);
        var result = employeeRepository.save(currentEmployee);

        return employeeMapper.map(result);
    }


    public List<EmployeeDto> findAll() {
        return employeeMapper.map(employeeRepository.findAll());
    }

    public List<EmployeeDto> findAll(EmployeeFilter employeeFilter) {
        var result = employeeRepository.
                findAll(EmployeeSpecification.getSpec(employeeFilter));

        return employeeMapper.map(result);
    }

    private Employee getEmployeeById(Long executorId) {
        return employeeRepository.
                findById(executorId).
                orElseThrow(() -> new BadRequest(String.format("the Employee with %d id is is not found", executorId)));

    }

    private Employee getEmployeeByAccount(String account) {
        return employeeRepository.
                findByAccount(account).
                orElseThrow(() -> new UsernameNotFoundException(String.
                        format("User with %s account not found", account)));
    }
}
