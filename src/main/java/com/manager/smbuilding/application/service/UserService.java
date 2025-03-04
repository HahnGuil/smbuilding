package com.manager.smbuilding.application.service;

import com.manager.smbuilding.domain.model.Role;
import com.manager.smbuilding.domain.model.User;
import com.manager.smbuilding.domain.repository.RoleRepository;
import com.manager.smbuilding.domain.repository.UserRepository;
import jakarta.persistence.Table;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
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
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Evita remover as roles do próprio ADMIN, caso o usuário com a role ADMIN esteja fazendo a requisição
        if (roleName.equals("ADMIN")) {
            throw new RuntimeException("Não é permitido modificar a role ADMIN diretamente.");
        }



        // Obtém a role nova que será atribuída ao usuário
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        // Limpa as roles do usuário alvo
        user.getRoles().clear();

        // Adiciona a nova role
        user.getRoles().add(role);

        // Salva as alterações
        userRepository.save(user);
    }

}
