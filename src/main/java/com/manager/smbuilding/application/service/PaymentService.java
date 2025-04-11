package com.manager.smbuilding.application.service;

import com.manager.smbuilding.application.dto.request.PaymentRequestDTO;
import com.manager.smbuilding.application.dto.response.PaymentResponseDTO;
import com.manager.smbuilding.application.exception.FileDeletionException;
import com.manager.smbuilding.application.exception.ResourceNotFoundException;
import com.manager.smbuilding.application.utils.ValidationUtils;
import com.manager.smbuilding.domain.model.Payment;
import com.manager.smbuilding.domain.model.enums.DocumentType;
import com.manager.smbuilding.domain.repository.PaymentRepository;
import com.manager.smbuilding.infrastructure.service.GoogleDriveService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SupplierService supplierService;
    private final CenterCostService costCenterService;
    private final GoogleDriveService googleDriveService;


    public PaymentService(PaymentRepository paymentRepository, SupplierService supplierService, CenterCostService costCenterService, GoogleDriveService googleDriveService) {
        this.paymentRepository = paymentRepository;
        this.supplierService = supplierService;
        this.costCenterService = costCenterService;
        this.googleDriveService = googleDriveService;
    }

    public Payment createPayment(PaymentRequestDTO paymentRequestDTO) throws IOException {
        String uploadReceiptDocument = uploadReceiptDocument(paymentRequestDTO.receiptDocument());
        Payment payment = new Payment();
        Payment newPayment = toEntity(paymentRequestDTO, payment,uploadReceiptDocument);

        paymentRepository.save(toEntity(paymentRequestDTO, payment,uploadReceiptDocument));
        return newPayment;
    }

    public Payment updatePayment(UUID id, PaymentRequestDTO paymentRequestDTO) throws IOException {

        Payment updatePayment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        String newLinkUploadReceiptDocument = handleReceiptDocumentUpdate(updatePayment, paymentRequestDTO.receiptDocument());
        toEntity(paymentRequestDTO, updatePayment, newLinkUploadReceiptDocument);

        paymentRepository.save(updatePayment);
        return updatePayment;
    }

    protected Payment toEntity(PaymentRequestDTO paymentRequestDTO, Payment payment, String newLinkUploadReceiptDocument) {
        payment.setDocumentType(DocumentType.valueOf(paymentRequestDTO.documentType().toUpperCase()));
        payment.setDatePayment(paymentRequestDTO.datePayment());
        payment.setPaymentAmount(paymentRequestDTO.paymentAmount());
        payment.setCostCenter(costCenterService.findById(paymentRequestDTO.costCenter()));
        payment.setSupplier(supplierService.findById(paymentRequestDTO.supplier()));
        payment.setReceiptDocument(newLinkUploadReceiptDocument);

        return payment;
    }

    private PaymentResponseDTO toDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getDocumentType().name(),
                payment.getSupplier().getName(),
                payment.getCostCenter().getCostCenter(),
                payment.getDatePayment(),
                payment.getPaymentAmount(),
                payment.getReceiptDocument()
        );
    }

    protected String handleReceiptDocumentUpdate(Payment updatePayment, MultipartFile receiptDocument) throws IOException {
        String newLinkUploadReceiptDocument;

        if (receiptDocument != null && !receiptDocument.isEmpty()) {
            deleteReceiptDocument(updatePayment.getReceiptDocument());
            newLinkUploadReceiptDocument = uploadReceiptDocument(receiptDocument);
        } else {
            newLinkUploadReceiptDocument = updatePayment.getReceiptDocument();
        }

        return newLinkUploadReceiptDocument;
    }

    protected void deleteReceiptDocument(String receiptDocument) throws IOException {
        boolean isDeleted = googleDriveService.deleteFileByLink(receiptDocument);
        if (!isDeleted) {
            throw new FileDeletionException("Failed to delete the file with the given link: " + receiptDocument);
        }
    }

    protected String uploadReceiptDocument(MultipartFile receiptDocument) throws IOException {
        File tempFile = File.createTempFile(Objects.requireNonNull(receiptDocument.getOriginalFilename()), null);
        receiptDocument.transferTo(tempFile);

        String mimeType = receiptDocument.getContentType();
        String folderId = googleDriveService.getIdDriveFolder();

        return googleDriveService.uploadFile(folderId, tempFile, mimeType);
    }

    public List<PaymentResponseDTO> listByCostCenter(String costCenter){
        List<Payment> payments = ValidationUtils.validateNonEmptyList(
                paymentRepository.findByCostCenterName(costCenter),
                "No payments found for cost center: " + costCenter
        );
        return payments.stream().map(this::toDTO).toList();
    }

    public List<PaymentResponseDTO> listBySupplier(String supplier){
        List<Payment> payments = ValidationUtils.validateNonEmptyList(
                paymentRepository.findBySupplierName(supplier), "No payments found for supplier: " + supplier
        );
        return payments.stream().map(this::toDTO).toList();
    }

    public List<PaymentResponseDTO> listByDateRange(LocalDate startDate, LocalDate endDate){
        List<Payment> payments = ValidationUtils.validateNonEmptyList(paymentRepository.findByDatePaymentBetween(startDate, endDate), "No payments found for start date: " + startDate + " and end date: " + endDate);
        return payments.stream().map(this::toDTO).toList();
    }

    public List<PaymentResponseDTO> listByPaymentAmountRange(Double min, Double max){
        List<Payment> payments = ValidationUtils.validateNonEmptyList(paymentRepository.findByPaymentAmountBetween(min, max), "No payments found for min/max: " + min + " and max: " + max);
        return payments.stream().map(this::toDTO).toList();
    }

}
