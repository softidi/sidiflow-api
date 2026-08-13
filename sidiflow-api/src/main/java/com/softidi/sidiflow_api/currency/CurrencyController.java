package com.softidi.sidiflow_api.currency;

import com.softidi.sidiflow_api.currency.dto.CurrencyRequest;
import com.softidi.sidiflow_api.currency.dto.CurrencyResponse;
import com.softidi.sidiflow_api.currency.service.CurrencyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService service;

    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CurrencyResponse> findByCode(@PathVariable String code){
        return ResponseEntity.ok(service.findByCode(code));
    }

    @PostMapping
    public ResponseEntity<CurrencyResponse> save(@Valid @RequestBody CurrencyRequest request){
        CurrencyResponse response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponse> update(@PathVariable @Positive(message = "{validation.positive}") Long id,
                                                   @Valid @RequestBody CurrencyRequest request){
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive(message = "{validation.positive}") Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
