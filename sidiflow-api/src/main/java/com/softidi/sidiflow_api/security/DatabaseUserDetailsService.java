package com.softidi.sidiflow_api.security;

import com.softidi.sidiflow_api.role.Role;
import com.softidi.sidiflow_api.user.User;
import com.softidi.sidiflow_api.user.UserRepository;
import com.softidi.sidiflow_api.user.UserStatus;
import com.softidi.sidiflow_api.userrole.UserRole;
import com.softidi.sidiflow_api.userrole.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private static final Short STATUS_ACTIVE = (short) 1;

    @Value("${error.username.not-found}")
    private String usernameNotFoundMessage;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameIgnoreCaseAndStatus(username, UserStatus.ACTIVE).orElseThrow(
                () -> new UsernameNotFoundException(usernameNotFoundMessage)
        );
        List<Role> roles = userRoleRepository
                .findAllByUserIdAndRoleStatusOrderByRoleNameAsc(user.getId(), STATUS_ACTIVE)
                .stream()
                .map(UserRole::getRole).toList();
        List<GrantedAuthority> authorities = roles.stream()
                .map(Role::getName)
                .map(role -> role.toUpperCase(Locale.ROOT))
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                authorities
        );
    }
}
