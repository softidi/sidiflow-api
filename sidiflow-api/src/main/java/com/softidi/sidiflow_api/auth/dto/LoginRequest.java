package com.softidi.sidiflow_api.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "{validation.not-blank}")
        String username,
        @NotBlank(message = "{validation.not-blank}")
        String password
) {}
