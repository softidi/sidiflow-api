package com.softidi.sidiflow_api.auth.service;

import com.softidi.sidiflow_api.auth.dto.LoginRequest;
import com.softidi.sidiflow_api.auth.dto.LoginResponse;
import com.softidi.sidiflow_api.exception.InvalidCredentialsException;
import com.softidi.sidiflow_api.security.jwt.JwtService;
import com.softidi.sidiflow_api.user.User;
import com.softidi.sidiflow_api.user.UserRepository;
import com.softidi.sidiflow_api.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication;
        String username = request.username().trim();

        try {
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(username, request.password());
            authentication = authenticationManager.authenticate(authenticationRequest);
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Usuario o contraseña incorrectos.");
        }

        User user = userRepository.findByUsernameIgnoreCaseAndStatus(username, UserStatus.ACTIVE)
                .orElseThrow(
                        () -> new InvalidCredentialsException("Usuario o contraseña incorrectos.")
                );

        String accessToken = jwtService.generateToken(authentication, user.getId());
        return new LoginResponse(
                accessToken,
                "Bearer",
                jwtService.getExpirationSeconds()
        );
    }

}
