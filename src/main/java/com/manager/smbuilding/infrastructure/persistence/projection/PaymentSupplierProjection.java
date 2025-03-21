package com.manager.smbuilding.infrastructure.persistence.projection;

import java.time.LocalDate;

public interface PaymentSupplierProjection {

    Long getUId();
    String getDocumentType();
    Long getSupplierId();
    Long getCostCenter();
    LocalDate getDatePayment();
    Double getPaymentAmount();
    String getReceiptDocument();

}
