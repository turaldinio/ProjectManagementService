package com.digital.pm.service.mapping;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.model.employee.Employee;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public  Employee create(CreateEmployeeDto createEmployeeDto) {

        return Employee.
                builder().
                firstName(createEmployeeDto.getFirstName()).
                lastName(createEmployeeDto.getLastName()).
                patronymic(createEmployeeDto.getPatronymic()).
                post(createEmployeeDto.getPost()).
                account(createEmployeeDto.getAccount()).
                email(createEmployeeDto.getEmail()).
                status(EmployeeStatus.ACTIVE).
                build();


    }

    public  EmployeeDto map(Employee employee) {
        return EmployeeDto.
                builder().
                id(employee.getId()).
                firstName(employee.getFirstName()).
                lastName(employee.getLastName()).
                patronymic(employee.getPatronymic()).
                post(employee.getPost()).
                account(employee.getAccount()).
                email(employee.getEmail()).
                status(employee.getStatus()).
                build();


    }

    public  List<EmployeeDto> map(List<Employee> employeeList) {
        return employeeList.
                stream().
                map(x -> EmployeeDto.
                        builder().id(x.getId()).
                        firstName(x.getFirstName()).
                        lastName(x.getLastName()).
                        patronymic(x.getPatronymic()).post(x.getPost()).
                        account(x.getAccount()).
                        email(x.getEmail()).
                        status(x.getStatus()).
                        build()).
                collect(Collectors.toList());

    }
}
