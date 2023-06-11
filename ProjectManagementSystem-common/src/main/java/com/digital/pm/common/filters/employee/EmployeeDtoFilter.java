package com.digital.pm.common.filters.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDtoFilter {
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
}
