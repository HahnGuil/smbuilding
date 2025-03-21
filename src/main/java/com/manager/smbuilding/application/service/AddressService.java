package com.manager.smbuilding.application.service;

import com.manager.smbuilding.application.dto.request.SupplierRequestDTO;
import com.manager.smbuilding.domain.model.Address;
import com.manager.smbuilding.domain.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address createAddress(SupplierRequestDTO data) {
        Address address = new Address();
        address.setUf(data.uf());
        address.setCity(data.city());
        address.setStreet(data.street());
        return address;
    }

    public void updateAddress(Long id, SupplierRequestDTO data) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com o ID: " + id));

        address.setUf(data.uf());
        address.setCity(data.city());
        address.setStreet(data.street());
        addressRepository.save(address);
    }


    public Optional<Address> getAddressBySupplierId(Long supplierId) {
        return addressRepository.findSupplierById(supplierId);
    }



}
