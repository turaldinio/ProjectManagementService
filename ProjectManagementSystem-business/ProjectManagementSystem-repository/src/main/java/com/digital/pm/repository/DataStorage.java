package com.digital.pm.repository;

import com.digital.pm.dto.employee.FilterEmployee;
import com.digital.pm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DataStorage extends JpaRepository<Employee,Long> {
    Employee create(Employee employee) ;

    Employee update(Long employeeId, Employee employee);

    Employee getById(Long id);

    List<Employee> getAll();

    void deleteById(Long id);

    List<Employee> searchWithFilter(FilterEmployee filterEmployee);
}
