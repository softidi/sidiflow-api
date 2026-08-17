package com.softidi.sidiflow_api.user_role;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @EntityGraph(attributePaths = "role")
    List<UserRole> findAllByUserIdAndRoleStatusOrderByRoleNameAsc(Long userId, Short status);
    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);
}
