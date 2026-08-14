package com.softidi.sidiflow_api.currency.service;

import com.softidi.sidiflow_api.currency.Currency;
import com.softidi.sidiflow_api.currency.CurrencyMapper;
import com.softidi.sidiflow_api.currency.CurrencyRepository;
import com.softidi.sidiflow_api.currency.dto.CurrencyRequest;
import com.softidi.sidiflow_api.currency.dto.CurrencyResponse;
import com.softidi.sidiflow_api.exception.ResourceAlreadyExistsException;
import com.softidi.sidiflow_api.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {

    @Mock
    private CurrencyRepository repository;

    @Mock
    private CurrencyMapper mapper;

    @InjectMocks
    private CurrencyServiceImpl service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(
                service,
                "resourceNotFoundMessage",
                "Registro no encontrado."
        );

        ReflectionTestUtils.setField(
                service,
                "resourceAlreadyExistsMessage",
                "El registro ya existe."
        );
    }

    @Test
    void shouldReturnAllActiveCurrencies(){
        List<Currency> currencies = List.of(
                new Currency("Peso MX","MXN","$"),
                new Currency("Dolar","USD","$")
        );

        List<CurrencyResponse> currencyResponses = List.of(
                new CurrencyResponse(1L,"Peso MX","MXN","$"),
                new CurrencyResponse(2L,"Dolar","USD","$")
        );

        when(repository.findAllByIsActiveTrueOrderByNameAsc()).thenReturn(currencies);
        when(mapper.toResponse(currencies.get(0))).thenReturn(currencyResponses.get(0));
        when(mapper.toResponse(currencies.get(1))).thenReturn(currencyResponses.get(1));

        List<CurrencyResponse> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("MXN", result.get(0).code());
        assertEquals("USD", result.get(1).code());
        verify(repository).findAllByIsActiveTrueOrderByNameAsc();
        verify(mapper).toResponse(currencies.get(0));
        verify(mapper).toResponse(currencies.get(1));
    }

    @Test
    void shouldReturnAllActiveCurrenciesEmpty(){
        when(repository.findAllByIsActiveTrueOrderByNameAsc()).thenReturn(new ArrayList<>());

        List<CurrencyResponse> result = service.findAll();

        assertEquals(0, result.size());
        verify(repository).findAllByIsActiveTrueOrderByNameAsc();
        verify(mapper, never()).toResponse(any());
    }

    @Test
    void shouldReturnCurrencyByCode(){
        CurrencyResponse response = new CurrencyResponse(1L, "Peso MX","MXN","$");

        Currency currency = new Currency();
        currency.setName("Peso MX");
        currency.setCode("MXN");
        currency.setSymbol("$");

        when(repository.findByCodeAndIsActiveTrue(currency.getCode())).thenReturn(Optional.of(currency));
        when(mapper.toResponse(currency)).thenReturn(response);

        CurrencyResponse currencyDB = service.findByCode(" mxn ");

        assertNotNull(currencyDB);
        assertEquals(1L, currencyDB.id());
        assertEquals("MXN", currencyDB.code());
        assertEquals("Peso MX", currencyDB.name());
        assertEquals("$", currencyDB.symbol());

        verify(repository).findByCodeAndIsActiveTrue(currency.getCode());
        verify(mapper).toResponse(currency);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenCurrencyCodeNotFound(){
        when(repository.findByCodeAndIsActiveTrue("MXN")).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> service.findByCode("MXN")
        );

        assertEquals("Registro no encontrado.", ex.getMessage());

        verify(repository).findByCodeAndIsActiveTrue("MXN");
        verify(mapper,never()).toResponse(any());
    }

    @Test
    void shouldSaveCurrencySuccessfully(){
        CurrencyRequest request = new CurrencyRequest("Peso MX","MXN","$");

        Currency currency = new Currency();
        currency.setName("Peso MX");
        currency.setCode("MXN");
        currency.setSymbol("$");

        Currency savedCurrency = new Currency();
        savedCurrency.setId(1L);
        savedCurrency.setName("Peso MX");
        savedCurrency.setCode("MXN");
        savedCurrency.setSymbol("$");

        CurrencyResponse response = new CurrencyResponse(1L, "Peso MX","MXN","$");

        when(mapper.toEntity(request)).thenReturn(currency);
        when(repository.existsByCodeIgnoreCase("MXN")).thenReturn(false);
        when(repository.save(currency)).thenReturn(savedCurrency);
        when(mapper.toResponse(savedCurrency)).thenReturn(response);

        CurrencyResponse result = service.save(request);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("MXN", result.code());
        assertEquals("Peso MX", result.name());
        assertEquals("$", result.symbol());

        verify(mapper).toEntity(request);
        verify(repository).existsByCodeIgnoreCase("MXN");
        verify(repository).save(currency);
        verify(mapper).toResponse(savedCurrency);
    }

    @Test
    void shouldThrowResourceAlreadyExistsExceptionWhenCurrencyCodeAlreadyExists(){
        CurrencyRequest request = new CurrencyRequest("Peso MX","MXN","$");

        Currency currency = new Currency();
        currency.setName("Peso MX");
        currency.setCode("MXN");
        currency.setSymbol("$");

        when(mapper.toEntity(request)).thenReturn(currency);
        when(repository.existsByCodeIgnoreCase("MXN")).thenReturn(true);

        ResourceAlreadyExistsException ex = assertThrows(
            ResourceAlreadyExistsException.class,
            () -> service.save(request)
        );

        assertEquals("El registro ya existe.", ex.getMessage());
        verify(repository).existsByCodeIgnoreCase("MXN");
        verify(repository, never()).save(any());
        verify(mapper, never()).toResponse(any());
    }

    @Test
    void shouldCurrencyUpdateSuccessfully(){
        Long id = 1L;

        Currency currency = new Currency();
        currency.setName("Peso MX");
        currency.setCode("MXN");
        currency.setSymbol("$");

        CurrencyRequest request = new CurrencyRequest("Peso","MXN","$");

        CurrencyResponse response = new CurrencyResponse(id, "Peso", "MXN", "$");

        when(repository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.of(currency));
        when(repository.existsByCodeIgnoreCaseAndIdNot("MXN", id)).thenReturn(false);
        when(mapper.toResponse(currency)).thenReturn(response);

        CurrencyResponse result = service.update(id, request);

        assertEquals("Peso", result.name());
        assertEquals("MXN", result.code());

        verify(repository).findByIdAndIsActiveTrue(id);
        verify(repository).existsByCodeIgnoreCaseAndIdNot("MXN",id);
        verify(mapper).updateEntity(request, currency);
        verify(mapper).toResponse(currency);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenCurrencyUpdate(){
        Long id = 1L;

        CurrencyRequest request = new CurrencyRequest("Peso","MXN","$");

        when(repository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class, () -> {
                    service.update(id, request);
                }
        );

        assertEquals("Registro no encontrado.", ex.getMessage());
        verify(repository).findByIdAndIsActiveTrue(id);
    }

    @Test
    void shouldThrowResourceAlreadyExistsExceptionWhenCurrencyUpdate(){
        Long id = 1L;

        Currency currency = new Currency();
        currency.setName("Peso MX");
        currency.setCode("MXN");
        currency.setSymbol("$");

        CurrencyRequest request = new CurrencyRequest("Peso","MXN","$");

        when(repository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.of(currency));
        when(repository.existsByCodeIgnoreCaseAndIdNot("MXN", id)).thenReturn(true);

        ResourceAlreadyExistsException ex = assertThrows(
                ResourceAlreadyExistsException.class, () -> {
                    service.update(id, request);
                }
        );

        assertEquals("El registro ya existe.", ex.getMessage());
        verify(repository).findByIdAndIsActiveTrue(id);
        verify(repository).existsByCodeIgnoreCaseAndIdNot("MXN",id);
    }

    @Test
    void shouldUpdateCurrencyEntityFromRequest() {
        CurrencyMapper mapper = new CurrencyMapper();

        Currency currency = new Currency();
        currency.setName("Peso MX");
        currency.setCode("MXN");
        currency.setSymbol("$");

        CurrencyRequest request = new CurrencyRequest("Peso mexicano", "mxn", "Mex$");

        mapper.updateEntity(request, currency);

        assertEquals("Peso mexicano", currency.getName());
        assertEquals("MXN", currency.getCode());
        assertEquals("Mex$", currency.getSymbol());
    }


    @Test
    void shouldDeactivateCurrencySuccessfully(){
        Long id = 1L;

        Currency currency = new Currency();
        currency.setId(id);
        currency.setActive(true);

        when(repository.findByIdAndIsActiveTrue(id)).thenReturn(Optional.of(currency));

        service.deleteById(id);

        assertFalse(currency.isActive());
        verify(repository).findByIdAndIsActiveTrue(id);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenCurrencyNotFoundOnDeactivate(){
        Long id = 1L;

        when(repository.findByIdAndIsActiveTrue(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class, () -> {
                    service.deleteById(id);
                }
        );
        assertEquals("Registro no encontrado.", ex.getMessage());
        verify(repository).findByIdAndIsActiveTrue(id);
    }

}