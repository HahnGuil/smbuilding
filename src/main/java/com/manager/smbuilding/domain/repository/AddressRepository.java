package com.manager.smbuilding.domain.repository;

import com.manager.smbuilding.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    public Optional<Address> findSupplierById(UUID id);

}
