package com.duke.bds.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.duke.bds.IntegrationTest;
import com.duke.bds.domain.District;
import com.duke.bds.domain.Ward;
import com.duke.bds.repository.WardRepository;
import com.duke.bds.service.dto.WardDTO;
import com.duke.bds.service.mapper.WardMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WardResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private WardMapper wardMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWardMockMvc;

    private Ward ward;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ward createEntity(EntityManager em) {
        Ward ward = new Ward().name(DEFAULT_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        ward.setDistrict(district);
        return ward;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ward createUpdatedEntity(EntityManager em) {
        Ward ward = new Ward().name(UPDATED_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createUpdatedEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        ward.setDistrict(district);
        return ward;
    }

    @BeforeEach
    public void initTest() {
        ward = createEntity(em);
    }

    @Test
    @Transactional
    void createWard() throws Exception {
        int databaseSizeBeforeCreate = wardRepository.findAll().size();
        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);
        restWardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wardDTO)))
            .andExpect(status().isCreated());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeCreate + 1);
        Ward testWard = wardList.get(wardList.size() - 1);
        assertThat(testWard.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createWardWithExistingId() throws Exception {
        // Create the Ward with an existing ID
        ward.setId(1L);
        WardDTO wardDTO = wardMapper.toDto(ward);

        int databaseSizeBeforeCreate = wardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = wardRepository.findAll().size();
        // set the field null
        ward.setName(null);

        // Create the Ward, which fails.
        WardDTO wardDTO = wardMapper.toDto(ward);

        restWardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wardDTO)))
            .andExpect(status().isBadRequest());

        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWards() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList
        restWardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ward.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getWard() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get the ward
        restWardMockMvc
            .perform(get(ENTITY_API_URL_ID, ward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ward.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingWard() throws Exception {
        // Get the ward
        restWardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWard() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        int databaseSizeBeforeUpdate = wardRepository.findAll().size();

        // Update the ward
        Ward updatedWard = wardRepository.findById(ward.getId()).get();
        // Disconnect from session so that the updates on updatedWard are not directly saved in db
        em.detach(updatedWard);
        updatedWard.name(UPDATED_NAME);
        WardDTO wardDTO = wardMapper.toDto(updatedWard);

        restWardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wardDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeUpdate);
        Ward testWard = wardList.get(wardList.size() - 1);
        assertThat(testWard.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingWard() throws Exception {
        int databaseSizeBeforeUpdate = wardRepository.findAll().size();
        ward.setId(count.incrementAndGet());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWard() throws Exception {
        int databaseSizeBeforeUpdate = wardRepository.findAll().size();
        ward.setId(count.incrementAndGet());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWard() throws Exception {
        int databaseSizeBeforeUpdate = wardRepository.findAll().size();
        ward.setId(count.incrementAndGet());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWardWithPatch() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        int databaseSizeBeforeUpdate = wardRepository.findAll().size();

        // Update the ward using partial update
        Ward partialUpdatedWard = new Ward();
        partialUpdatedWard.setId(ward.getId());

        partialUpdatedWard.name(UPDATED_NAME);

        restWardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWard))
            )
            .andExpect(status().isOk());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeUpdate);
        Ward testWard = wardList.get(wardList.size() - 1);
        assertThat(testWard.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateWardWithPatch() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        int databaseSizeBeforeUpdate = wardRepository.findAll().size();

        // Update the ward using partial update
        Ward partialUpdatedWard = new Ward();
        partialUpdatedWard.setId(ward.getId());

        partialUpdatedWard.name(UPDATED_NAME);

        restWardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWard))
            )
            .andExpect(status().isOk());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeUpdate);
        Ward testWard = wardList.get(wardList.size() - 1);
        assertThat(testWard.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingWard() throws Exception {
        int databaseSizeBeforeUpdate = wardRepository.findAll().size();
        ward.setId(count.incrementAndGet());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wardDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWard() throws Exception {
        int databaseSizeBeforeUpdate = wardRepository.findAll().size();
        ward.setId(count.incrementAndGet());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWard() throws Exception {
        int databaseSizeBeforeUpdate = wardRepository.findAll().size();
        ward.setId(count.incrementAndGet());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(wardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWard() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        int databaseSizeBeforeDelete = wardRepository.findAll().size();

        // Delete the ward
        restWardMockMvc
            .perform(delete(ENTITY_API_URL_ID, ward.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
