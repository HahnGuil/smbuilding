package com.manager.smbuilding.domain.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "STATE", nullable = false)
    private String uf;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "STREET",nullable = false)
    private String street;

    @OneToOne
    @JoinColumn(name = "id")
    private Supplier supplier;

    public Address() {}

    public Address(UUID id, String uf, String city, String street, Supplier supplier) {
        this.id = id;
        this.uf = uf;
        this.city = city;
        this.street = street;
        this.supplier = supplier;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getId(), address.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", uf='" + uf + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", supplier=" + supplier +
                '}';
    }
}
