package com.manager.smbuilding.presentation.controller;


import com.manager.smbuilding.application.service.UserService;
import com.manager.smbuilding.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
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
