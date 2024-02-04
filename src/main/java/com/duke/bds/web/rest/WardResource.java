package com.duke.bds.web.rest;

import com.duke.bds.repository.WardRepository;
import com.duke.bds.service.WardQueryService;
import com.duke.bds.service.WardService;
import com.duke.bds.service.criteria.WardCriteria;
import com.duke.bds.service.dto.WardDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.duke.bds.domain.Ward}.
 */
@RestController
@RequestMapping("/api")
public class WardResource {

    private final Logger log = LoggerFactory.getLogger(WardResource.class);

    private static final String ENTITY_NAME = "ward";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WardService wardService;

    private final WardRepository wardRepository;

    private final WardQueryService wardQueryService;

    public WardResource(WardService wardService, WardRepository wardRepository, WardQueryService wardQueryService) {
        this.wardService = wardService;
        this.wardRepository = wardRepository;
        this.wardQueryService = wardQueryService;
    }

    /**
     * {@code POST  /wards} : Create a new ward.
     *
     * @param wardDTO the wardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wardDTO, or with status {@code 400 (Bad Request)} if the ward has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wards")
    public ResponseEntity<WardDTO> createWard(@Valid @RequestBody WardDTO wardDTO) throws URISyntaxException {
        log.debug("REST request to save Ward : {}", wardDTO);
        if (wardDTO.getId() != null) {
            throw new BadRequestAlertException("A new ward cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WardDTO result = wardService.save(wardDTO);
        return ResponseEntity
            .created(new URI("/api/wards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wards/:id} : Updates an existing ward.
     *
     * @param id the id of the wardDTO to save.
     * @param wardDTO the wardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wardDTO,
     * or with status {@code 400 (Bad Request)} if the wardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wards/{id}")
    public ResponseEntity<WardDTO> updateWard(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WardDTO wardDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Ward : {}, {}", id, wardDTO);
        if (wardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WardDTO result = wardService.update(wardDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /wards/:id} : Partial updates given fields of an existing ward, field will ignore if it is null
     *
     * @param id the id of the wardDTO to save.
     * @param wardDTO the wardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wardDTO,
     * or with status {@code 400 (Bad Request)} if the wardDTO is not valid,
     * or with status {@code 404 (Not Found)} if the wardDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the wardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/wards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WardDTO> partialUpdateWard(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WardDTO wardDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ward partially : {}, {}", id, wardDTO);
        if (wardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WardDTO> result = wardService.partialUpdate(wardDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wardDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /wards} : get all the wards.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wards in body.
     */
    @GetMapping("/wards")
    public ResponseEntity<List<WardDTO>> getAllWards(
        WardCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Wards by criteria: {}", criteria);
        Page<WardDTO> page = wardQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wards/count} : count all the wards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/wards/count")
    public ResponseEntity<Long> countWards(WardCriteria criteria) {
        log.debug("REST request to count Wards by criteria: {}", criteria);
        return ResponseEntity.ok().body(wardQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wards/:id} : get the "id" ward.
     *
     * @param id the id of the wardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wards/{id}")
    public ResponseEntity<WardDTO> getWard(@PathVariable Long id) {
        log.debug("REST request to get Ward : {}", id);
        Optional<WardDTO> wardDTO = wardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wardDTO);
    }

    /**
     * {@code DELETE  /wards/:id} : delete the "id" ward.
     *
     * @param id the id of the wardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wards/{id}")
    public ResponseEntity<Void> deleteWard(@PathVariable Long id) {
        log.debug("REST request to delete Ward : {}", id);
        wardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
