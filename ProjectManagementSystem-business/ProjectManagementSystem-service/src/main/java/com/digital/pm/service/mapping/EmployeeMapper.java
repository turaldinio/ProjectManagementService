package com.digital.pm.service.mapping;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import pm.model.Employee;

import java.util.concurrent.atomic.AtomicInteger;

public class EmployeeMapper {
    private AtomicInteger atomicInteger = new AtomicInteger();

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
                id(atomicInteger.incrementAndGet()).
                build();


    }
}
