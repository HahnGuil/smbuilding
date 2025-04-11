package com.manager.smbuilding.domain.repository;

import com.manager.smbuilding.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findSupplierById(Long id);




}
