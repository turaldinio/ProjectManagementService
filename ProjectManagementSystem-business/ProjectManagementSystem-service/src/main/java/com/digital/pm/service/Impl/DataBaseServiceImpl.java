package com.digital.pm.service.Impl;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.dto.employee.FilterEmployee;
import com.digital.pm.model.Employee;
import com.digital.pm.repository.DataBaseRepository;
import com.digital.pm.service.DataBaseService;
import com.digital.pm.service.mapping.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseServiceImpl implements DataBaseService {

    private final EmployeeMapper employeeMapper;
    private final DataBaseRepository dataStorage;

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
