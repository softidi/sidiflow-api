package com.softidi.sidiflow_api.role;

import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleResponse toResponse(Role role){
        return new RoleResponse(role.getId(), role.getName(), role.getDescription());
    }
}
