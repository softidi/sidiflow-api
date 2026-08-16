package com.softidi.sidiflow_api.user.dto;

import com.softidi.sidiflow_api.currency.dto.CurrencyResponse;

public record UserResponse(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email,
        CurrencyResponse defaultCurrency,
        Short status
) {}
