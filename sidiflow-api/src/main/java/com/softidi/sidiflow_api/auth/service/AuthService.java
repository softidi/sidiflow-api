package com.softidi.sidiflow_api.auth.service;

import com.softidi.sidiflow_api.auth.dto.LoginRequest;
import com.softidi.sidiflow_api.auth.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
