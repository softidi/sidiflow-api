package com.softidi.sidiflow_api.currency.service;

import com.softidi.sidiflow_api.currency.Currency;
import com.softidi.sidiflow_api.currency.CurrencyMapper;
import com.softidi.sidiflow_api.currency.CurrencyRepository;
import com.softidi.sidiflow_api.currency.dto.CurrencyRequest;
import com.softidi.sidiflow_api.currency.dto.CurrencyResponse;
import com.softidi.sidiflow_api.exception.ResourceAlreadyExistsException;
import com.softidi.sidiflow_api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService{

    private final CurrencyRepository repository;
    private final CurrencyMapper mapper;

    @Value("${error.resource.not-found}")
    private String resourceNotFoundMessage;
    @Value("${error.resource.already-exists}")
    private String resourceAlreadyExistsMessage;

    @Override
    @Transactional(readOnly = true)
    public List<CurrencyResponse> findAll() {
        return repository.findAllByIsActiveTrueOrderByNameAsc().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyResponse findByCode(String code) {
        Currency currency = repository.findByCodeAndIsActiveTrue(code.trim().toUpperCase(Locale.ROOT)).orElseThrow(
                () -> new ResourceNotFoundException(resourceNotFoundMessage)
        );
        return mapper.toResponse(currency);
    }

    @Override
    @Transactional
    public CurrencyResponse save(CurrencyRequest request) {
        Currency currency = mapper.toEntity(request);
        if(repository.existsByCodeIgnoreCase(currency.getCode())){
            throw new ResourceAlreadyExistsException(resourceAlreadyExistsMessage);
        }
        Currency saved = repository.save(currency);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CurrencyResponse update(Long id, CurrencyRequest request) {
        Currency currency = repository.findByIdAndIsActiveTrue(id).orElseThrow(
                () -> new ResourceNotFoundException(resourceNotFoundMessage)
        );
        if(repository.existsByCodeIgnoreCaseAndIdNot(request.code().trim().toUpperCase(Locale.ROOT), id)){
            throw new ResourceAlreadyExistsException(resourceAlreadyExistsMessage);
        }
        mapper.updateEntity(request, currency);
        return mapper.toResponse(currency);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Currency currency = repository.findByIdAndIsActiveTrue(id).orElseThrow(
                () -> new ResourceNotFoundException(resourceNotFoundMessage)
        );
        currency.setActive(false);
    }
}
