package com.manager.smbuilding.infrastructure.security;

import com.manager.smbuilding.domain.model.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Recupera o usuário autenticado do SecurityContextHolder
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Obtém o email do usuário autenticado a partir do SecurityContext
            User user = (User) authentication.getPrincipal();
            return Optional.of(user.getName());  // Aqui você pode retornar o email ou outro dado
        }

        return Optional.empty();  // Retorna vazio se não houver autenticação
    }
}
