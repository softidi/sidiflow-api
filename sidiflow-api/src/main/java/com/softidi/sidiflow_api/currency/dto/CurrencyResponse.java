package com.softidi.sidiflow_api.currency.dto;

public record CurrencyResponse (
        Long id,
        String name,
        String code,
        String symbol
){}
