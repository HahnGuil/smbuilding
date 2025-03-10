package com.manager.smbuilding.domain.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "COST_CENTER")
public class CostCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COST_CENTER", nullable = false, unique = true)
    private String costCenter;

    public CostCenter() {}

    public CostCenter(Long id, String costCenter) {
        this.id = id;
        this.costCenter = costCenter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostCenter that = (CostCenter) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CostCenter{ id=" + id + ", costCenter='" + costCenter + '\'' + '}';
    }
}
