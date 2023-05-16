package com.digital.pm.repository.impl;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.model.Employee;
import com.digital.pm.repository.DataStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataBaseDataStorageImpl implements DataStorage {
    private Connection connection;

    public DataBaseDataStorageImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Employee create(Employee employee) {
        try (var insert = connection.prepareStatement("insert into employee(first_name, last_name, patronymic, post, account, email, status_id)" +
                "values (?,?,?,?,?,?,?)")) {
            insert.setString(1, employee.getFirsName());
            insert.setString(2, employee.getLastName());
            insert.setString(3, employee.getPatronymic());
            insert.setString(4, employee.getPost());
            insert.setString(5, employee.getAccount());
            insert.setString(6, employee.getEmail());
            insert.setInt(7, employee.getStatus().ordinal());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
