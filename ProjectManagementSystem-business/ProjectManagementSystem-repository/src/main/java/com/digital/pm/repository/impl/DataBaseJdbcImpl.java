package com.digital.pm.repository.impl;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.dto.employee.FilterEmployee;
import com.digital.pm.model.Employee;
import com.digital.pm.repository.DataBaseRepository;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.*;

@RequiredArgsConstructor
public class DataBaseJdbcImpl implements DataBaseRepository {
    private final Connection connection;


    public Employee create(Employee employee) {
        try (var insert = connection.prepareStatement("insert into employee(id,first_name, last_name, patronymic, post, account, email, status_id)" +
                "values (?,?,?,?,?,?,?,?)")) {
            long id = getLastId() + 1;
            insert.setLong(1, id);
            insert.setString(2, employee.getFirsName());
            insert.setString(3, employee.getLastName());
            insert.setString(4, employee.getPatronymic());
            insert.setString(5, employee.getPost());
            insert.setString(6, employee.getAccount());
            insert.setString(7, employee.getEmail());
            insert.setInt(8, employee.getStatus().ordinal() + 1);

            insert.execute();

            var result = getById(id);

            if (result != null) {
                insert.close();
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private long getLastId() {
        var all = getAll();
        if (all.isEmpty()) {
            return 0;
        }
        return all.get(0).getId();

    }

    public Employee update(Long employeeId, Employee employee) {
        try {
            deleteById(employeeId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return create(employee);
    }

    public Employee getById(Long id) throws Exception {
        try (var select = connection.prepareStatement("select * from employee where employee.id=?")) {
            select.setLong(1, id);

            var resultSet = select.executeQuery();

            if (resultSet.next()) {
                return Employee.builder().
                        id(resultSet.getLong("id")).
                        firsName(resultSet.getString("first_name")).
                        lastName(resultSet.getString("last_name")).
                        patronymic(resultSet.getString("patronymic")).
                        account(resultSet.getString("account")).
                        email(resultSet.getString("email")).
                        post(resultSet.getString("post")).
                        status(EmployeeStatus.values()[resultSet.getInt("status_id")]).
                        build();
            }

        }
        return null;
    }

    public List<Employee> getAll() {
        try (var select = connection.prepareStatement("select * from employee e inner join employee_status es on e.status_id = es.id")) {
            var resultSet = select.executeQuery();
            List<Employee> list = new ArrayList<>();
            while (resultSet.next()) {

                var employee = Employee.builder().
                        id(resultSet.getLong("id")).
                        firsName(resultSet.getString("first_name")).
                        lastName(resultSet.getString("last_name")).
                        patronymic(resultSet.getString("patronymic")).
                        account(resultSet.getString("account")).
                        email(resultSet.getString("email")).
                        post(resultSet.getString("post")).
                        status(EmployeeStatus.valueOf(resultSet.getString("status"))).
                        build();
                list.add(employee);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public List<Employee> searchWithFilter(FilterEmployee filterEmployee) {
        String request = "select * from employee where 1=1";
        Map<Integer, Object> map = new HashMap<>();
        int paramCount = 1;
        if (filterEmployee.getId() != null) {
            request = request + " and id=?";
            map.put(paramCount++, filterEmployee.getId());
        }

        if (filterEmployee.getLastName() != null) {
            request = request + " and last_name=?";
            map.put(paramCount++, filterEmployee.getLastName());

        }

        if (filterEmployee.getFirsName() != null) {
            request = request + " and first_name=?";
            map.put(paramCount++, filterEmployee.getFirsName());

        }
        if (filterEmployee.getPatronymic() != null) {
            request = request + " and patronymic=?";
            map.put(paramCount++, filterEmployee.getPatronymic());

        }
        if (filterEmployee.getAccount() != null) {
            request = request + " and account=?";
            map.put(paramCount++, filterEmployee.getAccount());

        }
        if (filterEmployee.getEmail() != null) {
            request = request + " and email=?";
            map.put(paramCount++, filterEmployee.getEmail());

        }
        if (filterEmployee.getStatus() != null) {
            request = request + " and status_id=?";
            map.put(paramCount++, filterEmployee.getStatus());

        }
        if (filterEmployee.getPost() != null) {
            request = request + " and post=?";
            map.put(paramCount, filterEmployee.getPost());

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
                var employee = Employee.builder().
                        id(resultSet.getLong("id")).
                        firsName(resultSet.getString("first_name")).
                        lastName(resultSet.getString("last_name")).
                        patronymic(resultSet.getString("patronymic")).
                        account(resultSet.getString("account")).
                        email(resultSet.getString("email")).
                        post(resultSet.getString("post")).
                        status(EmployeeStatus.values()[resultSet.getInt("status_id")]).
                        build();
                list.add(employee);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
