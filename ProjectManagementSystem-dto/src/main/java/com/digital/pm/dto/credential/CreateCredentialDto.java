package com.digital.pm.dto.credential;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCredentialDto {
    @Schema(description = "Аккаунт")

    private String login;
    @Schema(description = "Пароль")

    private String password;
}
