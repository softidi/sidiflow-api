package com.softidi.sidiflow_api.user_role.service;

import com.softidi.sidiflow_api.role.RoleResponse;

import java.util.List;

public interface UserRoleService {
    List<RoleResponse> findAllByUserId(Long userId);
    void assignRole(Long userId, Long roleId);
    void removeRole(Long userId, Long roleId);
}
