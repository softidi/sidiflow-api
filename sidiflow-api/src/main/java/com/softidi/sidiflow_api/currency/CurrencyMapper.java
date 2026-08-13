package com.softidi.sidiflow_api.currency;

import com.softidi.sidiflow_api.currency.dto.CurrencyRequest;
import com.softidi.sidiflow_api.currency.dto.CurrencyResponse;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CurrencyMapper {

    public CurrencyResponse toResponse(Currency currency){
        return new CurrencyResponse(
                currency.getId(),
                currency.getName(),
                currency.getCode(),
                currency.getSymbol()
        );
    }

    public Currency toEntity(CurrencyRequest request){
        Currency currency = new Currency();
        mapRequestToEntity(request, currency);
        return currency;
    }

    public void updateEntity(CurrencyRequest request, Currency currency){
        mapRequestToEntity(request, currency);
    }

    private void mapRequestToEntity(CurrencyRequest request, Currency currency){
        currency.setName(request.name().trim());
        currency.setCode(request.code().trim().toUpperCase(Locale.ROOT));
        currency.setSymbol(request.symbol().trim());
    }
}
