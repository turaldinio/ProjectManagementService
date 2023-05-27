package com.digital.pm.dto.employee;

import com.digital.pm.common.enums.EmployeeStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String post;
    private String account;
    private String email;
    private EmployeeStatus status;
}
