package com.manager.smbuilding.infrastructure.configuration;

import com.manager.smbuilding.domain.model.Role;
import com.manager.smbuilding.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica se as roles já existem, caso contrário, cria e salva no banco
        if (!roleRepository.findByRoleName("ADMIN").isPresent()) {
            roleRepository.save(new Role("ADMIN"));
        }
        if (!roleRepository.findByRoleName("MANAGER").isPresent()) {
            roleRepository.save(new Role("MANAGER"));
        }
        if (!roleRepository.findByRoleName("BOARD").isPresent()) {
            roleRepository.save(new Role("BOARD"));
        }
        if (!roleRepository.findByRoleName("RESIDENT").isPresent()) {
            roleRepository.save(new Role("RESIDENT"));
        }
    }
}
