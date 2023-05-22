package com.digital.pm.repository;

import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.model.employee.Employee;

import java.util.List;

public interface EmployeeDataRepository {
    Employee create(Employee employee) ;

    Employee update(Long employeeId, Employee employee);

    Employee getById(Long id) throws Exception;

    List<Employee> getAll();

    Employee deleteById(Long id) throws Exception;

    List<Employee> searchWithFilter(EmployeeFilter filterEmployee);
}
