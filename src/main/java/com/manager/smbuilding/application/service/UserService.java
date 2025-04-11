package com.manager.smbuilding.application.service;

import com.manager.smbuilding.application.exception.AccessDinedException;
import com.manager.smbuilding.application.exception.ResourceNotFoundException;
import com.manager.smbuilding.domain.model.Role;
import com.manager.smbuilding.domain.model.User;
import com.manager.smbuilding.domain.repository.RoleRepository;
import com.manager.smbuilding.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void assignRoleToUser(UUID userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (roleName.equals("ADMIN")) {
            throw new AccessDinedException("You do not have access to modify the Role");
        }

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.getRoles().clear();
        user.getRoles().add(role);

        userRepository.save(user);
    }

}
