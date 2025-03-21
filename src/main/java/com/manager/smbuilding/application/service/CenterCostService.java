package com.manager.smbuilding.application.service;

import com.manager.smbuilding.domain.model.CostCenter;
import com.manager.smbuilding.domain.repository.CostCenterRepository;
import org.springframework.stereotype.Service;

@Service
public class CenterCostService {

    private final CostCenterRepository costCenterRepository;

    public CenterCostService(CostCenterRepository costCenterRepository) {
        this.costCenterRepository = costCenterRepository;
    }

    public CostCenter findById(Long id) {
        return costCenterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cost center not found"));
    }


}
