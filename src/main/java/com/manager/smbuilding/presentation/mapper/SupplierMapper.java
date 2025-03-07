package com.manager.smbuilding.presentation.mapper;

import com.manager.smbuilding.application.dto.request.SupplierRequestDTO;
import com.manager.smbuilding.application.dto.response.SupplierResponseDTO;
import com.manager.smbuilding.domain.model.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "dto.name", target = "name"),
            @Mapping(source = "dto.cnpj", target = "cnpj"),
            @Mapping(source = "dto.phone", target = "phone"),
            @Mapping(source = "dto.email", target = "email")
    })
    Supplier toEntity(SupplierRequestDTO dto);

    @Mappings({
            @Mapping(source = "entity.name", target = "name"),
            @Mapping(source = "entity.cnpj", target = "cnpj"),
            @Mapping(source = "entity.phone", target = "phone"),
            @Mapping(source = "entity.email", target = "email"),
            @Mapping(source = "entity.address.uf", target = "state"), // Mapeia uf para state
            @Mapping(source = "entity.address.city", target = "city"), // Mapeia city
            @Mapping(source = "entity.address.street", target = "street") // Mapeia street
    })
    SupplierResponseDTO toDto(Supplier entity);

    default void updateEntity(@MappingTarget Supplier entity, SupplierRequestDTO dto) {
        entity.setName(dto.name());
        entity.setCnpj(dto.cnpj());
        entity.setPhone(dto.phone());
        entity.setEmail(dto.email());
    }
}