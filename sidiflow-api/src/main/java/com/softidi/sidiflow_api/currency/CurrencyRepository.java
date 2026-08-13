package com.softidi.sidiflow_api.currency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findAllByIsActiveTrueOrderByNameAsc();
    Optional<Currency> findByIdAndIsActiveTrue(Long id);
    Optional<Currency> findByCodeAndIsActiveTrue(String code);
    boolean existsByCodeIgnoreCase(String code);
    boolean existsByCodeIgnoreCaseAndIdNot(String code, Long id);
}
