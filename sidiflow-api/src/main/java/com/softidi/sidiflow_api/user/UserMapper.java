package com.softidi.sidiflow_api.user;

import com.softidi.sidiflow_api.currency.CurrencyMapper;
import com.softidi.sidiflow_api.user.dto.UserCreateRequest;
import com.softidi.sidiflow_api.user.dto.UserResponse;
import com.softidi.sidiflow_api.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final CurrencyMapper currencyMapper;

    public UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                currencyMapper.toResponse(user.getDefaultCurrency()),
                (short) user.getStatus().ordinal()
        );
    }

    public User toEntity(UserCreateRequest request){
        User user = new User();
        mapRequestToEntity(request, user);
        return user;
    }

    public void updateEntity(UserUpdateRequest request, User user){
        user.setUsername(normalizeUsername(request.username()));
        user.setFirstName(normalizeField(request.firstName()));
        user.setLastName(normalizeField(request.lastName()));
        user.setEmail(normalizeEmail(request.email()));
    }

    private void mapRequestToEntity(UserCreateRequest request, User user){
        user.setUsername(normalizeUsername(request.username()));
        user.setFirstName(normalizeField(request.firstName()));
        user.setLastName(normalizeField(request.lastName()));
        user.setEmail(normalizeEmail(request.email()));
    }

    private String normalizeUsername(String username){
        return username.trim();
    }

    private String normalizeEmail(String email){
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private String normalizeField(String value){
        return value == null ? null : value.trim();
    }

}
