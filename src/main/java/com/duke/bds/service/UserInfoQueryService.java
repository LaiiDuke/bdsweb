package com.duke.bds.service;

import com.duke.bds.domain.*; // for static metamodels
import com.duke.bds.domain.UserInfo;
import com.duke.bds.repository.UserInfoRepository;
import com.duke.bds.service.criteria.UserInfoCriteria;
import com.duke.bds.service.dto.UserInfoDTO;
import com.duke.bds.service.mapper.UserInfoMapper;
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
 * Service for executing complex queries for {@link UserInfo} entities in the database.
 * The main input is a {@link UserInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserInfoDTO} or a {@link Page} of {@link UserInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserInfoQueryService extends QueryService<UserInfo> {

    private final Logger log = LoggerFactory.getLogger(UserInfoQueryService.class);

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    public UserInfoQueryService(UserInfoRepository userInfoRepository, UserInfoMapper userInfoMapper) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoMapper = userInfoMapper;
    }

    /**
     * Return a {@link List} of {@link UserInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserInfoDTO> findByCriteria(UserInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserInfo> specification = createSpecification(criteria);
        return userInfoMapper.toDto(userInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserInfoDTO> findByCriteria(UserInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserInfo> specification = createSpecification(criteria);
        return userInfoRepository.findAll(specification, page).map(userInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserInfo> specification = createSpecification(criteria);
        return userInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link UserInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserInfo> createSpecification(UserInfoCriteria criteria) {
        Specification<UserInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserInfo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), UserInfo_.name));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), UserInfo_.phone));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(UserInfo_.user, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}