package com.digital.pm.service.Impl;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.repository.DataStorage;
import com.digital.pm.repository.impl.DataStorageImpl;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.mapping.EmployeeMapper;
import pm.model.Employee;

import java.util.List;


public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final DataStorage dataStorage;

    public EmployeeServiceImpl() {
        employeeMapper = new EmployeeMapper();
        dataStorage = new DataStorageImpl();
    }

    @Override
    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {
        Employee employee = employeeMapper.create(createEmployeeDto);
        employee = dataStorage.create(employee);
        return employeeMapper.map(employee);


    }

    @Override
    public EmployeeDto update(int employeeId, CreateEmployeeDto createEmployeeDto) {
        Employee employee = employeeMapper.create(createEmployeeDto);
        return employeeMapper.map(dataStorage.update(employeeId, employee));

    }

    @Override
    public EmployeeDto getById(int id) {
        try {
            var foundEmployee = dataStorage.getById(id);
            return employeeMapper.map(foundEmployee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<EmployeeDto> getAll() {
        var listEmployee = dataStorage.getAll();
        return employeeMapper.map(listEmployee);

    }

    @Override
    public EmployeeDto deleteById(int id) {
        try {
            var employee = dataStorage.deleteById(id);
            return employeeMapper.map(employee);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
