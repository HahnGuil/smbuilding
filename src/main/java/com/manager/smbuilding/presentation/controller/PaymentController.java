package com.manager.smbuilding.presentation.controller;

import com.manager.smbuilding.application.dto.request.PaymentRequestDTO;
import com.manager.smbuilding.application.exception.EmptyFileException;
import com.manager.smbuilding.application.service.PaymentService;
import com.manager.smbuilding.domain.model.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-payment")
    public ResponseEntity<Payment> createPayment(@RequestParam ("documentType") String documentType,
                                                 @RequestParam ("supplier") Long supplier,
                                                 @RequestParam ("costCenter") Long costCenter,
                                                 @RequestParam ("datePayment") LocalDate datePayment,
                                                 @RequestParam ("paymentAmount") Double paymentAmout,
                                                 @RequestParam (value = "receiptDocument", required = true) MultipartFile receiptDocumet) throws IOException {

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(documentType, supplier, costCenter, datePayment, paymentAmout, receiptDocumet);

        if(receiptDocumet.isEmpty()){
            throw new EmptyFileException("File cannot be empty");
        }

        Payment payment = paymentService.createPayment(paymentRequestDTO);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/update-payment/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable UUID id,
            @RequestParam(value = "documentType", required = false) String documentType,
            @RequestParam(value = "supplier", required = false) Long supplier,
            @RequestParam(value = "costCenter", required = false) Long costCenter,
            @RequestParam(value = "datePayment", required = false) LocalDate datePayment,
            @RequestParam(value = "paymentAmount", required = false) Double paymentAmount,
            @RequestParam(value = "receiptDocument", required = false) MultipartFile receiptDocument) throws IOException {

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(documentType, supplier, costCenter, datePayment, paymentAmount, receiptDocument);
        Payment updatedPayment = paymentService.updatePayment(id, paymentRequestDTO);
        return ResponseEntity.ok(updatedPayment);
    }
}

