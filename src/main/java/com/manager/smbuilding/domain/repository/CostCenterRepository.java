package com.manager.smbuilding.domain.repository;

import com.manager.smbuilding.domain.model.CostCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {

    @Query("SELECT c.costCenter FROM CostCenter c WHERE c.id = :id")
    Optional<CostCenter> findById(@Param("id") Long id);
}
