package com.digital.pm.dto.employee;

import com.digital.pm.common.enums.EmployeeStatus;
import lombok.Data;

@Data
public class EmployeeDto {
    private int id;
    private String fullName;
    private String post;
    private String account;
    private String email;
    private EmployeeStatus status;
}
