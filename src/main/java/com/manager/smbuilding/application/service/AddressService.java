package com.manager.smbuilding.application.service;

import com.manager.smbuilding.application.dto.request.SupplierRequestDTO;
import com.manager.smbuilding.domain.model.Address;
import com.manager.smbuilding.domain.model.Supplier;
import com.manager.smbuilding.domain.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void createAddress(SupplierRequestDTO data, Supplier supplier) {
        Address address = new Address();
        address.setUf(data.uf());
        address.setCity(data.city());
        address.setStreet(data.street());
        address.setSupplier(supplier);
        addressRepository.save(address);
    }

    public Optional<Address> findSupplierById(UUID supplierId) {
        return addressRepository.findSupplierById(supplierId);
    }


}
