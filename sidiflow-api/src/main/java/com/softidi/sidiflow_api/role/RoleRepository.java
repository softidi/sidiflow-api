package com.softidi.sidiflow_api.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByStatusOrderByNameAsc(Short status);
    Optional<Role> findByIdAndStatus(Long id, Short status);
    Optional<Role> findByNameIgnoreCaseAndStatus(String name, Short status);
    boolean existsByNameIgnoreCaseAndStatus(String name, Short status);
}
