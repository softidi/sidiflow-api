package com.softidi.sidiflow_api.user.dto;

import jakarta.validation.constraints.*;

public record UserCreateRequest(
        @NotBlank(message = "{validation.not-blank}")
        @Size(max = 150, message = "{validation.size.max}")
        String username,
        @Size(max = 150, message = "{validation.size.max}")
        String firstName,
        @Size(max = 150, message = "{validation.size.max}")
        String lastName,
        @NotBlank(message = "{validation.not-blank}")
        @Email
        @Size(max = 150, message = "{validation.size.max}")
        String email,
        @NotBlank(message = "{validation.not-blank}")
        @Size(min = 8, max = 100, message = "{validation.size}")
        String password,
        @NotNull(message = "{validation.not-null}")
        @Positive(message = "{validation.positive}")
        Long defaultCurrencyId
) {}
