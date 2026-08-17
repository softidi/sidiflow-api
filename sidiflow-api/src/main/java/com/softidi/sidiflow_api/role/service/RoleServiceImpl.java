package com.softidi.sidiflow_api.role.service;

import com.softidi.sidiflow_api.role.RoleMapper;
import com.softidi.sidiflow_api.role.RoleRepository;
import com.softidi.sidiflow_api.role.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper;

    private static final Short STATUS_ACTIVE = 1;

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> findAll() {
        return repository.findAllByStatusOrderByNameAsc(STATUS_ACTIVE).stream().map(mapper::toResponse).toList();
    }
}
