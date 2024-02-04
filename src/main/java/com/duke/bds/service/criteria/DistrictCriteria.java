package com.duke.bds.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.duke.bds.domain.District} entity. This class is used
 * in {@link com.duke.bds.web.rest.DistrictResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /districts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DistrictCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter wardsId;

    private LongFilter streetsId;

    private LongFilter provinceId;

    private Boolean distinct;

    public DistrictCriteria() {}

    public DistrictCriteria(DistrictCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.wardsId = other.wardsId == null ? null : other.wardsId.copy();
        this.streetsId = other.streetsId == null ? null : other.streetsId.copy();
        this.provinceId = other.provinceId == null ? null : other.provinceId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DistrictCriteria copy() {
        return new DistrictCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getWardsId() {
        return wardsId;
    }

    public LongFilter wardsId() {
        if (wardsId == null) {
            wardsId = new LongFilter();
        }
        return wardsId;
    }

    public void setWardsId(LongFilter wardsId) {
        this.wardsId = wardsId;
    }

    public LongFilter getStreetsId() {
        return streetsId;
    }

    public LongFilter streetsId() {
        if (streetsId == null) {
            streetsId = new LongFilter();
        }
        return streetsId;
    }

    public void setStreetsId(LongFilter streetsId) {
        this.streetsId = streetsId;
    }

    public LongFilter getProvinceId() {
        return provinceId;
    }

    public LongFilter provinceId() {
        if (provinceId == null) {
            provinceId = new LongFilter();
        }
        return provinceId;
    }

    public void setProvinceId(LongFilter provinceId) {
        this.provinceId = provinceId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DistrictCriteria that = (DistrictCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(wardsId, that.wardsId) &&
            Objects.equals(streetsId, that.streetsId) &&
            Objects.equals(provinceId, that.provinceId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, wardsId, streetsId, provinceId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (wardsId != null ? "wardsId=" + wardsId + ", " : "") +
            (streetsId != null ? "streetsId=" + streetsId + ", " : "") +
            (provinceId != null ? "provinceId=" + provinceId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
