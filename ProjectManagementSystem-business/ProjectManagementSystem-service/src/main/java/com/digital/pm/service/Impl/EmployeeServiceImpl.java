package com.digital.pm.service.Impl;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.employee.FilterEmployee;
import com.digital.pm.repository.DataStorage;
import com.digital.pm.repository.impl.DataBaseStorageImpl;
import com.digital.pm.repository.impl.FileStorageImpl;
import com.digital.pm.service.EmployeeService;
import com.digital.pm.service.mapping.EmployeeMapper;
import com.digital.pm.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final DataStorage dataStorage;

    public EmployeeServiceImpl(Connection connection) {
        employeeMapper = new EmployeeMapper();
        dataStorage = new DataBaseStorageImpl(connection);
    }

    @Override
    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {
        try {
            Employee employee = employeeMapper.create(createEmployeeDto);

            employee = dataStorage.create(employee);
            if (employee == null) {
                throw new Exception("object creation exception");
            }
            return employeeMapper.map(employee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        Employee employee = employeeMapper.create(createEmployeeDto);
        return employeeMapper.map(dataStorage.update(employeeId, employee));

    }

    @Override
    public EmployeeDto getById(Long id) {
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
    public EmployeeDto deleteById(Long id) {
        try {
            var employee = dataStorage.deleteById(id);
            return employeeMapper.map(employee);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<EmployeeDto> searchWithFilter(FilterEmployee filterEmployee) {
        var listEmployee = dataStorage.searchWithFilter(filterEmployee);
        return employeeMapper.map(listEmployee);

    }
}
