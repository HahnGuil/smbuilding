package com.manager.smbuilding.application.dto.request;

import jakarta.persistence.Column;

public record SupplierRequestDTO(String name, String cnpj, String phone, String email, String uf, String city, String street) {
}
