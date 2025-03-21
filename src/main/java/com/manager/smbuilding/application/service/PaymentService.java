package com.manager.smbuilding.application.service;

import com.manager.smbuilding.application.dto.request.PaymentRequestDTO;
import com.manager.smbuilding.domain.model.Payment;
import com.manager.smbuilding.domain.model.enums.DocumentType;
import com.manager.smbuilding.domain.repository.PaymentRepository;
import com.manager.smbuilding.infrastructure.service.GoogleDriveService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
        Payment newPayment = toEntity(paymentRequestDTO, uploadReceiptDocument);

        paymentRepository.save(newPayment);
        return newPayment;
    }


    protected Payment toEntity(PaymentRequestDTO paymentRequestDTO, String linkReceiptDocument) {
        Payment payment = new Payment();

        payment.setDocumentType(DocumentType.valueOf(paymentRequestDTO.documentType().toUpperCase()));
        payment.setDatePayment(paymentRequestDTO.datePayment());
        payment.setPaymentAmount(paymentRequestDTO.paymentAmount());
        payment.setCostCenter(costCenterService.findById(paymentRequestDTO.costCenter()));
        payment.setSupplier(supplierService.findById(paymentRequestDTO.supplier()));
        payment.setReceiptDocument(linkReceiptDocument);

        return payment;

    }

    public Payment updatePayment(UUID id, PaymentRequestDTO paymentRequestDTO) throws IOException {

        Payment updatePayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        String newLinkUploadReceiptDocument = handleReceiptDocumentUpdate(updatePayment, paymentRequestDTO.receiptDocument());

        updatePayment.setDocumentType(DocumentType.valueOf(paymentRequestDTO.documentType().toUpperCase()));
        updatePayment.setDatePayment(paymentRequestDTO.datePayment());
        updatePayment.setPaymentAmount(paymentRequestDTO.paymentAmount());
        updatePayment.setCostCenter(costCenterService.findById(paymentRequestDTO.costCenter()));
        updatePayment.setSupplier(supplierService.findById(paymentRequestDTO.supplier()));
        updatePayment.setReceiptDocument(newLinkUploadReceiptDocument);

//        setAuditorToPayment(updatePayment, false);





        paymentRepository.save(updatePayment);

        return updatePayment;
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
            throw new IOException("Failed to delete the file with the given link: " + receiptDocument);
        }
    }

    protected void findLinkReceiptDocument(String receiptDocument) throws IOException {
        if(googleDriveService.deleteFileByLink(receiptDocument)) {
            return;
        };

    }


    protected String uploadReceiptDocument(MultipartFile receiptDocument) throws IOException {
        File tempFile = File.createTempFile(Objects.requireNonNull(receiptDocument.getOriginalFilename()), null);
        receiptDocument.transferTo(tempFile);

        String mimeType = receiptDocument.getContentType();
        String folderId = googleDriveService.getIdDriveFolder();

        return googleDriveService.uploadFile(folderId, tempFile, mimeType);
    }
}
