package com.digital.pm.service.mapping;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import pm.model.Employee;

import java.util.concurrent.atomic.AtomicInteger;

public class EmployeeMapper {

    public Employee create(CreateEmployeeDto createEmployeeDto) {

        return Employee.
                builder().
                firsName(createEmployeeDto.getFirstName()).
                lastName(createEmployeeDto.getLastName()).
                patronymic(createEmployeeDto.getPatronymic()).
                post(createEmployeeDto.getPost()).
                account(createEmployeeDto.getAccount()).
                email(createEmployeeDto.getEmail()).
                status(EmployeeStatus.ACTIVE).
                build();


    }

    public EmployeeDto map(Employee employee) {
        return EmployeeDto.
                builder().
                id(employee.getId()).
                fullName(employee.getFirsName() + " " +
                        employee.getLastName() + " " +
                        employee.getPatronymic()).
                post(employee.getPost()).
                account(employee.getAccount()).
                email(employee.getEmail()).
                status(EmployeeStatus.ACTIVE).
                build();


    }
}
