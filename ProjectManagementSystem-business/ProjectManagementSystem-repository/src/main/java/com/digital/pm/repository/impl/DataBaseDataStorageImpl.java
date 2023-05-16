package com.digital.pm.repository.impl;

import com.digital.pm.model.Employee;
import com.digital.pm.repository.DataStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class DataBaseDataStorageImpl implements DataStorage {
    private Connection connection;

    public DataBaseDataStorageImpl(Connection connection) {
        this.connection =connection;
    }

    @Override
    public Employee create(Employee employee) {
        return null;
    }

    @Override
    public Employee update(long employeeId, Employee employee) {
        return null;
    }

    @Override
    public Employee getById(long id) throws Exception {
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public Employee deleteById(long id) throws Exception {
        return null;
    }
}
