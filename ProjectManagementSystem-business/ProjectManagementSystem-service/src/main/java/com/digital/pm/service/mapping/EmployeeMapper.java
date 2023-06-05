package com.digital.pm.service.mapping;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.model.Employee;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeMapper {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Employee create(CreateEmployeeDto createEmployeeDto) {

        return Employee.
                builder().
                firstName(createEmployeeDto.getFirstName()).
                lastName(createEmployeeDto.getLastName()).
                patronymic(createEmployeeDto.getPatronymic()).
                post(createEmployeeDto.getPost()).
                credentialId(createEmployeeDto.getCredentialId()).
                email(createEmployeeDto.getEmail()).
                status(EmployeeStatus.ACTIVE).
                build();


    }

    public Employee update(Employee employee, CreateEmployeeDto createEmployeeDto) {
        if (createEmployeeDto.getFirstName() != null) {
            employee.setFirstName(createEmployeeDto.getFirstName());
        }
        if (createEmployeeDto.getLastName() != null) {
            employee.setLastName(createEmployeeDto.getLastName());
        }
        if (createEmployeeDto.getPatronymic() != null) {
            employee.setPatronymic(createEmployeeDto.getPatronymic());
        }
        if (createEmployeeDto.getCredentialId() != null) {
            employee.setCredentialId(createEmployeeDto.getCredentialId());
        }
        if (createEmployeeDto.getEmail() != null) {
            employee.setEmail(createEmployeeDto.getEmail());
        }
        if (createEmployeeDto.getPost() != null) {
            employee.setPost(createEmployeeDto.getPost());
        }
        if (createEmployeeDto.getCredentialId() != null) {
            employee.setCredentialId(createEmployeeDto.getCredentialId());
        }
        return employee;
    }

    public EmployeeDto map(Employee employee) {
        return EmployeeDto.
                builder().
                id(employee.getId()).
                firstName(employee.getFirstName()).
                lastName(employee.getLastName()).
                patronymic(employee.getPatronymic()).
                post(employee.getPost()).
                credentialId(employee.getCredentialId()).
                status(employee.getStatus()).
                build();


    }

    public List<EmployeeDto> map(List<Employee> employeeList) {
        return employeeList.
                stream().
                map(x -> EmployeeDto.
                        builder().id(x.getId()).
                        firstName(x.getFirstName()).
                        lastName(x.getLastName()).
                        patronymic(x.getPatronymic()).post(x.getPost()).
                        credentialId(x.getCredentialId()).
                        status(x.getStatus()).
                        build()).
                collect(Collectors.toList());

    }
}
