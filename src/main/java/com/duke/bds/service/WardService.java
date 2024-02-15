package com.duke.bds.service;

import com.duke.bds.service.dto.WardDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.duke.bds.domain.Ward}.
 */
public interface WardService {
    /**
     * Save a ward.
     *
     * @param wardDTO the entity to save.
     * @return the persisted entity.
     */
    WardDTO save(WardDTO wardDTO);

    /**
     * Updates a ward.
     *
     * @param wardDTO the entity to update.
     * @return the persisted entity.
     */
    WardDTO update(WardDTO wardDTO);

    /**
     * Partially updates a ward.
     *
     * @param wardDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WardDTO> partialUpdate(WardDTO wardDTO);

    /**
     * Get all the wards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WardDTO> findAll(Pageable pageable);

    /**
     * Get all the wards with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WardDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" ward.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WardDTO> findOne(Long id);

    /**
     * Delete the "id" ward.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<WardDTO> findByProvinceId(Long provinceId, Pageable pageable);
}
