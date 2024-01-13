package com.duke.bds.service.dto;

import com.duke.bds.domain.enumeration.PostStatus;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.duke.bds.domain.Street} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StreetDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private PostStatus status;

    private WardDTO ward;

    private DistrictDTO district;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public WardDTO getWard() {
        return ward;
    }

    public void setWard(WardDTO ward) {
        this.ward = ward;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StreetDTO)) {
            return false;
        }

        StreetDTO streetDTO = (StreetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, streetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StreetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", ward=" + getWard() +
            ", district=" + getDistrict() +
            "}";
    }
}
