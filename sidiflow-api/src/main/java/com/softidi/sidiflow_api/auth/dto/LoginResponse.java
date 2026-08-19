package com.softidi.sidiflow_api.auth.dto;

public record LoginResponse(
        String accessToken,
        String tokenType,
        Long expiresIn
) {}
