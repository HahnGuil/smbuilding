package com.manager.smbuilding.domain.repository;

import com.manager.smbuilding.domain.model.Supplier;
import com.manager.smbuilding.infrastructure.persistence.projection.SupplierAddressProjection;
import com.manager.smbuilding.infrastructure.persistence.projection.SupplierNameAndCnpjProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {


    @Query("SELECT s.id AS id, s.name AS name, s.cnpj AS cnpj, s.phone AS phone, s.email AS email, " +
            "a.uf AS uf, a.city AS city, a.street AS street " +
            "FROM Supplier s LEFT JOIN s.address a " +
            "WHERE UPPER(s.name) LIKE CONCAT('%', UPPER(:name), '%')")
    List<SupplierAddressProjection> findSupplierByName(String name);

    @Query("SELECT s.name FROM Supplier s WHERE s.id = :id")
    Optional<String> findSupplierNameById(@Param("id") UUID id);

    @Query("SELECT s.name AS name, s.cnpj AS cnpj FROM Supplier s WHERE s.id = :id")
    Optional<SupplierNameAndCnpjProjection> findSupplierNameAndCnpjById(@Param("id") UUID id);
}
