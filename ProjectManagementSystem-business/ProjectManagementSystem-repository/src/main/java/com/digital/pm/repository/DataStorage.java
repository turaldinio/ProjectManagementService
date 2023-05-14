package com.digital.pm.repository;

import pm.model.Employee;

import java.util.List;

public interface DataStorage {
    Employee create(Employee employee);

    Employee update();

    Employee getById(int id);

    List<Employee> getAll();

    void deleteById(int id);
}
