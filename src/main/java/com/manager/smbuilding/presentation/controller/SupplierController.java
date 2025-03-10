package com.manager.smbuilding.presentation.controller;

import com.manager.smbuilding.application.dto.request.SupplierRequestDTO;
import com.manager.smbuilding.application.dto.response.ErrorResponseDTO;
import com.manager.smbuilding.application.dto.response.SupplierResponseDTO;
import com.manager.smbuilding.application.service.AddressService;
import com.manager.smbuilding.application.service.SupplierService;
import com.manager.smbuilding.domain.model.Supplier;
import com.manager.smbuilding.infrastructure.persistence.projection.SupplierNameAndCnpjProjection;
import com.manager.smbuilding.presentation.mapper.SupplierMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;

    public SupplierController(SupplierService supplierService, SupplierMapper supplierMapper, AddressService addressService) {
        this.supplierService = supplierService;
        this.supplierMapper = supplierMapper;
    }

    @PostMapping("/create-supplier")
    public SupplierResponseDTO createSupplier(@RequestBody SupplierRequestDTO data) {
        Supplier newSupplier = supplierService.createSupplier(data);
        return supplierMapper.toDto(newSupplier);
    }

    @PutMapping("/update-supplier/{id}")
    public ResponseEntity<?> updateSupplier(
            @PathVariable UUID id,
            @RequestBody @Valid SupplierRequestDTO data) {

        try {
            SupplierResponseDTO updatedSupplier = supplierService.updateSupplier(id, data);
            return ResponseEntity.ok(updatedSupplier);

        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(ex.getMessage()));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("An unexpected error has occurred"));
        }
    }



    @GetMapping("/suppliers")
    public ResponseEntity<?> getSuppliersByName(
            @RequestParam(name = "name") String name) {
        try {
            List<SupplierResponseDTO> suppliers = supplierService.getSuppliersByName(name);

            if (suppliers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("No supplier found with name: " + name));
            }
            return ResponseEntity.ok(suppliers);

        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("Supplier not found with name: " + name));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("An unexpected error occurred while searching for suppliers."));
        }
    }

    @GetMapping("/supplier-name/{id}")
    public ResponseEntity<?> getSupplierNameById(@PathVariable UUID id) {
        try {
            Optional<String> supplierName = supplierService.getSupplierNameById(id);

            if (supplierName.isPresent()) {
                return ResponseEntity.ok(supplierName.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("Supplier not found with ID: " + id));
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("An unexpected error occurred while fetching the vendor name."));
        }
    }

    @GetMapping("/name-and-cnpj/{id}")
    public ResponseEntity<?> getSupplierNameAndCnpj(@PathVariable UUID id) {
        Optional<SupplierNameAndCnpjProjection> optionalSupplier = supplierService.findSupplierNameAndCnpjById(id);

        if (optionalSupplier.isPresent()) {
            return ResponseEntity.ok(optionalSupplier.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO("Supplier not found with ID: " + id));
        }
    }
}