package com.duke.bds.service.criteria;

import com.duke.bds.domain.enumeration.PostStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.duke.bds.domain.Post} entity. This class is used
 * in {@link com.duke.bds.web.rest.PostResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /posts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostCriteria implements Serializable, Criteria {

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

    private StringFilter title;

    private LongFilter price;

    private LongFilter square;

    private StringFilter address;

    private StringFilter phone;

    private StringFilter googleMapsLocation;

    private DoubleFilter width;

    private DoubleFilter length;

    private StringFilter direction;

    private StringFilter distance;

    private StringFilter legal;

    private LongFilter numberOfFloors;

    private LongFilter numberOfBedroom;

    private BooleanFilter hasKitchen;

    private BooleanFilter hasDinningRoom;

    private BooleanFilter hasRooftop;

    private BooleanFilter hasGarage;

    private BooleanFilter isVip;

    private LocalDateFilter postingTime;

    private LocalDateFilter expiredTime;

    private DoubleFilter brokerageFees;

    private PostStatusFilter status;

    private DoubleFilter star;

    private StringFilter hash;

    private LongFilter imagesId;

    private LongFilter typeId;

    private LongFilter categoryId;

    private LongFilter userId;

    private LongFilter provinceId;

    private LongFilter districtId;

    private LongFilter wardId;

    private LongFilter streetId;

    private Boolean distinct;

    public PostCriteria() {}

    public PostCriteria(PostCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.square = other.square == null ? null : other.square.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.googleMapsLocation = other.googleMapsLocation == null ? null : other.googleMapsLocation.copy();
        this.width = other.width == null ? null : other.width.copy();
        this.length = other.length == null ? null : other.length.copy();
        this.direction = other.direction == null ? null : other.direction.copy();
        this.distance = other.distance == null ? null : other.distance.copy();
        this.legal = other.legal == null ? null : other.legal.copy();
        this.numberOfFloors = other.numberOfFloors == null ? null : other.numberOfFloors.copy();
        this.numberOfBedroom = other.numberOfBedroom == null ? null : other.numberOfBedroom.copy();
        this.hasKitchen = other.hasKitchen == null ? null : other.hasKitchen.copy();
        this.hasDinningRoom = other.hasDinningRoom == null ? null : other.hasDinningRoom.copy();
        this.hasRooftop = other.hasRooftop == null ? null : other.hasRooftop.copy();
        this.hasGarage = other.hasGarage == null ? null : other.hasGarage.copy();
        this.isVip = other.isVip == null ? null : other.isVip.copy();
        this.postingTime = other.postingTime == null ? null : other.postingTime.copy();
        this.expiredTime = other.expiredTime == null ? null : other.expiredTime.copy();
        this.brokerageFees = other.brokerageFees == null ? null : other.brokerageFees.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.star = other.star == null ? null : other.star.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.imagesId = other.imagesId == null ? null : other.imagesId.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.provinceId = other.provinceId == null ? null : other.provinceId.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.wardId = other.wardId == null ? null : other.wardId.copy();
        this.streetId = other.streetId == null ? null : other.streetId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PostCriteria copy() {
        return new PostCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LongFilter getPrice() {
        return price;
    }

    public LongFilter price() {
        if (price == null) {
            price = new LongFilter();
        }
        return price;
    }

    public void setPrice(LongFilter price) {
        this.price = price;
    }

    public LongFilter getSquare() {
        return square;
    }

    public LongFilter square() {
        if (square == null) {
            square = new LongFilter();
        }
        return square;
    }

    public void setSquare(LongFilter square) {
        this.square = square;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getGoogleMapsLocation() {
        return googleMapsLocation;
    }

    public StringFilter googleMapsLocation() {
        if (googleMapsLocation == null) {
            googleMapsLocation = new StringFilter();
        }
        return googleMapsLocation;
    }

    public void setGoogleMapsLocation(StringFilter googleMapsLocation) {
        this.googleMapsLocation = googleMapsLocation;
    }

    public DoubleFilter getWidth() {
        return width;
    }

    public DoubleFilter width() {
        if (width == null) {
            width = new DoubleFilter();
        }
        return width;
    }

    public void setWidth(DoubleFilter width) {
        this.width = width;
    }

    public DoubleFilter getLength() {
        return length;
    }

    public DoubleFilter length() {
        if (length == null) {
            length = new DoubleFilter();
        }
        return length;
    }

    public void setLength(DoubleFilter length) {
        this.length = length;
    }

    public StringFilter getDirection() {
        return direction;
    }

    public StringFilter direction() {
        if (direction == null) {
            direction = new StringFilter();
        }
        return direction;
    }

    public void setDirection(StringFilter direction) {
        this.direction = direction;
    }

    public StringFilter getDistance() {
        return distance;
    }

    public StringFilter distance() {
        if (distance == null) {
            distance = new StringFilter();
        }
        return distance;
    }

    public void setDistance(StringFilter distance) {
        this.distance = distance;
    }

    public StringFilter getLegal() {
        return legal;
    }

    public StringFilter legal() {
        if (legal == null) {
            legal = new StringFilter();
        }
        return legal;
    }

    public void setLegal(StringFilter legal) {
        this.legal = legal;
    }

    public LongFilter getNumberOfFloors() {
        return numberOfFloors;
    }

    public LongFilter numberOfFloors() {
        if (numberOfFloors == null) {
            numberOfFloors = new LongFilter();
        }
        return numberOfFloors;
    }

    public void setNumberOfFloors(LongFilter numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public LongFilter getNumberOfBedroom() {
        return numberOfBedroom;
    }

    public LongFilter numberOfBedroom() {
        if (numberOfBedroom == null) {
            numberOfBedroom = new LongFilter();
        }
        return numberOfBedroom;
    }

    public void setNumberOfBedroom(LongFilter numberOfBedroom) {
        this.numberOfBedroom = numberOfBedroom;
    }

    public BooleanFilter getHasKitchen() {
        return hasKitchen;
    }

    public BooleanFilter hasKitchen() {
        if (hasKitchen == null) {
            hasKitchen = new BooleanFilter();
        }
        return hasKitchen;
    }

    public void setHasKitchen(BooleanFilter hasKitchen) {
        this.hasKitchen = hasKitchen;
    }

    public BooleanFilter getHasDinningRoom() {
        return hasDinningRoom;
    }

    public BooleanFilter hasDinningRoom() {
        if (hasDinningRoom == null) {
            hasDinningRoom = new BooleanFilter();
        }
        return hasDinningRoom;
    }

    public void setHasDinningRoom(BooleanFilter hasDinningRoom) {
        this.hasDinningRoom = hasDinningRoom;
    }

    public BooleanFilter getHasRooftop() {
        return hasRooftop;
    }

    public BooleanFilter hasRooftop() {
        if (hasRooftop == null) {
            hasRooftop = new BooleanFilter();
        }
        return hasRooftop;
    }

    public void setHasRooftop(BooleanFilter hasRooftop) {
        this.hasRooftop = hasRooftop;
    }

    public BooleanFilter getHasGarage() {
        return hasGarage;
    }

    public BooleanFilter hasGarage() {
        if (hasGarage == null) {
            hasGarage = new BooleanFilter();
        }
        return hasGarage;
    }

    public void setHasGarage(BooleanFilter hasGarage) {
        this.hasGarage = hasGarage;
    }

    public BooleanFilter getIsVip() {
        return isVip;
    }

    public BooleanFilter isVip() {
        if (isVip == null) {
            isVip = new BooleanFilter();
        }
        return isVip;
    }

    public void setIsVip(BooleanFilter isVip) {
        this.isVip = isVip;
    }

    public LocalDateFilter getPostingTime() {
        return postingTime;
    }

    public LocalDateFilter postingTime() {
        if (postingTime == null) {
            postingTime = new LocalDateFilter();
        }
        return postingTime;
    }

    public void setPostingTime(LocalDateFilter postingTime) {
        this.postingTime = postingTime;
    }

    public LocalDateFilter getExpiredTime() {
        return expiredTime;
    }

    public LocalDateFilter expiredTime() {
        if (expiredTime == null) {
            expiredTime = new LocalDateFilter();
        }
        return expiredTime;
    }

    public void setExpiredTime(LocalDateFilter expiredTime) {
        this.expiredTime = expiredTime;
    }

    public DoubleFilter getBrokerageFees() {
        return brokerageFees;
    }

    public DoubleFilter brokerageFees() {
        if (brokerageFees == null) {
            brokerageFees = new DoubleFilter();
        }
        return brokerageFees;
    }

    public void setBrokerageFees(DoubleFilter brokerageFees) {
        this.brokerageFees = brokerageFees;
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

    public DoubleFilter getStar() {
        return star;
    }

    public DoubleFilter star() {
        if (star == null) {
            star = new DoubleFilter();
        }
        return star;
    }

    public void setStar(DoubleFilter star) {
        this.star = star;
    }

    public StringFilter getHash() {
        return hash;
    }

    public StringFilter hash() {
        if (hash == null) {
            hash = new StringFilter();
        }
        return hash;
    }

    public void setHash(StringFilter hash) {
        this.hash = hash;
    }

    public LongFilter getImagesId() {
        return imagesId;
    }

    public LongFilter imagesId() {
        if (imagesId == null) {
            imagesId = new LongFilter();
        }
        return imagesId;
    }

    public void setImagesId(LongFilter imagesId) {
        this.imagesId = imagesId;
    }

    public LongFilter getTypeId() {
        return typeId;
    }

    public LongFilter typeId() {
        if (typeId == null) {
            typeId = new LongFilter();
        }
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public LongFilter categoryId() {
        if (categoryId == null) {
            categoryId = new LongFilter();
        }
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
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

    public LongFilter getStreetId() {
        return streetId;
    }

    public LongFilter streetId() {
        if (streetId == null) {
            streetId = new LongFilter();
        }
        return streetId;
    }

    public void setStreetId(LongFilter streetId) {
        this.streetId = streetId;
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
        final PostCriteria that = (PostCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(price, that.price) &&
            Objects.equals(square, that.square) &&
            Objects.equals(address, that.address) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(googleMapsLocation, that.googleMapsLocation) &&
            Objects.equals(width, that.width) &&
            Objects.equals(length, that.length) &&
            Objects.equals(direction, that.direction) &&
            Objects.equals(distance, that.distance) &&
            Objects.equals(legal, that.legal) &&
            Objects.equals(numberOfFloors, that.numberOfFloors) &&
            Objects.equals(numberOfBedroom, that.numberOfBedroom) &&
            Objects.equals(hasKitchen, that.hasKitchen) &&
            Objects.equals(hasDinningRoom, that.hasDinningRoom) &&
            Objects.equals(hasRooftop, that.hasRooftop) &&
            Objects.equals(hasGarage, that.hasGarage) &&
            Objects.equals(isVip, that.isVip) &&
            Objects.equals(postingTime, that.postingTime) &&
            Objects.equals(expiredTime, that.expiredTime) &&
            Objects.equals(brokerageFees, that.brokerageFees) &&
            Objects.equals(status, that.status) &&
            Objects.equals(star, that.star) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(imagesId, that.imagesId) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(provinceId, that.provinceId) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(wardId, that.wardId) &&
            Objects.equals(streetId, that.streetId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            price,
            square,
            address,
            phone,
            googleMapsLocation,
            width,
            length,
            direction,
            distance,
            legal,
            numberOfFloors,
            numberOfBedroom,
            hasKitchen,
            hasDinningRoom,
            hasRooftop,
            hasGarage,
            isVip,
            postingTime,
            expiredTime,
            brokerageFees,
            status,
            star,
            hash,
            imagesId,
            typeId,
            categoryId,
            userId,
            provinceId,
            districtId,
            wardId,
            streetId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (square != null ? "square=" + square + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (googleMapsLocation != null ? "googleMapsLocation=" + googleMapsLocation + ", " : "") +
            (width != null ? "width=" + width + ", " : "") +
            (length != null ? "length=" + length + ", " : "") +
            (direction != null ? "direction=" + direction + ", " : "") +
            (distance != null ? "distance=" + distance + ", " : "") +
            (legal != null ? "legal=" + legal + ", " : "") +
            (numberOfFloors != null ? "numberOfFloors=" + numberOfFloors + ", " : "") +
            (numberOfBedroom != null ? "numberOfBedroom=" + numberOfBedroom + ", " : "") +
            (hasKitchen != null ? "hasKitchen=" + hasKitchen + ", " : "") +
            (hasDinningRoom != null ? "hasDinningRoom=" + hasDinningRoom + ", " : "") +
            (hasRooftop != null ? "hasRooftop=" + hasRooftop + ", " : "") +
            (hasGarage != null ? "hasGarage=" + hasGarage + ", " : "") +
            (isVip != null ? "isVip=" + isVip + ", " : "") +
            (postingTime != null ? "postingTime=" + postingTime + ", " : "") +
            (expiredTime != null ? "expiredTime=" + expiredTime + ", " : "") +
            (brokerageFees != null ? "brokerageFees=" + brokerageFees + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (star != null ? "star=" + star + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (imagesId != null ? "imagesId=" + imagesId + ", " : "") +
            (typeId != null ? "typeId=" + typeId + ", " : "") +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (provinceId != null ? "provinceId=" + provinceId + ", " : "") +
            (districtId != null ? "districtId=" + districtId + ", " : "") +
            (wardId != null ? "wardId=" + wardId + ", " : "") +
            (streetId != null ? "streetId=" + streetId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
