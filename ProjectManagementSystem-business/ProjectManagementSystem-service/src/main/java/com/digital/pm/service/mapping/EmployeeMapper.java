package com.digital.pm.service.mapping;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.employee.EmployeeDto;
import com.digital.pm.model.Credential;
import com.digital.pm.model.Employee;
import com.digital.pm.service.impl.CredentialService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final CredentialService credentialService;

    public Employee create(CreateEmployeeDto createEmployeeDto) {

        return Employee.
                builder().
                firstName(createEmployeeDto.getFirstName()).
                lastName(createEmployeeDto.getLastName()).
                patronymic(createEmployeeDto.getPatronymic()).
                post(createEmployeeDto.getPost()).
                email(createEmployeeDto.getEmail()).
                status(EmployeeStatus.ACTIVE).
                build();


    }

    public Employee create(CreateEmployeeDto createEmployeeDto, Credential credential) {

        return Employee.
                builder().
                firstName(createEmployeeDto.getFirstName()).
                lastName(createEmployeeDto.getLastName()).
                patronymic(createEmployeeDto.getPatronymic()).
                post(createEmployeeDto.getPost()).
                email(createEmployeeDto.getEmail()).
                status(EmployeeStatus.ACTIVE).
                credential(credential).
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
//        if (createEmployeeDto.getCredentialDto() != null) {
//            if (createEmployeeDto.getCredentialDto().getPassword() != null) {
//
//            }
//            if (createEmployeeDto.getCredentialDto().getLogin() != null) {
//
//            }
//            employee.setCredential(credentialService.findByLogin(createEmployeeDto.getCredentialDto()));
//        }
        if (createEmployeeDto.getEmail() != null) {
            employee.setEmail(createEmployeeDto.getEmail());
        }
        if (createEmployeeDto.getPost() != null) {
            employee.setPost(createEmployeeDto.getPost());
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
                email(employee.getEmail()).
                credentialDto(credentialService.map(employee.getCredential())).
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
                        credentialDto(credentialService.map(x.getCredential())).
                        status(x.getStatus()).
                        email(x.getEmail()).
                        build()).
                collect(Collectors.toList());

    }
}
