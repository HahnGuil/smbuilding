package com.manager.smbuilding.domain.repository;

import com.manager.smbuilding.domain.model.Payment;
import com.manager.smbuilding.infrastructure.persistence.projection.PaymentSupplierProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT p.id AS payment_id, p.documentType AS documentType, s.id AS supplier_id," +
            "p.costCenter.id AS costCenter_id, p.datePayment AS datePayment, p.receiptDocument AS receiptDocument FROM Payment p JOIN p.supplier s " +
            "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :nameSupplier, '%'))")
    Page<PaymentSupplierProjection> findNamyBySupplier(@Param("nameSupplier") String nameSupplier, Pageable pageable);


}
