package com.digital.pm.service.Impl;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.repository.DataStorage;
import com.digital.pm.repository.impl.DataStorageImpl;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.mapping.EmployeeMapper;
import pm.model.Employee;


public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final DataStorage dataStorage;

    public EmployeeServiceImpl() {
        employeeMapper = new EmployeeMapper();
        dataStorage = new DataStorageImpl();
    }

    @Override
    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {
        Employee employee=employeeMapper.create(createEmployeeDto);
    }
}
