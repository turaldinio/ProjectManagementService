package com.digital.pm.service.Impl;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.repository.DataStorage;
import com.digital.pm.repository.impl.DataBaseDataStorageImpl;
import com.digital.pm.repository.impl.FileDataStorageImpl;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.mapping.EmployeeMapper;
import com.digital.pm.model.Employee;

import java.sql.Connection;
import java.util.List;


public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final DataStorage dataStorage;

    public EmployeeServiceImpl() {
        employeeMapper = new EmployeeMapper();
        dataStorage = new FileDataStorageImpl();
    }

    public EmployeeServiceImpl(Connection connection) {
        employeeMapper = new EmployeeMapper();
        dataStorage = new DataBaseDataStorageImpl(connection);
    }

    @Override
    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {
        try {
            Employee employee = employeeMapper.create(createEmployeeDto);
            if (employee == null) {
                throw new Exception("object creation procedure");
            }
            employee = dataStorage.create(employee);
            return employeeMapper.map(employee);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public EmployeeDto update(long employeeId, CreateEmployeeDto createEmployeeDto) {
        Employee employee = employeeMapper.create(createEmployeeDto);
        return employeeMapper.map(dataStorage.update(employeeId, employee));

    }

    @Override
    public EmployeeDto getById(long id) {
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
    public EmployeeDto deleteById(long id) {
        try {
            var employee = dataStorage.deleteById(id);
            return employeeMapper.map(employee);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
