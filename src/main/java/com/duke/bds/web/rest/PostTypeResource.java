package com.duke.bds.web.rest;

import com.duke.bds.repository.PostTypeRepository;
import com.duke.bds.service.PostTypeService;
import com.duke.bds.service.dto.PostTypeDTO;
import com.duke.bds.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.duke.bds.domain.PostType}.
 */
@RestController
@RequestMapping("/api")
public class PostTypeResource {

    private final Logger log = LoggerFactory.getLogger(PostTypeResource.class);

    private static final String ENTITY_NAME = "postType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostTypeService postTypeService;

    private final PostTypeRepository postTypeRepository;

    public PostTypeResource(PostTypeService postTypeService, PostTypeRepository postTypeRepository) {
        this.postTypeService = postTypeService;
        this.postTypeRepository = postTypeRepository;
    }

    /**
     * {@code POST  /post-types} : Create a new postType.
     *
     * @param postTypeDTO the postTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postTypeDTO, or with status {@code 400 (Bad Request)} if the postType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-types")
    public ResponseEntity<PostTypeDTO> createPostType(@Valid @RequestBody PostTypeDTO postTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PostType : {}", postTypeDTO);
        if (postTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new postType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostTypeDTO result = postTypeService.save(postTypeDTO);
        return ResponseEntity
            .created(new URI("/api/post-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-types/:id} : Updates an existing postType.
     *
     * @param id the id of the postTypeDTO to save.
     * @param postTypeDTO the postTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postTypeDTO,
     * or with status {@code 400 (Bad Request)} if the postTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-types/{id}")
    public ResponseEntity<PostTypeDTO> updatePostType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PostTypeDTO postTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PostType : {}, {}", id, postTypeDTO);
        if (postTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PostTypeDTO result = postTypeService.update(postTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /post-types/:id} : Partial updates given fields of an existing postType, field will ignore if it is null
     *
     * @param id the id of the postTypeDTO to save.
     * @param postTypeDTO the postTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postTypeDTO,
     * or with status {@code 400 (Bad Request)} if the postTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the postTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the postTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/post-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PostTypeDTO> partialUpdatePostType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PostTypeDTO postTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PostType partially : {}, {}", id, postTypeDTO);
        if (postTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PostTypeDTO> result = postTypeService.partialUpdate(postTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /post-types} : get all the postTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postTypes in body.
     */
    @GetMapping("/post-types")
    public ResponseEntity<List<PostTypeDTO>> getAllPostTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PostTypes");
        Page<PostTypeDTO> page = postTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-types/:id} : get the "id" postType.
     *
     * @param id the id of the postTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-types/{id}")
    public ResponseEntity<PostTypeDTO> getPostType(@PathVariable Long id) {
        log.debug("REST request to get PostType : {}", id);
        Optional<PostTypeDTO> postTypeDTO = postTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postTypeDTO);
    }

    /**
     * {@code DELETE  /post-types/:id} : delete the "id" postType.
     *
     * @param id the id of the postTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-types/{id}")
    public ResponseEntity<Void> deletePostType(@PathVariable Long id) {
        log.debug("REST request to delete PostType : {}", id);
        postTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
