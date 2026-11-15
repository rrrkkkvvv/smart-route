package com.rrrkkkvvv.smartRoute.auth.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @Email
        @NotNull
        String email,
        @NotNull
        String name,
        @NotNull
        String password
) {
}
