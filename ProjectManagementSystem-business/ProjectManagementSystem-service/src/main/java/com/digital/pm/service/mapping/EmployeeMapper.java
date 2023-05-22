package com.digital.pm.service.mapping;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.model.employee.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static Employee create(CreateEmployeeDto createEmployeeDto) {

        return Employee.
                builder().
                id(createEmployeeDto.getId()).
                firstName(createEmployeeDto.getFirstName()).
                lastName(createEmployeeDto.getLastName()).
                patronymic(createEmployeeDto.getPatronymic()).
                post(createEmployeeDto.getPost()).
                account(createEmployeeDto.getAccount()).
                email(createEmployeeDto.getEmail()).
                status(createEmployeeDto.getStatus()).
                build();


    }

    public static EmployeeDto map(Employee employee) {
        return EmployeeDto.
                builder().
                id(employee.getId()).
                firstName(employee.getFirstName()).
                lastName(employee.getLastName()).
                patronymic(employee.getPatronymic()).
                post(employee.getPost()).
                account(employee.getAccount()).
                email(employee.getEmail()).
                status(EmployeeStatus.ACTIVE).
                build();


    }

    public static List<EmployeeDto> map(List<Employee> employeeList) {
        return employeeList.
                stream().
                map(x -> EmployeeDto.
                        builder().id(x.getId()).
                        firstName(x.getFirstName()).
                        lastName(x.getLastName()).
                        patronymic(x.getPatronymic()).post(x.getPost()).
                        account(x.getAccount()).
                        email(x.getEmail()).
                        status(EmployeeStatus.ACTIVE).
                        build()).
                collect(Collectors.toList());

    }
}
