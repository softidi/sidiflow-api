package com.softidi.sidiflow_api.user.service;

import com.softidi.sidiflow_api.user.dto.UserCreateRequest;
import com.softidi.sidiflow_api.user.dto.UserResponse;
import com.softidi.sidiflow_api.user.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();
    UserResponse findById(Long id);
    UserResponse save(UserCreateRequest request);
    UserResponse update(Long id, UserUpdateRequest request);
    void deleteById(Long id);
}
