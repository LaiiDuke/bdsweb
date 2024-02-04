package com.duke.bds.service.dto;

import com.duke.bds.domain.enumeration.PostStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.duke.bds.domain.Post} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String title;

    @Lob
    private String content;

    @NotNull
    private Long price;

    private Long square;

    private String address;

    private String phone;

    private String googleMapsLocation;

    private Double width;

    private Double length;

    private String direction;

    private String distance;

    private String legal;

    private Long numberOfFloors;

    private Long numberOfBedroom;

    private Boolean hasKitchen;

    private Boolean hasDinningRoom;

    private Boolean hasRooftop;

    private Boolean hasGarage;

    private Boolean isVip;

    private LocalDate postingTime;

    private LocalDate expiredTime;

    private Double brokerageFees;

    private PostStatus status;

    private Double star;

    private String hash;

    private PostTypeDTO type;

    private CategoryDTO category;

    private UserDTO user;

    private ProvinceDTO province;

    private DistrictDTO district;

    private WardDTO ward;

    private StreetDTO street;

    private ImageDTO image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSquare() {
        return square;
    }

    public void setSquare(Long square) {
        this.square = square;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGoogleMapsLocation() {
        return googleMapsLocation;
    }

    public void setGoogleMapsLocation(String googleMapsLocation) {
        this.googleMapsLocation = googleMapsLocation;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public Long getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(Long numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public Long getNumberOfBedroom() {
        return numberOfBedroom;
    }

    public void setNumberOfBedroom(Long numberOfBedroom) {
        this.numberOfBedroom = numberOfBedroom;
    }

    public Boolean getHasKitchen() {
        return hasKitchen;
    }

    public void setHasKitchen(Boolean hasKitchen) {
        this.hasKitchen = hasKitchen;
    }

    public Boolean getHasDinningRoom() {
        return hasDinningRoom;
    }

    public void setHasDinningRoom(Boolean hasDinningRoom) {
        this.hasDinningRoom = hasDinningRoom;
    }

    public Boolean getHasRooftop() {
        return hasRooftop;
    }

    public void setHasRooftop(Boolean hasRooftop) {
        this.hasRooftop = hasRooftop;
    }

    public Boolean getHasGarage() {
        return hasGarage;
    }

    public void setHasGarage(Boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public Boolean getIsVip() {
        return isVip;
    }

    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    public LocalDate getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(LocalDate postingTime) {
        this.postingTime = postingTime;
    }

    public LocalDate getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDate expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Double getBrokerageFees() {
        return brokerageFees;
    }

    public void setBrokerageFees(Double brokerageFees) {
        this.brokerageFees = brokerageFees;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public PostTypeDTO getType() {
        return type;
    }

    public void setType(PostTypeDTO type) {
        this.type = type;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ProvinceDTO getProvince() {
        return province;
    }

    public void setProvince(ProvinceDTO province) {
        this.province = province;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    public WardDTO getWard() {
        return ward;
    }

    public void setWard(WardDTO ward) {
        this.ward = ward;
    }

    public StreetDTO getStreet() {
        return street;
    }

    public void setStreet(StreetDTO street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDTO)) {
            return false;
        }

        PostDTO postDTO = (PostDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, postDTO.id);
    }

    public ImageDTO getImage() {
        return image;
    }

    public void setImage(ImageDTO image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", price=" + getPrice() +
            ", square=" + getSquare() +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", googleMapsLocation='" + getGoogleMapsLocation() + "'" +
            ", width=" + getWidth() +
            ", length=" + getLength() +
            ", direction='" + getDirection() + "'" +
            ", distance='" + getDistance() + "'" +
            ", legal='" + getLegal() + "'" +
            ", numberOfFloors=" + getNumberOfFloors() +
            ", numberOfBedroom=" + getNumberOfBedroom() +
            ", hasKitchen='" + getHasKitchen() + "'" +
            ", hasDinningRoom='" + getHasDinningRoom() + "'" +
            ", hasRooftop='" + getHasRooftop() + "'" +
            ", hasGarage='" + getHasGarage() + "'" +
            ", isVip='" + getIsVip() + "'" +
            ", postingTime='" + getPostingTime() + "'" +
            ", expiredTime='" + getExpiredTime() + "'" +
            ", brokerageFees=" + getBrokerageFees() +
            ", status='" + getStatus() + "'" +
            ", star=" + getStar() +
            ", hash='" + getHash() + "'" +
            ", type=" + getType() +
            ", category=" + getCategory() +
            ", user=" + getUser() +
            ", province=" + getProvince() +
            ", district=" + getDistrict() +
            ", ward=" + getWard() +
            ", street=" + getStreet() +
            "}";
    }
}
