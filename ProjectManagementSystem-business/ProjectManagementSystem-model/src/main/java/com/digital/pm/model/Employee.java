package com.digital.pm.model;

import com.digital.pm.common.enums.EmployeeStatus;
import lombok.*;

@Builder
@Data
public class Employee {
    private long id;
    private String firsName;
    private String lastName;
    private String patronymic;
    private String post;
    private String account;
    private String email;
    private EmployeeStatus status;


}
