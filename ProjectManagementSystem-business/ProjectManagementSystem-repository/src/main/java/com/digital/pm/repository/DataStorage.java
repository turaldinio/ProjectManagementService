package com.digital.pm.repository;

import com.digital.pm.dto.employee.FilterEmployee;
import com.digital.pm.model.Employee;

import java.util.List;

public interface DataStorage {
    Employee create(Employee employee) ;

    Employee update(Long employeeId, Employee employee);

    Employee getById(Long id) throws Exception;

    List<Employee> getAll();

    Employee deleteById(Long id) throws Exception;

    List<Employee> searchWithFilter(FilterEmployee filterEmployee);
}
