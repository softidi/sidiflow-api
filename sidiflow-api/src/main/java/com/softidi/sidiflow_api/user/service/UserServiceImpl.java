package com.softidi.sidiflow_api.user.service;

import com.softidi.sidiflow_api.currency.Currency;
import com.softidi.sidiflow_api.currency.CurrencyRepository;
import com.softidi.sidiflow_api.exception.ResourceAlreadyExistsException;
import com.softidi.sidiflow_api.exception.ResourceNotFoundException;
import com.softidi.sidiflow_api.user.User;
import com.softidi.sidiflow_api.user.UserMapper;
import com.softidi.sidiflow_api.user.UserRepository;
import com.softidi.sidiflow_api.user.UserStatus;
import com.softidi.sidiflow_api.user.dto.UserCreateRequest;
import com.softidi.sidiflow_api.user.dto.UserResponse;
import com.softidi.sidiflow_api.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository repository;
    private final CurrencyRepository currencyRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${error.resource.not-found}")
    private String resourceNotFoundMessage;
    @Value("${error.resource.already-exists}")
    private String resourceAlreadyExistsMessage;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return repository.findByStatus(UserStatus.ACTIVE).stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = repository.findByIdAndStatus(id, UserStatus.ACTIVE).orElseThrow(
                () -> new ResourceNotFoundException(resourceNotFoundMessage)
        );
        return mapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse save(UserCreateRequest request) {
        validateSave(request);
        Currency currency = currencyRepository.findByIdAndIsActiveTrue(request.defaultCurrencyId())
                .orElseThrow(() -> new ResourceNotFoundException(resourceNotFoundMessage));
        User user = mapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setDefaultCurrency(currency);
        User userSaved = repository.save(user);
        return mapper.toResponse(userSaved);
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = repository.findByIdAndStatus(id, UserStatus.ACTIVE).orElseThrow(
                () -> new ResourceNotFoundException(resourceNotFoundMessage)
        );
        validateUpdate(id, request);
        Currency currency = currencyRepository.findByIdAndIsActiveTrue(request.defaultCurrencyId())
                .orElseThrow(() -> new ResourceNotFoundException(resourceNotFoundMessage));
        mapper.updateEntity(request, user);
        user.setDefaultCurrency(currency);
        return mapper.toResponse(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        User user = repository.findByIdAndStatus(id, UserStatus.ACTIVE).orElseThrow(
                () -> new ResourceNotFoundException(resourceNotFoundMessage)
        );
        user.setStatus(UserStatus.INACTIVE);
    }

    private void validateSave(UserCreateRequest request){
        if(repository.existsByUsernameIgnoreCase(request.username().trim())){
            throw new ResourceAlreadyExistsException(resourceAlreadyExistsMessage);
        }
        if(repository.existsByEmailIgnoreCase(request.email().trim())){
            throw new ResourceAlreadyExistsException(resourceAlreadyExistsMessage);
        }
    }

    private void validateUpdate(Long id, UserUpdateRequest request){
        if(repository.existsByUsernameIgnoreCaseAndIdNot(request.username().trim(), id)){
            throw new ResourceAlreadyExistsException(resourceAlreadyExistsMessage);
        }
        if(repository.existsByEmailIgnoreCaseAndIdNot(request.email().trim(), id)){
            throw new ResourceAlreadyExistsException(resourceAlreadyExistsMessage);
        }
    }
}
