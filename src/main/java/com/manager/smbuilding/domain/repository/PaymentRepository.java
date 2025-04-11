package com.manager.smbuilding.domain.repository;

import com.manager.smbuilding.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT p FROM Payment p WHERE LOWER(p.costCenter.costCenter) LIKE LOWER(CONCAT('%', :costCenterName, '%'))")
    List<Payment> findByCostCenterName(@Param("costCenterName") String costCenterName);

    @Query("SELECT p FROM Payment p WHERE LOWER(p.supplier.name) LIKE LOWER(CONCAT('%', :supplierName, '%'))")
    List<Payment> findBySupplierName(@Param("supplierName") String supplierName);

    List<Payment> findByDatePaymentBetween(LocalDate startDate, LocalDate endDate);

    List<Payment> findByPaymentAmountBetween(Double min, Double max);


}
