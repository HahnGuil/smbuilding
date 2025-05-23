package com.manager.smbuilding.presentation.controller;


import com.manager.smbuilding.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/assign-role/{userId}/{roleName}")
    public ResponseEntity<?> assignRole(@PathVariable UUID userId, @PathVariable String roleName) {
        try {
            userService.assignRoleToUser(userId, roleName);
            return ResponseEntity.ok("Role atribuída com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
}
