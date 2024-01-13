package com.duke.bds.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Ward.
 */
@Entity
@Table(name = "ward")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ward implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "ward")
    @JsonIgnoreProperties(value = { "ward", "district" }, allowSetters = true)
    private Set<Street> streets = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "wards", "streets", "province" }, allowSetters = true)
    private District district;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ward id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Ward name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Street> getStreets() {
        return this.streets;
    }

    public void setStreets(Set<Street> streets) {
        if (this.streets != null) {
            this.streets.forEach(i -> i.setWard(null));
        }
        if (streets != null) {
            streets.forEach(i -> i.setWard(this));
        }
        this.streets = streets;
    }

    public Ward streets(Set<Street> streets) {
        this.setStreets(streets);
        return this;
    }

    public Ward addStreets(Street street) {
        this.streets.add(street);
        street.setWard(this);
        return this;
    }

    public Ward removeStreets(Street street) {
        this.streets.remove(street);
        street.setWard(null);
        return this;
    }

    public District getDistrict() {
        return this.district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Ward district(District district) {
        this.setDistrict(district);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ward)) {
            return false;
        }
        return id != null && id.equals(((Ward) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ward{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
