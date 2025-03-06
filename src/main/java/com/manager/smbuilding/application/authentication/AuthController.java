package com.manager.smbuilding.application.authentication;

import com.manager.smbuilding.application.dto.request.LoginRequestDTO;
import com.manager.smbuilding.application.dto.request.RegisterRequestDTO;
import com.manager.smbuilding.application.dto.response.LoginResponseDTO;
import com.manager.smbuilding.application.excption.InvalidCredentialsException;
import com.manager.smbuilding.application.excption.UserAlreadyExistsException;
import com.manager.smbuilding.application.excption.UserNotFoundException;
import com.manager.smbuilding.domain.model.Role;
import com.manager.smbuilding.domain.model.User;
import com.manager.smbuilding.domain.repository.RoleRepository;
import com.manager.smbuilding.domain.repository.UserRepository;
import com.manager.smbuilding.infrastructure.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO body) {
        User user = this.userRepository.findByEmail(body.email())
                .orElseThrow(() -> new UserNotFoundException("User not found. Check email and password or register a new user."));

        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect email or password");
        }

        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginResponseDTO(user.getName(), token));
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequestDTO body) {
        //Checks if the email or CPF already exists
        if (userRepository.existsByEmail(body.email())) {
            throw new UserAlreadyExistsException("Email already registered. Please log in or recover your password.");
        }

        if (userRepository.existsByCpf(body.cpf())) {
            throw new UserAlreadyExistsException("CPF already registered. Please log in or recover your password.");
        }

        // User creation
        User newUser = new User();
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setCpf(body.cpf());
        newUser.setTower(body.tower());
        newUser.setApartment(body.apartment());
        newUser.setPassword(passwordEncoder.encode(body.password()));

        // Assigns the role "RESIDENT" by default
        Role defaultRole = roleRepository.findByRoleName("RESIDENT")
                .orElseThrow(() -> new RuntimeException("Role 'RESIDENT' não encontrada"));
        newUser.addRole(defaultRole);

        // Salva o usuário
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Usuário criado com sucesso!"));
    }



}
