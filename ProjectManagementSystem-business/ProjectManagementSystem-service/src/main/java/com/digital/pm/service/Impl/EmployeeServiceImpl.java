package com.digital.pm.service.Impl;

import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.repository.DataStorage;
import com.digital.pm.repository.impl.DataStorageImpl;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.mapping.EmployeeMapper;


public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeMapper employeeMapper;
    private DataStorage dataStorage;

    public EmployeeServiceImpl() {
        employeeMapper = new EmployeeMapper();
        dataStorage = new DataStorageImpl();
    }

    @Override
    public EmployeeDto create() {
        return null;
    }
}
