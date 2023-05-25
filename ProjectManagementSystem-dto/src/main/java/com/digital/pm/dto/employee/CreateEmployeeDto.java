package com.digital.pm.dto.employee;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDto {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String post;
    private String account;
    private String email;


}
