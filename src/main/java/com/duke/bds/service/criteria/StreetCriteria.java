package com.duke.bds.service.criteria;

import com.duke.bds.domain.enumeration.PostStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.duke.bds.domain.Street} entity. This class is used
 * in {@link com.duke.bds.web.rest.StreetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /streets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StreetCriteria implements Serializable, Criteria {

    /**
     * Class for filtering PostStatus
     */
    public static class PostStatusFilter extends Filter<PostStatus> {

        public PostStatusFilter() {}

        public PostStatusFilter(PostStatusFilter filter) {
            super(filter);
        }

        @Override
        public PostStatusFilter copy() {
            return new PostStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private PostStatusFilter status;

    private LongFilter wardId;

    private LongFilter districtId;

    private Boolean distinct;

    public StreetCriteria() {}

    public StreetCriteria(StreetCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.wardId = other.wardId == null ? null : other.wardId.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StreetCriteria copy() {
        return new StreetCriteria(this);
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

    public PostStatusFilter getStatus() {
        return status;
    }

    public PostStatusFilter status() {
        if (status == null) {
            status = new PostStatusFilter();
        }
        return status;
    }

    public void setStatus(PostStatusFilter status) {
        this.status = status;
    }

    public LongFilter getWardId() {
        return wardId;
    }

    public LongFilter wardId() {
        if (wardId == null) {
            wardId = new LongFilter();
        }
        return wardId;
    }

    public void setWardId(LongFilter wardId) {
        this.wardId = wardId;
    }

    public LongFilter getDistrictId() {
        return districtId;
    }

    public LongFilter districtId() {
        if (districtId == null) {
            districtId = new LongFilter();
        }
        return districtId;
    }

    public void setDistrictId(LongFilter districtId) {
        this.districtId = districtId;
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
        final StreetCriteria that = (StreetCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(status, that.status) &&
            Objects.equals(wardId, that.wardId) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, wardId, districtId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StreetCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (wardId != null ? "wardId=" + wardId + ", " : "") +
            (districtId != null ? "districtId=" + districtId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
