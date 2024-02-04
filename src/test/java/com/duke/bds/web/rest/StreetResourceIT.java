package com.duke.bds.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.duke.bds.IntegrationTest;
import com.duke.bds.domain.District;
import com.duke.bds.domain.Street;
import com.duke.bds.domain.Ward;
import com.duke.bds.domain.enumeration.PostStatus;
import com.duke.bds.repository.StreetRepository;
import com.duke.bds.service.StreetService;
import com.duke.bds.service.criteria.StreetCriteria;
import com.duke.bds.service.dto.StreetDTO;
import com.duke.bds.service.mapper.StreetMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StreetResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StreetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final PostStatus DEFAULT_STATUS = PostStatus.WAITING;
    private static final PostStatus UPDATED_STATUS = PostStatus.VERIFIED;

    private static final String ENTITY_API_URL = "/api/streets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StreetRepository streetRepository;

    @Mock
    private StreetRepository streetRepositoryMock;

    @Autowired
    private StreetMapper streetMapper;

    @Mock
    private StreetService streetServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStreetMockMvc;

    private Street street;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Street createEntity(EntityManager em) {
        Street street = new Street().name(DEFAULT_NAME).status(DEFAULT_STATUS);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        street.setDistrict(district);
        return street;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Street createUpdatedEntity(EntityManager em) {
        Street street = new Street().name(UPDATED_NAME).status(UPDATED_STATUS);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createUpdatedEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        street.setDistrict(district);
        return street;
    }

    @BeforeEach
    public void initTest() {
        street = createEntity(em);
    }

    @Test
    @Transactional
    void createStreet() throws Exception {
        int databaseSizeBeforeCreate = streetRepository.findAll().size();
        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);
        restStreetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(streetDTO)))
            .andExpect(status().isCreated());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeCreate + 1);
        Street testStreet = streetList.get(streetList.size() - 1);
        assertThat(testStreet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStreet.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createStreetWithExistingId() throws Exception {
        // Create the Street with an existing ID
        street.setId(1L);
        StreetDTO streetDTO = streetMapper.toDto(street);

        int databaseSizeBeforeCreate = streetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStreetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(streetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = streetRepository.findAll().size();
        // set the field null
        street.setName(null);

        // Create the Street, which fails.
        StreetDTO streetDTO = streetMapper.toDto(street);

        restStreetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(streetDTO)))
            .andExpect(status().isBadRequest());

        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStreets() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList
        restStreetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(street.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStreetsWithEagerRelationshipsIsEnabled() throws Exception {
        when(streetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStreetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(streetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStreetsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(streetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStreetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(streetRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getStreet() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get the street
        restStreetMockMvc
            .perform(get(ENTITY_API_URL_ID, street.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(street.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getStreetsByIdFiltering() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        Long id = street.getId();

        defaultStreetShouldBeFound("id.equals=" + id);
        defaultStreetShouldNotBeFound("id.notEquals=" + id);

        defaultStreetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStreetShouldNotBeFound("id.greaterThan=" + id);

        defaultStreetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStreetShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStreetsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where name equals to DEFAULT_NAME
        defaultStreetShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the streetList where name equals to UPDATED_NAME
        defaultStreetShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStreetsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStreetShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the streetList where name equals to UPDATED_NAME
        defaultStreetShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStreetsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where name is not null
        defaultStreetShouldBeFound("name.specified=true");

        // Get all the streetList where name is null
        defaultStreetShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllStreetsByNameContainsSomething() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where name contains DEFAULT_NAME
        defaultStreetShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the streetList where name contains UPDATED_NAME
        defaultStreetShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStreetsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where name does not contain DEFAULT_NAME
        defaultStreetShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the streetList where name does not contain UPDATED_NAME
        defaultStreetShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStreetsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where status equals to DEFAULT_STATUS
        defaultStreetShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the streetList where status equals to UPDATED_STATUS
        defaultStreetShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStreetsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultStreetShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the streetList where status equals to UPDATED_STATUS
        defaultStreetShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllStreetsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where status is not null
        defaultStreetShouldBeFound("status.specified=true");

        // Get all the streetList where status is null
        defaultStreetShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllStreetsByWardIsEqualToSomething() throws Exception {
        Ward ward;
        if (TestUtil.findAll(em, Ward.class).isEmpty()) {
            streetRepository.saveAndFlush(street);
            ward = WardResourceIT.createEntity(em);
        } else {
            ward = TestUtil.findAll(em, Ward.class).get(0);
        }
        em.persist(ward);
        em.flush();
        street.setWard(ward);
        streetRepository.saveAndFlush(street);
        Long wardId = ward.getId();

        // Get all the streetList where ward equals to wardId
        defaultStreetShouldBeFound("wardId.equals=" + wardId);

        // Get all the streetList where ward equals to (wardId + 1)
        defaultStreetShouldNotBeFound("wardId.equals=" + (wardId + 1));
    }

    @Test
    @Transactional
    void getAllStreetsByDistrictIsEqualToSomething() throws Exception {
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            streetRepository.saveAndFlush(street);
            district = DistrictResourceIT.createEntity(em);
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        em.persist(district);
        em.flush();
        street.setDistrict(district);
        streetRepository.saveAndFlush(street);
        Long districtId = district.getId();

        // Get all the streetList where district equals to districtId
        defaultStreetShouldBeFound("districtId.equals=" + districtId);

        // Get all the streetList where district equals to (districtId + 1)
        defaultStreetShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStreetShouldBeFound(String filter) throws Exception {
        restStreetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(street.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restStreetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStreetShouldNotBeFound(String filter) throws Exception {
        restStreetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStreetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStreet() throws Exception {
        // Get the street
        restStreetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStreet() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        int databaseSizeBeforeUpdate = streetRepository.findAll().size();

        // Update the street
        Street updatedStreet = streetRepository.findById(street.getId()).get();
        // Disconnect from session so that the updates on updatedStreet are not directly saved in db
        em.detach(updatedStreet);
        updatedStreet.name(UPDATED_NAME).status(UPDATED_STATUS);
        StreetDTO streetDTO = streetMapper.toDto(updatedStreet);

        restStreetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, streetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(streetDTO))
            )
            .andExpect(status().isOk());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
        Street testStreet = streetList.get(streetList.size() - 1);
        assertThat(testStreet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStreet.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();
        street.setId(count.incrementAndGet());

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStreetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, streetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(streetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();
        street.setId(count.incrementAndGet());

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStreetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(streetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();
        street.setId(count.incrementAndGet());

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStreetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(streetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStreetWithPatch() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        int databaseSizeBeforeUpdate = streetRepository.findAll().size();

        // Update the street using partial update
        Street partialUpdatedStreet = new Street();
        partialUpdatedStreet.setId(street.getId());

        restStreetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStreet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStreet))
            )
            .andExpect(status().isOk());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
        Street testStreet = streetList.get(streetList.size() - 1);
        assertThat(testStreet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStreet.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateStreetWithPatch() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        int databaseSizeBeforeUpdate = streetRepository.findAll().size();

        // Update the street using partial update
        Street partialUpdatedStreet = new Street();
        partialUpdatedStreet.setId(street.getId());

        partialUpdatedStreet.name(UPDATED_NAME).status(UPDATED_STATUS);

        restStreetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStreet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStreet))
            )
            .andExpect(status().isOk());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
        Street testStreet = streetList.get(streetList.size() - 1);
        assertThat(testStreet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStreet.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();
        street.setId(count.incrementAndGet());

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStreetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, streetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(streetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();
        street.setId(count.incrementAndGet());

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStreetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(streetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();
        street.setId(count.incrementAndGet());

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStreetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(streetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStreet() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        int databaseSizeBeforeDelete = streetRepository.findAll().size();

        // Delete the street
        restStreetMockMvc
            .perform(delete(ENTITY_API_URL_ID, street.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
