package com.duke.bds.service;

import com.duke.bds.domain.*; // for static metamodels
import com.duke.bds.domain.PostType;
import com.duke.bds.repository.PostTypeRepository;
import com.duke.bds.service.criteria.PostTypeCriteria;
import com.duke.bds.service.dto.PostTypeDTO;
import com.duke.bds.service.mapper.PostTypeMapper;
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
 * Service for executing complex queries for {@link PostType} entities in the database.
 * The main input is a {@link PostTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PostTypeDTO} or a {@link Page} of {@link PostTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PostTypeQueryService extends QueryService<PostType> {

    private final Logger log = LoggerFactory.getLogger(PostTypeQueryService.class);

    private final PostTypeRepository postTypeRepository;

    private final PostTypeMapper postTypeMapper;

    public PostTypeQueryService(PostTypeRepository postTypeRepository, PostTypeMapper postTypeMapper) {
        this.postTypeRepository = postTypeRepository;
        this.postTypeMapper = postTypeMapper;
    }

    /**
     * Return a {@link List} of {@link PostTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PostTypeDTO> findByCriteria(PostTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PostType> specification = createSpecification(criteria);
        return postTypeMapper.toDto(postTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PostTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PostTypeDTO> findByCriteria(PostTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PostType> specification = createSpecification(criteria);
        return postTypeRepository.findAll(specification, page).map(postTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PostTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PostType> specification = createSpecification(criteria);
        return postTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link PostTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PostType> createSpecification(PostTypeCriteria criteria) {
        Specification<PostType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PostType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PostType_.name));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), PostType_.icon));
            }
        }
        return specification;
    }
}
