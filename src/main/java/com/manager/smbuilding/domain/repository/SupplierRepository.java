package com.manager.smbuilding.domain.repository;

import com.manager.smbuilding.domain.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    @Query("SELECT s.name FROM Supplier s WHERE s.id = :idSupplier")
    Optional<String> getSupplierNameById(@Param("idSupplier") UUID idSupplier);
}
