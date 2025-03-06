package com.manager.smbuilding.application.service;

import com.manager.smbuilding.application.dto.request.SupplierRequestDTO;
import com.manager.smbuilding.domain.model.Supplier;
import com.manager.smbuilding.domain.repository.SupplierRepository;
import com.manager.smbuilding.presentation.mapper.SupplierMapper;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final AddressService addressService;
    private final SupplierMapper supplierMapper;

    public SupplierService(SupplierRepository supplierRepository, AddressService addressService, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.addressService = addressService;
        this.supplierMapper = supplierMapper;
    }

    public Supplier createSupplier(SupplierRequestDTO data){

        Supplier newSupplier = supplierMapper.toEntity(data);
        supplierRepository.save(newSupplier);
        addressService.createAddress(data, newSupplier);

        return newSupplier;
    }
}
