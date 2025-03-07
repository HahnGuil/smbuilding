package com.manager.smbuilding.application.service;

import com.manager.smbuilding.application.dto.request.SupplierRequestDTO;
import com.manager.smbuilding.application.dto.response.SupplierResponseDTO;
import com.manager.smbuilding.domain.model.Address;
import com.manager.smbuilding.domain.model.Supplier;
import com.manager.smbuilding.domain.repository.SupplierRepository;
import com.manager.smbuilding.infrastructure.persistence.projection.SupplierAddressProjection;
import com.manager.smbuilding.infrastructure.persistence.projection.SupplierNameAndCnpjProjection;
import com.manager.smbuilding.presentation.mapper.SupplierMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Supplier createSupplier(SupplierRequestDTO data) {
        Supplier newSupplier = supplierMapper.toEntity(data);
        Address newAddress = addressService.createAddress(data);
        newSupplier.setAddress(newAddress);
        newAddress.setSupplier(newSupplier);
        return supplierRepository.save(newSupplier);
    }

    public SupplierResponseDTO updateSupplier(UUID id, SupplierRequestDTO data) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + id));

        supplierMapper.updateEntity(existingSupplier, data);

        Address address = addressService.getAddressBySupplierId(id)
                .orElseGet(() -> {
                    Address newAddress = addressService.createAddress(data);
                    newAddress.setSupplier(existingSupplier);
                    return newAddress;
                });

        address.setUf(data.uf());
        address.setCity(data.city());
        address.setStreet(data.street());

        supplierRepository.save(existingSupplier);

        return new SupplierResponseDTO(
                existingSupplier.getName(),
                existingSupplier.getCnpj(),
                existingSupplier.getPhone(),
                existingSupplier.getEmail(),
                address.getUf(),
                address.getCity(),
                address.getStreet()
        );
    }

    public List<SupplierResponseDTO> getSuppliersByName(String name) {
        List<SupplierAddressProjection> suppliers = supplierRepository.findSupplierByName(name);
        return suppliers.stream()
                .map(supplier -> new SupplierResponseDTO(
                        supplier.getName(),
                        supplier.getCnpj(),
                        supplier.getPhone(),
                        supplier.getEmail(),
                        supplier.getUf(),
                        supplier.getCity(),
                        supplier.getStreet()
                ))
                .collect(Collectors.toList());
    }

    public Optional<String> getSupplierNameById(UUID id) {
        return supplierRepository.findSupplierNameById(id);
    }

    public Optional<SupplierNameAndCnpjProjection> findSupplierNameAndCnpjById(UUID id) {
        return supplierRepository.findSupplierNameAndCnpjById(id);
    }






}
