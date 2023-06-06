package com.digital.pm.dto.employee;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.dto.credential.CredentialDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {

    @Schema(description = "Идентификатор", defaultValue = "autoincrement")
    private Long id;
    @Schema(description = "Имя")

    private String firstName;
    @Schema(description = "Фамилия")

    private String lastName;
    @Schema(description = "Отчество")

    private String patronymic;
    @Schema(description = "Должность")

    private String post;
    @Schema(description = "id Учетной запись")

    private CredentialDto credentialDto;
    @Schema(description = "Адрес электронной почты")

    private String email;
    @Schema(description = "Статус сотрудника", defaultValue = "ACTIVE")

    private EmployeeStatus status;
}
