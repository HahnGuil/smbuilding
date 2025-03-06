package com.manager.smbuilding.infrastructure.persistence.projection;

import java.util.UUID;

public interface SupplierAddressProjection {
    UUID getId();
    String getName();
    String getCnpj();
    String getPhone();
    String getEmail();
    String getUf();
    String getCity();
    String getStreet();
}
