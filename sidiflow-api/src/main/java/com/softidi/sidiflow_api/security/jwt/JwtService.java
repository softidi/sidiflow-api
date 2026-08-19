package com.softidi.sidiflow_api.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;

    @Value("${app.security.jwt.issuer}")
    private String jwtIssuer;

    @Value("${app.security.jwt.expiration-minutes}")
    private Long expirationMinutes;

    @Value("${app.security.jwt.audience}")
    private String jwtAudience;

    public String generateToken(Authentication authentication, Long userId) {
        Instant now = Instant.now();
        List<String> roles =
                authentication
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .filter(authority -> authority.startsWith("ROLE_"))
                        .map(authority -> authority.substring("ROLE_".length()))
                        .toList();
        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .issuer(jwtIssuer)
                        .subject(userId.toString())
                        .audience(List.of(jwtAudience))
                        .issuedAt(now)
                        .expiresAt(now.plus(expirationMinutes, ChronoUnit.MINUTES))
                        .claim("username", authentication.getName())
                        .claim("roles", roles)
                        .build();
        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    public Long getExpirationSeconds() {
        return expirationMinutes * 60;
    }
}
