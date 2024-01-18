package com.duke.bds.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A District.
 */
@Entity
@Table(name = "district")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "district")
    @JsonIgnoreProperties(value = { "streets", "district" }, allowSetters = true)
    private Set<Ward> wards = new HashSet<>();

    @OneToMany(mappedBy = "district")
    @JsonIgnoreProperties(value = { "ward", "district" }, allowSetters = true)
    private Set<Street> streets = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "districts" }, allowSetters = true)
    private Province province;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public District id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public District name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Ward> getWards() {
        return this.wards;
    }

    public void setWards(Set<Ward> wards) {
        if (this.wards != null) {
            this.wards.forEach(i -> i.setDistrict(null));
        }
        if (wards != null) {
            wards.forEach(i -> i.setDistrict(this));
        }
        this.wards = wards;
    }

    public District wards(Set<Ward> wards) {
        this.setWards(wards);
        return this;
    }

    public District addWards(Ward ward) {
        this.wards.add(ward);
        ward.setDistrict(this);
        return this;
    }

    public District removeWards(Ward ward) {
        this.wards.remove(ward);
        ward.setDistrict(null);
        return this;
    }

    public Set<Street> getStreets() {
        return this.streets;
    }

    public void setStreets(Set<Street> streets) {
        if (this.streets != null) {
            this.streets.forEach(i -> i.setDistrict(null));
        }
        if (streets != null) {
            streets.forEach(i -> i.setDistrict(this));
        }
        this.streets = streets;
    }

    public District streets(Set<Street> streets) {
        this.setStreets(streets);
        return this;
    }

    public District addStreets(Street street) {
        this.streets.add(street);
        street.setDistrict(this);
        return this;
    }

    public District removeStreets(Street street) {
        this.streets.remove(street);
        street.setDistrict(null);
        return this;
    }

    public Province getProvince() {
        return this.province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public District province(Province province) {
        this.setProvince(province);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof District)) {
            return false;
        }
        return id != null && id.equals(((District) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "District{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
