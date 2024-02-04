package com.duke.bds.service;

import com.duke.bds.domain.*; // for static metamodels
import com.duke.bds.domain.Street;
import com.duke.bds.repository.StreetRepository;
import com.duke.bds.service.criteria.StreetCriteria;
import com.duke.bds.service.dto.StreetDTO;
import com.duke.bds.service.mapper.StreetMapper;
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
 * Service for executing complex queries for {@link Street} entities in the database.
 * The main input is a {@link StreetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StreetDTO} or a {@link Page} of {@link StreetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StreetQueryService extends QueryService<Street> {

    private final Logger log = LoggerFactory.getLogger(StreetQueryService.class);

    private final StreetRepository streetRepository;

    private final StreetMapper streetMapper;

    public StreetQueryService(StreetRepository streetRepository, StreetMapper streetMapper) {
        this.streetRepository = streetRepository;
        this.streetMapper = streetMapper;
    }

    /**
     * Return a {@link List} of {@link StreetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StreetDTO> findByCriteria(StreetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Street> specification = createSpecification(criteria);
        return streetMapper.toDto(streetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StreetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StreetDTO> findByCriteria(StreetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Street> specification = createSpecification(criteria);
        return streetRepository.findAll(specification, page).map(streetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StreetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Street> specification = createSpecification(criteria);
        return streetRepository.count(specification);
    }

    /**
     * Function to convert {@link StreetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Street> createSpecification(StreetCriteria criteria) {
        Specification<Street> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Street_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Street_.name));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Street_.status));
            }
            if (criteria.getWardId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getWardId(), root -> root.join(Street_.ward, JoinType.LEFT).get(Ward_.id))
                    );
            }
            if (criteria.getDistrictId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDistrictId(), root -> root.join(Street_.district, JoinType.LEFT).get(District_.id))
                    );
            }
        }
        return specification;
    }
}
