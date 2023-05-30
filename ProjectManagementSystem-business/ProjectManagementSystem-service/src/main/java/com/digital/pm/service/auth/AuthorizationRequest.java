package com.digital.pm.service.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationRequest {
    @Schema(description = "Логин сотрудника")
    private String login;
    @Schema(description = "Пароль сотрудника")

    private String password;
}
