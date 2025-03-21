package com.manager.smbuilding.domain.model;

import com.manager.smbuilding.domain.model.enums.DocumentType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "PAYMENT")
public class Payment{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE", nullable = false)
    private DocumentType documentType;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "costCenter_id", nullable = false)
    private CostCenter costCenter;

    @Column(name = "DATE_PAYMENT", nullable = false)
    private LocalDate datePayment;

    @Column(name = "PAYMENTE_AMOUNT", nullable = false)
    private Double paymentAmount;

    @Column(name = "RECEIPT_DOCUMENT", nullable = false)
    private String receiptDocument;

    public Payment() {}

    public Payment(UUID id, DocumentType documentType, Supplier supplier, CostCenter costCenter, LocalDate datePayment, Double paymentAmount, String receiptDocument) {
        this.id = id;
        this.documentType = documentType;
        this.supplier = supplier;
        this.costCenter = costCenter;
        this.datePayment = datePayment;
        this.paymentAmount = paymentAmount;
        this.receiptDocument = receiptDocument;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public LocalDate getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(LocalDate datePayment) {
        this.datePayment = datePayment;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getReceiptDocument() {
        return receiptDocument;
    }

    public void setReceiptDocument(String receiptDocument) {
        this.receiptDocument = receiptDocument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(getId(), payment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", documentType=" + documentType +
                ", supplier=" + supplier +
                ", costCenter=" + costCenter +
                ", datePayment=" + datePayment +
                ", paymentAmount=" + paymentAmount +
                ", receiptDocument='" + receiptDocument + '\'' +
                '}';
    }
}
