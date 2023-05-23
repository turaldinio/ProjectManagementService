package com.digital.pm.common.filters;

import com.digital.pm.common.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeFilter {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String post;
    private String account;
    private String email;
    private EmployeeStatus status;
}
