package com.softidi.sidiflow_api.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "defaultCurrency")
    List<User> findByStatus(UserStatus status);
    Optional<User> findByIdAndStatus(Long id, UserStatus status);
    Optional<User> findByUsernameIgnoreCaseAndStatus(String username, UserStatus status);
    Optional<User> findByEmailIgnoreCaseAndStatus(String email, UserStatus status);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByUsernameIgnoreCaseAndIdNot(String username,Long id);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    boolean existsByIdAndStatus(Long id, UserStatus status);
}
