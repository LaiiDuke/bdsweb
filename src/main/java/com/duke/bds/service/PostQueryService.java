package com.duke.bds.service;

import com.duke.bds.domain.*; // for static metamodels
import com.duke.bds.domain.Post;
import com.duke.bds.repository.PostRepository;
import com.duke.bds.service.criteria.PostCriteria;
import com.duke.bds.service.dto.PostDTO;
import com.duke.bds.service.mapper.PostMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Post} entities in the database.
 * The main input is a {@link PostCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PostDTO} or a {@link Page} of {@link PostDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PostQueryService extends QueryService<Post> {

    private final Logger log = LoggerFactory.getLogger(PostQueryService.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    public PostQueryService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    /**
     * Return a {@link List} of {@link PostDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PostDTO> findByCriteria(PostCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Post> specification = createSpecification(criteria);
        return postMapper.toDto(postRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PostDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PostDTO> findByCriteria(PostCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Post> specification = createSpecification(criteria);
        return postRepository.findAll(specification, page).map(postMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PostCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Post> specification = createSpecification(criteria);
        return postRepository.count(specification);
    }

    /**
     * Function to convert {@link PostCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Post> createSpecification(PostCriteria criteria) {
        Specification<Post> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Post_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Post_.title));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Post_.price));
            }
            if (criteria.getSquare() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSquare(), Post_.square));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Post_.address));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Post_.phone));
            }
            if (criteria.getGoogleMapsLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoogleMapsLocation(), Post_.googleMapsLocation));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWidth(), Post_.width));
            }
            if (criteria.getLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLength(), Post_.length));
            }
            if (criteria.getDirection() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDirection(), Post_.direction));
            }
            if (criteria.getDistance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistance(), Post_.distance));
            }
            if (criteria.getLegal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLegal(), Post_.legal));
            }
            if (criteria.getNumberOfFloors() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfFloors(), Post_.numberOfFloors));
            }
            if (criteria.getNumberOfBedroom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfBedroom(), Post_.numberOfBedroom));
            }
            if (criteria.getHasKitchen() != null) {
                specification = specification.and(buildSpecification(criteria.getHasKitchen(), Post_.hasKitchen));
            }
            if (criteria.getHasDinningRoom() != null) {
                specification = specification.and(buildSpecification(criteria.getHasDinningRoom(), Post_.hasDinningRoom));
            }
            if (criteria.getHasRooftop() != null) {
                specification = specification.and(buildSpecification(criteria.getHasRooftop(), Post_.hasRooftop));
            }
            if (criteria.getHasGarage() != null) {
                specification = specification.and(buildSpecification(criteria.getHasGarage(), Post_.hasGarage));
            }
            if (criteria.getIsVip() != null) {
                specification = specification.and(buildSpecification(criteria.getIsVip(), Post_.isVip));
            }
            if (criteria.getPostingTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostingTime(), Post_.postingTime));
            }
            if (criteria.getExpiredTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiredTime(), Post_.expiredTime));
            }
            if (criteria.getBrokerageFees() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBrokerageFees(), Post_.brokerageFees));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Post_.status));
            }
            if (criteria.getStar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStar(), Post_.star));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), Post_.hash));
            }
            if (criteria.getImagesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getImagesId(), root -> root.join(Post_.images, JoinType.LEFT).get(Image_.id))
                    );
            }
            if (criteria.getTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTypeId(), root -> root.join(Post_.type, JoinType.LEFT).get(PostType_.id))
                    );
            }
            if (criteria.getCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCategoryId(), root -> root.join(Post_.category, JoinType.LEFT).get(Category_.id))
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getUserId(), root -> root.join(Post_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getProvinceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProvinceId(), root -> root.join(Post_.province, JoinType.LEFT).get(Province_.id))
                    );
            }
            if (criteria.getDistrictId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDistrictId(), root -> root.join(Post_.district, JoinType.LEFT).get(District_.id))
                    );
            }
            if (criteria.getWardId() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getWardId(), root -> root.join(Post_.ward, JoinType.LEFT).get(Ward_.id)));
            }
            if (criteria.getStreetId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStreetId(), root -> root.join(Post_.street, JoinType.LEFT).get(Street_.id))
                    );
            }
        }
        return specification;
    }
}
