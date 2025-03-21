package com.manager.smbuilding.application.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record PaymentResponseDTO(String documentType, String supplier, String costCenter, LocalDate datePayment, Double paymentAmount, String receiptDocument) {
}
