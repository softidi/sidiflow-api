package com.softidi.sidiflow_api.role.service;

import com.softidi.sidiflow_api.role.Role;
import com.softidi.sidiflow_api.role.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> findAll();
}
