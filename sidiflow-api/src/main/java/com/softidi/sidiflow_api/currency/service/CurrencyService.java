package com.softidi.sidiflow_api.currency.service;

import com.softidi.sidiflow_api.currency.dto.CurrencyRequest;
import com.softidi.sidiflow_api.currency.dto.CurrencyResponse;

import java.util.List;

public interface CurrencyService {
    List<CurrencyResponse> findAll();
    CurrencyResponse findByCode(String code);
    CurrencyResponse save(CurrencyRequest request);
    CurrencyResponse update(Long id, CurrencyRequest request);
    void deleteById(Long id);
}
