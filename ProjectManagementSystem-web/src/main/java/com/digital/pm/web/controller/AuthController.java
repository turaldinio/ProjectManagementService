package com.digital.pm.web.controller;

import com.digital.pm.service.auth.AuthorizationRequest;
import com.digital.pm.service.auth.AuthorizationService;
import com.digital.pm.service.auth.AuthorizationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "AuthController", description = "Контроллер авторизации")
@ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthorizationResponse.class), mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Неправильные параметры аргумента", content = @Content(schema = @Schema(), mediaType = "application/json"))
})

public class AuthController {
    private final AuthorizationService authorizationService;
@Operation(summary = "авторизация пользователя",
        description = "Метод авторизации пользователя")
    @PostMapping("/login")
    public AuthorizationResponse login(@RequestBody AuthorizationRequest authorizationRequest) {
        return authorizationService.authorize(authorizationRequest);
    }

}
