package com.digital.pm.service.impl;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.model.employee.Employee;
import com.digital.pm.repository.EmployeeDataRepository;
import com.digital.pm.service.EmployeeDataService;
import com.digital.pm.service.mapping.EmployeeMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor

public class EmployeeDataServiceImpl implements EmployeeDataService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeDataRepository dataStorage;

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
        }
        return null;

    }

    @Override
    public EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        try {
            Employee employee = employeeMapper.create(createEmployeeDto);
            return employeeMapper.map(dataStorage.update(employeeId, employee));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @Override
    public EmployeeDto getById(Long id) {
        try {
            var foundEmployee = dataStorage.getById(id);
            return employeeMapper.map(foundEmployee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @Override
    public List<EmployeeDto> getAll() {
        try {
            var listEmployee = dataStorage.getAll();
            return employeeMapper.map(listEmployee);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

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
    public List<EmployeeDto> searchWithFilter(EmployeeFilter filterEmployee) {
        try {
            var listEmployee = dataStorage.searchWithFilter(filterEmployee);
            return employeeMapper.map(listEmployee);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
}
