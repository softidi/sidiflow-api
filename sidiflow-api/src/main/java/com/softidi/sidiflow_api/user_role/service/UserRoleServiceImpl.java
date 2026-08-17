package com.softidi.sidiflow_api.user_role.service;

import com.softidi.sidiflow_api.exception.ResourceAlreadyExistsException;
import com.softidi.sidiflow_api.exception.ResourceNotFoundException;
import com.softidi.sidiflow_api.role.Role;
import com.softidi.sidiflow_api.role.RoleMapper;
import com.softidi.sidiflow_api.role.RoleRepository;
import com.softidi.sidiflow_api.role.RoleResponse;
import com.softidi.sidiflow_api.user.User;
import com.softidi.sidiflow_api.user.UserRepository;
import com.softidi.sidiflow_api.user.UserStatus;
import com.softidi.sidiflow_api.user_role.UserRole;
import com.softidi.sidiflow_api.user_role.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements  UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Value("${error.resource.not-found}")
    private String resourceNotFoundMessage;
    @Value("${error.resource.already-exists}")
    private String resourceAlreadyExistsMessage;

    private static final Short STATUS_ACTIVE = 1;

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> findAllByUserId(Long userId){
        if(!userRepository.existsByIdAndStatus(userId, UserStatus.ACTIVE)){
            throw new ResourceNotFoundException(resourceNotFoundMessage);
        }
        return userRoleRepository.findAllByUserIdAndRoleStatusOrderByRoleNameAsc(userId, STATUS_ACTIVE).stream()
                .map(role -> roleMapper.toResponse(role.getRole())).toList();
    }

    @Override
    @Transactional
    public void assignRole(Long userId, Long roleId){
        User user = userRepository.findByIdAndStatus(userId, UserStatus.ACTIVE).orElseThrow(
                () -> new ResourceNotFoundException(resourceNotFoundMessage)
        );
        Role role = roleRepository.findByIdAndStatus(roleId, STATUS_ACTIVE).orElseThrow(
                () -> new ResourceNotFoundException(resourceNotFoundMessage)
        );
        if(userRoleRepository.existsByUserIdAndRoleId(userId, roleId)){
            throw new ResourceAlreadyExistsException(resourceAlreadyExistsMessage);
        }

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleRepository.save(userRole);
    }

    @Override
    @Transactional
    public void removeRole(Long userId, Long roleId){
        userRepository.findByIdAndStatus(userId, UserStatus.ACTIVE).orElseThrow(
                () -> new ResourceNotFoundException(resourceNotFoundMessage)
        );
        UserRole userRole = userRoleRepository.findByUserIdAndRoleId(userId, roleId).orElseThrow(
                () -> new ResourceNotFoundException(resourceNotFoundMessage)
        );
        userRoleRepository.delete(userRole);
    }
}
