package com.manager.smbuilding.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record PaymentRequestDTO(
        @NotBlank(message = "Document type is required")
        String documentType,
        @NotNull(message = "Supplier is required")
        Long supplier,
        @NotNull(message = "Cost Center is required")
        Long costCenter,
        @NotNull(message = "Date of payment is required")
        LocalDate datePayment,
        @NotNull(message = "Amount of payment is required")
        Double paymentAmount,
        @NotNull(message = "Receipt of payment is required")
        MultipartFile receiptDocument) {
}

