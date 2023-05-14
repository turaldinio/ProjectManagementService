package com.digital.pm.repository;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import pm.model.Employee;

import java.util.List;

public interface DataStorage {
    Employee create(Employee employee);

    Employee update(int employeeId, Employee employee);

    Employee getById(int id) throws Exception;

    List<Employee> getAll();

    Employee deleteById(int id) throws Exception;
}
