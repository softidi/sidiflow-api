package com.softidi.sidiflow_api.role.service;

import com.softidi.sidiflow_api.role.dto.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> findAll();
}
