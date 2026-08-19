package com.softidi.sidiflow_api.user;

import com.softidi.sidiflow_api.role.dto.RoleResponse;
import com.softidi.sidiflow_api.user.dto.UserCreateRequest;
import com.softidi.sidiflow_api.user.dto.UserResponse;
import com.softidi.sidiflow_api.user.dto.UserUpdateRequest;
import com.softidi.sidiflow_api.user.service.UserService;
import com.softidi.sidiflow_api.userrole.service.UserRoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserRoleService userRoleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> findById(@PathVariable @Positive(message = "{validation.positive}")
                                                     @Valid Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> save(@Valid @RequestBody UserCreateRequest request){
        UserResponse response = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> update(@PathVariable @Positive(message = "{validation.positive}") @Valid Long id,
                                               @Valid @RequestBody UserUpdateRequest request){
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable @Positive(message = "{validation.positive}") Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoleResponse>> findRolesByUserId(@PathVariable
                                                                    @Positive(message = "{validation.positive}")
                                                                    @Valid Long userId){
        return ResponseEntity.ok(userRoleService.findAllByUserId(userId));
    }

    @PostMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> assignRole(@PathVariable @Positive(message = "{validation.positive}") @Valid Long userId,
                                           @PathVariable @Positive(message = "{validation.positive}") @Valid Long roleId) {
        userRoleService.assignRole(userId, roleId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeRole(@PathVariable @Positive(message = "{validation.positive}") @Valid Long userId,
                                           @PathVariable @Positive(message = "{validation.positive}") @Valid Long roleId) {
        userRoleService.removeRole(userId, roleId);
        return ResponseEntity.noContent().build();
    }
}
