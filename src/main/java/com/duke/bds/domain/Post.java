package com.duke.bds.domain;

import com.duke.bds.domain.enumeration.PostStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PostStatus status;

    @Column(name = "hash")
    private String hash;

    @OneToMany(mappedBy = "post")
    @JsonIgnoreProperties(value = { "post" }, allowSetters = true)
    private Set<Image> images = new HashSet<>();

    @ManyToOne
    private PostType type;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

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
            ", status='" + getStatus() + "'" +
            ", hash='" + getHash() + "'" +
            "}";
    }
}
