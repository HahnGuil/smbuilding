package com.manager.smbuilding.domain.repository;

import com.manager.smbuilding.domain.model.CostCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {

}
