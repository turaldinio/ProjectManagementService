package com.digital.pm.common.filters.employee;

import com.digital.pm.common.enums.EmployeeStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public class EmployeeDaoFilter {

    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Отчество")
    private String patronymic;
    @Schema(description = "Учетная запись")
    private String login;
    @Schema(description = "Адрес электронной почты")
    private String email;
    @Schema(description = "поиск по всем полям")
    private String text;

    @Schema(description = "Статус сотрудника")
    private EmployeeStatus employeeStatus;
}
