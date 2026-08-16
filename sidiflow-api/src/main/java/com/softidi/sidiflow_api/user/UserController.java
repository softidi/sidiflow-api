package com.softidi.sidiflow_api.user;

import com.softidi.sidiflow_api.user.dto.UserCreateRequest;
import com.softidi.sidiflow_api.user.dto.UserResponse;
import com.softidi.sidiflow_api.user.dto.UserUpdateRequest;
import com.softidi.sidiflow_api.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable @Positive(message = "{validation.positive}")
                                                     @Valid Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@Valid @RequestBody UserCreateRequest request){
        UserResponse response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable @Positive(message = "{validation.positive}") @Valid Long id,
                                               @Valid @RequestBody UserUpdateRequest request){
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive(message = "{validation.positive}") Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
