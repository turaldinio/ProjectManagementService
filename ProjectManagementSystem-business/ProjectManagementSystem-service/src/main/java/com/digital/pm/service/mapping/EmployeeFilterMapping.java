package com.digital.pm.service.mapping;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.repository.filter.EmployeeDaoFilter;
import com.digital.pm.common.filters.employee.EmployeeDtoFilter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFilterMapping {
    public EmployeeDaoFilter create(EmployeeDtoFilter employeeDtoFilter) {
        return EmployeeDaoFilter.builder().
                login(employeeDtoFilter.getLogin()).
                text(employeeDtoFilter.getText()).
                patronymic(employeeDtoFilter.getPatronymic()).
                firstName(employeeDtoFilter.getFirstName()).
                lastName(employeeDtoFilter.getLastName()).
                email(employeeDtoFilter.getEmail()).
                employeeStatus(EmployeeStatus.ACTIVE).
                build();
    }
}
