package com.digital.pm.service.mapping;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import pm.model.Employee;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    public List<EmployeeDto> map(List<Employee> employeeList) {
        return employeeList.
                stream().
                map(x -> EmployeeDto.
                        builder().id(x.getId()).
                        fullName(x.getFirsName() + " " + x.getLastName() + " " + x.getPatronymic()).
                        post(x.getPost()).
                        account(x.getAccount()).
                        email(x.getEmail()).
                        status(EmployeeStatus.ACTIVE).
                        build()).collect(Collectors.toList());

    }
}
