package com.softidi.sidiflow_api.user.dto;

import jakarta.validation.constraints.*;

public record UserUpdateRequest(
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
        @NotNull(message = "{validation.not-null}")
        @Positive(message = "{validation.positive}")
        Long defaultCurrencyId
) {}
