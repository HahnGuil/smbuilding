package com.manager.smbuilding.domain.repository;

import com.manager.smbuilding.domain.model.Payment;
import com.manager.smbuilding.infrastructure.persistence.projection.PaymentSupplierProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT p.id AS payment_id, p.documentType AS documentType, s.id AS supplier_id," +
            "p.costCenter.id AS costCenter_id, p.datePayment AS datePayment, p.receiptDocument AS receiptDocument FROM Payment p JOIN p.supplier s " +
            "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :nameSupplier, '%'))")
    Page<PaymentSupplierProjection> findNamyBySupplier(@Param("nameSupplier") String nameSupplier, Pageable pageable);

    @Query("SELECT p FROM Payment p WHERE LOWER(p.costCenter.costCenter) LIKE LOWER(CONCAT('%', :costCenterName, '%'))")
    List<Payment> findByCostCenterName(@Param("costCenterName") String costCenterName);

    @Query("SELECT p FROM Payment p WHERE LOWER(p.supplier.name) LIKE LOWER(CONCAT('%', :supplierName, '%'))")
    List<Payment> findBySupplierName(@Param("supplierName") String supplierName);

    List<Payment> findByDatePaymentBetween(LocalDate startDate, LocalDate endDate);

    List<Payment> findByPaymentAmountBetween(Double min, Double max);


}
