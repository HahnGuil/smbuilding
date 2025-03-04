package com.manager.smbuilding.application.authentication;

import com.manager.smbuilding.application.dto.request.LoginRequestDTO;
import com.manager.smbuilding.application.dto.request.RegisterRequestDTO;
import com.manager.smbuilding.application.dto.response.LoginResponseDTO;
import com.manager.smbuilding.application.excption.UserAlreadyExistsException;
import com.manager.smbuilding.domain.model.Role;
import com.manager.smbuilding.domain.model.User;
import com.manager.smbuilding.domain.repository.RoleRepository;
import com.manager.smbuilding.domain.repository.UserRepository;
import com.manager.smbuilding.infrastructure.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        User user = this.userRepository.findByEmail(body.email()).orElseThrow(()-> new RuntimeException("Usuáro não encontrado"));

        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponseDTO(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        // Verifica se o email ou CPF já existe
        Optional<User> userByEmail = this.userRepository.findByEmail(body.email());
        Optional<User> userByCpf = this.userRepository.findByCpf(body.cpf());

        if (userByEmail.isPresent()) {
            throw new UserAlreadyExistsException("Email já cadastrado. Por favor, faça login ou recupere sua senha.");
        }

        if (userByCpf.isPresent()) {
            throw new UserAlreadyExistsException("CPF já cadastrado. Por favor, faça login ou recupere sua senha.");
        }

        // Cria o novo usuário
        User newUser = new User();
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setCpf(body.cpf());
        newUser.setTower(body.tower());
        newUser.setApartment(body.apartment());
        newUser.setPassword(passwordEncoder.encode(body.password()));

        // Atribui a role "RESIDENT" por padrão
        Role defaultRole = roleRepository.findByRoleName("RESIDENT")
                .orElseThrow(() -> new RuntimeException("Role 'RESIDENT' não encontrada"));
        newUser.addRole(defaultRole);

        // Salva o usuário
        this.userRepository.save(newUser);
        return ResponseEntity.ok("Usuário criado com sucesso!");
    }


}
