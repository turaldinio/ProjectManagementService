package com.digital.pm.repository;

import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.model.Employee;

import java.util.List;

public interface EmployeeDataRepository {
    Employee create(Employee employee) throws Exception;

    Employee update(Long employeeId, Employee employee) throws Exception;

    Employee getById(Long id) throws Exception;

    List<Employee> getAll() throws Exception;

    Employee deleteById(Long id) throws Exception;

    List<Employee> searchWithFilter(EmployeeFilter filterEmployee) throws Exception;
}
