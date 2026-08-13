package com.softidi.sidiflow_api.currency.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CurrencyRequest(
        @NotBlank(message = "{validation.not-blank}")
        @Size(max = 150, message = "{validation.size.max}")
        String name,
        @NotBlank(message = "{validation.not-blank}")
        @Pattern(regexp = "^[a-zA-Z]{3}$", message = "{validation.pattern}")
        String code,
        @NotBlank(message = "{validation.not-blank}")
        @Size(max = 10, message = "{validation.size.max}")
        String symbol
){}
