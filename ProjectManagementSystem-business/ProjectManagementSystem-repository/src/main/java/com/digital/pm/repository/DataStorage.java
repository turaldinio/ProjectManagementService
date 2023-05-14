package com.digital.pm.repository;

import pm.model.Employee;

import java.util.List;

public interface DataStorage {
    Employee create(Employee employee);

    Employee update(int employeeId,Employee employee);

    Employee getById(int id);

    List<Employee> getAll();

    void deleteById(int id);
}
