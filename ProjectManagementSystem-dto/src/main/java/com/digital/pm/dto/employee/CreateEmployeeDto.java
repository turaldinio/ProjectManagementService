package com.digital.pm.dto.employee;


import com.digital.pm.dto.credential.CreateCredentialDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDto {
    @Schema(description = "Имя")

    private String firstName;
    @Schema(description = "Фамилия")

    private String lastName;
    @Schema(description = "Отчество")

    private String patronymic;
    @Schema(description = "Должность")

    private String post;
    @Schema(description = "Учетная запись")

    private Long credentialId;
    @Schema(description = "Адрес электронной почты")

    private String email;
    @Schema(description = "Пароль")

    private String password;


}
