package com.manager.smbuilding.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STATE", nullable = false)
    private String uf;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "STREET",nullable = false)
    private String street;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Supplier supplier;

    public Address() {}

    public Address(Long id, String uf, String city, String street, Supplier supplier) {
        this.id = id;
        this.uf = uf;
        this.city = city;
        this.street = street;
        this.supplier = supplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
