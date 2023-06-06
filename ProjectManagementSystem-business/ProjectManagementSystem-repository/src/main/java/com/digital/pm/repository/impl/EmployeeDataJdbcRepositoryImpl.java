package com.digital.pm.repository.impl;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.model.Employee;
import com.digital.pm.repository.EmployeeDataRepository;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.*;

@RequiredArgsConstructor
public class EmployeeDataJdbcRepositoryImpl implements EmployeeDataRepository {
    private final Connection connection;


    public Employee create(Employee employee) throws Exception {
        try (var insert = connection.prepareStatement("insert into employee(id,first_name, last_name, patronymic, post, account, email, status)" +
                "values(id,first_name,last_name,patronymic,post,account,email,status) ")) {
            long id = getLastId() + 1;

            insert.setLong(1, id);
            insert.setString(2, employee.getFirstName());
            insert.setString(3, employee.getLastName());
            insert.setString(4, employee.getPatronymic());
            insert.setString(5, employee.getPost());
          //  insert.setString(6, employee.getAccount());
            insert.setString(6, employee.getEmail());
            insert.setString(7, employee.getStatus().name());

            insert.execute();

            var result = getById(id);

            if (result != null) {
                insert.close();
                return result;
            }
        }
        return null;
    }

    private long getLastId() throws Exception {
        var all = getAll();
        if (all.isEmpty()) {
            return 0;
        }
        return all.get(0).getId();

    }

    public Employee update(Long employeeId, Employee employee) throws Exception {
        deleteById(employeeId);
        return create(employee);
    }

    public Employee getById(Long id) throws Exception {
        try (var select = connection.prepareStatement("select * from employee where employee.id=?")) {
            select.setLong(1, id);

            var resultSet = select.executeQuery();

            if (resultSet.next()) {
                var employee = getEmployee(resultSet);
                resultSet.close();
                return employee;
            }
            resultSet.close();

        }
        return null;
    }

    public List<Employee> getAll() throws Exception {
        try (var select = connection.prepareStatement("select * from employee e ")) {
            var resultSet = select.executeQuery();
            List<Employee> list = new ArrayList<>();

            list.add(getEmployee(resultSet));

            resultSet.close();

            return list;
        }

    }

    public Employee deleteById(Long id) throws Exception {
        var employee = getById(id);
        try (var delete = connection.prepareStatement("delete from employee where id=?")) {
            delete.setLong(1, id);
            delete.executeUpdate();
        }

        return employee;
    }

    public List<Employee> searchWithFilter(EmployeeFilter filterEmployee) throws Exception {
        String request = "select * from employee where 1=1";
        Map<Integer, Object> map = new HashMap<>();
        int paramCount = 1;

        if (filterEmployee.getLastName() != null) {
            request = request + " and last_name=?";
            map.put(paramCount++, filterEmployee.getLastName());

        }

        if (filterEmployee.getFirstName() != null) {
            request = request + " and first_name=?";
            map.put(paramCount++, filterEmployee.getFirstName());

        }
        if (filterEmployee.getPatronymic() != null) {
            request = request + " and patronymic=?";
            map.put(paramCount++, filterEmployee.getPatronymic());

        }
        if (filterEmployee.getLogin() != null) {
            request = request + " and account=?";
            map.put(paramCount++, filterEmployee.getLogin());

        }
        if (filterEmployee.getEmail() != null) {
            request = request + " and email=?";
            map.put(paramCount++, filterEmployee.getEmail());

        }


        try (var search = connection.prepareStatement(request)) {
            for (Map.Entry<Integer, Object> pairs : map.entrySet()) {
                Integer key = pairs.getKey();
                Object value = pairs.getValue();
                search.setObject(key, value);
            }
            var resultSet = search.executeQuery();
            List<Employee> list = new ArrayList<>();

            while (resultSet.next()) {
                list.add(getEmployee(resultSet));
            }

            return list;
        }

    }

    public Employee getEmployee(ResultSet resultSet) throws Exception {
        while (resultSet.next()) {
            return Employee.builder().
                    id(resultSet.getLong("id")).
                    firstName(resultSet.getString("first_name")).
                    lastName(resultSet.getString("last_name")).
                    patronymic(resultSet.getString("patronymic")).
             //       account(resultSet.getString("account")).
                    email(resultSet.getString("email")).
                    post(resultSet.getString("post")).
                    status(EmployeeStatus.valueOf(resultSet.getString("status"))).
                    build();
        }
        return null;

    }
}
