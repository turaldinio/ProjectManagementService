package com.digital.pm.repository.impl;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.model.Employee;
import com.digital.pm.repository.DataStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataBaseDataStorageImpl implements DataStorage {
    private Connection connection;

    public DataBaseDataStorageImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Employee create(Employee employee)  {
        try (var insert = connection.prepareStatement("insert into employee(id,first_name, last_name, patronymic, post, account, email, status_id)" +
                "values (?,?,?,?,?,?,?,?)")) {
            int id = Statement.RETURN_GENERATED_KEYS;
            insert.setLong(1, id);
            insert.setString(2, employee.getFirsName());
            insert.setString(3, employee.getLastName());
            insert.setString(4, employee.getPatronymic());
            insert.setString(5, employee.getPost());
            insert.setString(6, employee.getAccount());
            insert.setString(7, employee.getEmail());
            insert.setInt(8, employee.getStatus().ordinal() + 1);

            insert.executeUpdate();

            var select = connection.prepareStatement("select * from employee where employee.id=?");
            select.setLong(1, id);


            var result = select.executeQuery();
            if (result.next()) {
                return employee;
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
