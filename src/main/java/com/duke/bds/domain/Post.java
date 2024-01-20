package com.duke.bds.domain;

import com.duke.bds.domain.enumeration.PostStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "square")
    private Long square;

    @Column(name = "address")
    private String address;

    @Column(name = "google_maps_location")
    private String googleMapsLocation;

    @Column(name = "width")
    private Double width;

    @Column(name = "length")
    private Double length;

    @Column(name = "direction")
    private String direction;

    @Column(name = "distance")
    private String distance;

    @Column(name = "legal")
    private String legal;

    @Column(name = "number_of_floors")
    private Long numberOfFloors;

    @Column(name = "number_of_bedroom")
    private Long numberOfBedroom;

    @Column(name = "has_kitchen")
    private Boolean hasKitchen;

    @Column(name = "has_dinning_room")
    private Boolean hasDinningRoom;

    @Column(name = "has_rooftop")
    private Boolean hasRooftop;

    @Column(name = "has_garage")
    private Boolean hasGarage;

    @Column(name = "is_vip")
    private Boolean isVip;

    @Column(name = "posting_time")
    private LocalDate postingTime;

    @Column(name = "expired_time")
    private LocalDate expiredTime;

    @Column(name = "brokerage_fees")
    private Double brokerageFees;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PostStatus status;

    @Column(name = "star")
    private Double star;

    @Column(name = "hash")
    private String hash;

    @OneToMany(mappedBy = "post")
    @JsonIgnoreProperties(value = { "post" }, allowSetters = true)
    private Set<Image> images = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private PostType type;

    @ManyToOne(optional = false)
    @NotNull
    private Category category;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "districts" }, allowSetters = true)
    private Province province;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "wards", "streets", "province" }, allowSetters = true)
    private District district;

    @ManyToOne
    @JsonIgnoreProperties(value = { "streets", "district" }, allowSetters = true)
    private Ward ward;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "ward", "district" }, allowSetters = true)
    private Street street;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Post id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Post title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public Post content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPrice() {
        return this.price;
    }

    public Post price(Long price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSquare() {
        return this.square;
    }

    public Post square(Long square) {
        this.setSquare(square);
        return this;
    }

    public void setSquare(Long square) {
        this.square = square;
    }

    public String getAddress() {
        return this.address;
    }

    public Post address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoogleMapsLocation() {
        return this.googleMapsLocation;
    }

    public Post googleMapsLocation(String googleMapsLocation) {
        this.setGoogleMapsLocation(googleMapsLocation);
        return this;
    }

    public void setGoogleMapsLocation(String googleMapsLocation) {
        this.googleMapsLocation = googleMapsLocation;
    }

    public Double getWidth() {
        return this.width;
    }

    public Post width(Double width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return this.length;
    }

    public Post length(Double length) {
        this.setLength(length);
        return this;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getDirection() {
        return this.direction;
    }

    public Post direction(String direction) {
        this.setDirection(direction);
        return this;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDistance() {
        return this.distance;
    }

    public Post distance(String distance) {
        this.setDistance(distance);
        return this;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLegal() {
        return this.legal;
    }

    public Post legal(String legal) {
        this.setLegal(legal);
        return this;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public Long getNumberOfFloors() {
        return this.numberOfFloors;
    }

    public Post numberOfFloors(Long numberOfFloors) {
        this.setNumberOfFloors(numberOfFloors);
        return this;
    }

    public void setNumberOfFloors(Long numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public Long getNumberOfBedroom() {
        return this.numberOfBedroom;
    }

    public Post numberOfBedroom(Long numberOfBedroom) {
        this.setNumberOfBedroom(numberOfBedroom);
        return this;
    }

    public void setNumberOfBedroom(Long numberOfBedroom) {
        this.numberOfBedroom = numberOfBedroom;
    }

    public Boolean getHasKitchen() {
        return this.hasKitchen;
    }

    public Post hasKitchen(Boolean hasKitchen) {
        this.setHasKitchen(hasKitchen);
        return this;
    }

    public void setHasKitchen(Boolean hasKitchen) {
        this.hasKitchen = hasKitchen;
    }

    public Boolean getHasDinningRoom() {
        return this.hasDinningRoom;
    }

    public Post hasDinningRoom(Boolean hasDinningRoom) {
        this.setHasDinningRoom(hasDinningRoom);
        return this;
    }

    public void setHasDinningRoom(Boolean hasDinningRoom) {
        this.hasDinningRoom = hasDinningRoom;
    }

    public Boolean getHasRooftop() {
        return this.hasRooftop;
    }

    public Post hasRooftop(Boolean hasRooftop) {
        this.setHasRooftop(hasRooftop);
        return this;
    }

    public void setHasRooftop(Boolean hasRooftop) {
        this.hasRooftop = hasRooftop;
    }

    public Boolean getHasGarage() {
        return this.hasGarage;
    }

    public Post hasGarage(Boolean hasGarage) {
        this.setHasGarage(hasGarage);
        return this;
    }

    public void setHasGarage(Boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public Boolean getIsVip() {
        return this.isVip;
    }

    public Post isVip(Boolean isVip) {
        this.setIsVip(isVip);
        return this;
    }

    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    public LocalDate getPostingTime() {
        return this.postingTime;
    }

    public Post postingTime(LocalDate postingTime) {
        this.setPostingTime(postingTime);
        return this;
    }

    public void setPostingTime(LocalDate postingTime) {
        this.postingTime = postingTime;
    }

    public LocalDate getExpiredTime() {
        return this.expiredTime;
    }

    public Post expiredTime(LocalDate expiredTime) {
        this.setExpiredTime(expiredTime);
        return this;
    }

    public void setExpiredTime(LocalDate expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Double getBrokerageFees() {
        return this.brokerageFees;
    }

    public Post brokerageFees(Double brokerageFees) {
        this.setBrokerageFees(brokerageFees);
        return this;
    }

    public void setBrokerageFees(Double brokerageFees) {
        this.brokerageFees = brokerageFees;
    }

    public PostStatus getStatus() {
        return this.status;
    }

    public Post status(PostStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Double getStar() {
        return this.star;
    }

    public Post star(Double star) {
        this.setStar(star);
        return this;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public String getHash() {
        return this.hash;
    }

    public Post hash(String hash) {
        this.setHash(hash);
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Set<Image> getImages() {
        return this.images;
    }

    public void setImages(Set<Image> images) {
        if (this.images != null) {
            this.images.forEach(i -> i.setPost(null));
        }
        if (images != null) {
            images.forEach(i -> i.setPost(this));
        }
        this.images = images;
    }

    public Post images(Set<Image> images) {
        this.setImages(images);
        return this;
    }

    public Post addImages(Image image) {
        this.images.add(image);
        image.setPost(this);
        return this;
    }

    public Post removeImages(Image image) {
        this.images.remove(image);
        image.setPost(null);
        return this;
    }

    public PostType getType() {
        return this.type;
    }

    public void setType(PostType postType) {
        this.type = postType;
    }

    public Post type(PostType postType) {
        this.setType(postType);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Post category(Category category) {
        this.setCategory(category);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post user(User user) {
        this.setUser(user);
        return this;
    }

    public Province getProvince() {
        return this.province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Post province(Province province) {
        this.setProvince(province);
        return this;
    }

    public District getDistrict() {
        return this.district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Post district(District district) {
        this.setDistrict(district);
        return this;
    }

    public Ward getWard() {
        return this.ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public Post ward(Ward ward) {
        this.setWard(ward);
        return this;
    }

    public Street getStreet() {
        return this.street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public Post street(Street street) {
        this.setStreet(street);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", price=" + getPrice() +
            ", square=" + getSquare() +
            ", address='" + getAddress() + "'" +
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
            "}";
    }
}
