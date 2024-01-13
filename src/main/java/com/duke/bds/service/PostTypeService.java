package com.duke.bds.service;

import com.duke.bds.service.dto.PostTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.duke.bds.domain.PostType}.
 */
public interface PostTypeService {
    /**
     * Save a postType.
     *
     * @param postTypeDTO the entity to save.
     * @return the persisted entity.
     */
    PostTypeDTO save(PostTypeDTO postTypeDTO);

    /**
     * Updates a postType.
     *
     * @param postTypeDTO the entity to update.
     * @return the persisted entity.
     */
    PostTypeDTO update(PostTypeDTO postTypeDTO);

    /**
     * Partially updates a postType.
     *
     * @param postTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PostTypeDTO> partialUpdate(PostTypeDTO postTypeDTO);

    /**
     * Get all the postTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" postType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostTypeDTO> findOne(Long id);

    /**
     * Delete the "id" postType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
