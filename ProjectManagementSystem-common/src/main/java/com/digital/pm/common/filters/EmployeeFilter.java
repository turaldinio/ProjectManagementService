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
    private String firstName;
    private String lastName;
    private String patronymic;
    private String account;
    private String email;
}
