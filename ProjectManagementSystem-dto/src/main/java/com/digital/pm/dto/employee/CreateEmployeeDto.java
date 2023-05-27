package com.digital.pm.dto.employee;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDto {
    @NotNull(message = "employee firstName is not be null")
    private String firstName;
    @NotNull(message = "employee lastName is not be null")
    private String lastName;
    private String patronymic;
    private String post;
    private String account;
    private String email;
    private String password;


}
