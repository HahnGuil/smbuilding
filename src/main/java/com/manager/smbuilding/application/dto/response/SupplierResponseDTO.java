package com.manager.smbuilding.application.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SupplierResponseDTO(
        @NotBlank(message = "O nome não pode estar em branco.") String name,
        @NotBlank(message = "O CNPJ não pode estar em branco.")
        @Size(min = 14, max = 14, message = "O CNPJ deve ter exatamente 14 caracteres.") String cnpj,
        String phone,
        String email,
        @NotBlank(message = "O estado não pode estar em branco.") String state, // Mapeia para uf
        @NotBlank(message = "A cidade não pode estar em branco.") String city,
        @NotBlank(message = "A rua não pode estar em branco.") String street
) {}
