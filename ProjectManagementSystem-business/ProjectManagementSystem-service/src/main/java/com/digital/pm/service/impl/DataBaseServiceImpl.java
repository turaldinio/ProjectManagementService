package com.digital.pm.service.impl;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.model.employee.Employee;
import com.digital.pm.repository.EmployeeDataRepository;
import com.digital.pm.service.DataBaseService;
import com.digital.pm.service.mapping.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseServiceImpl implements DataBaseService {

    private final EmployeeDataRepository dataStorage;

    @Override
    public EmployeeDto create(CreateEmployeeDto createEmployeeDto) {
        try {
            Employee employee = EmployeeMapper.create(createEmployeeDto);

            employee = dataStorage.create(employee);
            if (employee == null) {
                throw new Exception("object creation exception");
            }
            return EmployeeMapper.map(employee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public EmployeeDto update(Long employeeId, CreateEmployeeDto createEmployeeDto) {
        Employee employee = EmployeeMapper.create(createEmployeeDto);
        return EmployeeMapper.map(dataStorage.update(employeeId, employee));

    }

    @Override
    public EmployeeDto getById(Long id) {
        try {
            var foundEmployee = dataStorage.getById(id);
            return EmployeeMapper.map(foundEmployee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<EmployeeDto> getAll() {
        var listEmployee = dataStorage.getAll();
        return EmployeeMapper.map(listEmployee);

    }

    @Override
    public EmployeeDto deleteById(Long id) {
        try {
            var employee = dataStorage.deleteById(id);
            return EmployeeMapper.map(employee);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<EmployeeDto> searchWithFilter(EmployeeFilter filterEmployee) {
        var listEmployee = dataStorage.searchWithFilter(filterEmployee);
        return EmployeeMapper.map(listEmployee);

    }
}
