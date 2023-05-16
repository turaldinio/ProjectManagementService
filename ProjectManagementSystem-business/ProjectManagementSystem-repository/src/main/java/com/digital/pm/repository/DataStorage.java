package com.digital.pm.repository;

import com.digital.pm.model.Employee;

import java.util.List;

public interface DataStorage {
    Employee create(Employee employee);

    Employee update(long employeeId, Employee employee);

    Employee getById(long id) throws Exception;

    List<Employee> getAll();

    Employee deleteById(long id) throws Exception;
}
