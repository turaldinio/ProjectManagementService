package com.digital.pm.dto.credential;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class CredentialDto {
    @Schema(description = "Аккаунт")

    private String login;
}
