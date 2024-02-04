package com.duke.bds.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.duke.bds.IntegrationTest;
import com.duke.bds.domain.PostType;
import com.duke.bds.repository.PostTypeRepository;
import com.duke.bds.service.criteria.PostTypeCriteria;
import com.duke.bds.service.dto.PostTypeDTO;
import com.duke.bds.service.mapper.PostTypeMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PostTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PostTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/post-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PostTypeRepository postTypeRepository;

    @Autowired
    private PostTypeMapper postTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostTypeMockMvc;

    private PostType postType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostType createEntity(EntityManager em) {
        PostType postType = new PostType().name(DEFAULT_NAME).icon(DEFAULT_ICON).description(DEFAULT_DESCRIPTION);
        return postType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostType createUpdatedEntity(EntityManager em) {
        PostType postType = new PostType().name(UPDATED_NAME).icon(UPDATED_ICON).description(UPDATED_DESCRIPTION);
        return postType;
    }

    @BeforeEach
    public void initTest() {
        postType = createEntity(em);
    }

    @Test
    @Transactional
    void createPostType() throws Exception {
        int databaseSizeBeforeCreate = postTypeRepository.findAll().size();
        // Create the PostType
        PostTypeDTO postTypeDTO = postTypeMapper.toDto(postType);
        restPostTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PostType in the database
        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PostType testPostType = postTypeList.get(postTypeList.size() - 1);
        assertThat(testPostType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPostType.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testPostType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createPostTypeWithExistingId() throws Exception {
        // Create the PostType with an existing ID
        postType.setId(1L);
        PostTypeDTO postTypeDTO = postTypeMapper.toDto(postType);

        int databaseSizeBeforeCreate = postTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostType in the database
        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = postTypeRepository.findAll().size();
        // set the field null
        postType.setName(null);

        // Create the PostType, which fails.
        PostTypeDTO postTypeDTO = postTypeMapper.toDto(postType);

        restPostTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPostTypes() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        // Get all the postTypeList
        restPostTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    void getPostType() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        // Get the postType
        restPostTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, postType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    void getPostTypesByIdFiltering() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        Long id = postType.getId();

        defaultPostTypeShouldBeFound("id.equals=" + id);
        defaultPostTypeShouldNotBeFound("id.notEquals=" + id);

        defaultPostTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPostTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultPostTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPostTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPostTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        // Get all the postTypeList where name equals to DEFAULT_NAME
        defaultPostTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the postTypeList where name equals to UPDATED_NAME
        defaultPostTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPostTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        // Get all the postTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPostTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the postTypeList where name equals to UPDATED_NAME
        defaultPostTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPostTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        // Get all the postTypeList where name is not null
        defaultPostTypeShouldBeFound("name.specified=true");

        // Get all the postTypeList where name is null
        defaultPostTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllPostTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        // Get all the postTypeList where name contains DEFAULT_NAME
        defaultPostTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the postTypeList where name contains UPDATED_NAME
        defaultPostTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPostTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        // Get all the postTypeList where name does not contain DEFAULT_NAME
        defaultPostTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the postTypeList where name does not contain UPDATED_NAME
        defaultPostTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPostTypesByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        // Get all the postTypeList where icon equals to DEFAULT_ICON
        defaultPostTypeShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the postTypeList where icon equals to UPDATED_ICON
        defaultPostTypeShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllPostTypesByIconIsInShouldWork() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        // Get all the postTypeList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultPostTypeShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the postTypeList where icon equals to UPDATED_ICON
        defaultPostTypeShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllPostTypesByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        // Get all the postTypeList where icon is not null
        defaultPostTypeShouldBeFound("icon.specified=true");

        // Get all the postTypeList where icon is null
        defaultPostTypeShouldNotBeFound("icon.specified=false");
    }

    @Test
    @Transactional
    void getAllPostTypesByIconContainsSomething() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        // Get all the postTypeList where icon contains DEFAULT_ICON
        defaultPostTypeShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the postTypeList where icon contains UPDATED_ICON
        defaultPostTypeShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllPostTypesByIconNotContainsSomething() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        // Get all the postTypeList where icon does not contain DEFAULT_ICON
        defaultPostTypeShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the postTypeList where icon does not contain UPDATED_ICON
        defaultPostTypeShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostTypeShouldBeFound(String filter) throws Exception {
        restPostTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));

        // Check, that the count call also returns 1
        restPostTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostTypeShouldNotBeFound(String filter) throws Exception {
        restPostTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPostType() throws Exception {
        // Get the postType
        restPostTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPostType() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        int databaseSizeBeforeUpdate = postTypeRepository.findAll().size();

        // Update the postType
        PostType updatedPostType = postTypeRepository.findById(postType.getId()).get();
        // Disconnect from session so that the updates on updatedPostType are not directly saved in db
        em.detach(updatedPostType);
        updatedPostType.name(UPDATED_NAME).icon(UPDATED_ICON).description(UPDATED_DESCRIPTION);
        PostTypeDTO postTypeDTO = postTypeMapper.toDto(updatedPostType);

        restPostTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PostType in the database
        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeUpdate);
        PostType testPostType = postTypeList.get(postTypeList.size() - 1);
        assertThat(testPostType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPostType.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testPostType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingPostType() throws Exception {
        int databaseSizeBeforeUpdate = postTypeRepository.findAll().size();
        postType.setId(count.incrementAndGet());

        // Create the PostType
        PostTypeDTO postTypeDTO = postTypeMapper.toDto(postType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostType in the database
        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPostType() throws Exception {
        int databaseSizeBeforeUpdate = postTypeRepository.findAll().size();
        postType.setId(count.incrementAndGet());

        // Create the PostType
        PostTypeDTO postTypeDTO = postTypeMapper.toDto(postType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostType in the database
        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPostType() throws Exception {
        int databaseSizeBeforeUpdate = postTypeRepository.findAll().size();
        postType.setId(count.incrementAndGet());

        // Create the PostType
        PostTypeDTO postTypeDTO = postTypeMapper.toDto(postType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PostType in the database
        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePostTypeWithPatch() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        int databaseSizeBeforeUpdate = postTypeRepository.findAll().size();

        // Update the postType using partial update
        PostType partialUpdatedPostType = new PostType();
        partialUpdatedPostType.setId(postType.getId());

        partialUpdatedPostType.name(UPDATED_NAME);

        restPostTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostType))
            )
            .andExpect(status().isOk());

        // Validate the PostType in the database
        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeUpdate);
        PostType testPostType = postTypeList.get(postTypeList.size() - 1);
        assertThat(testPostType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPostType.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testPostType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdatePostTypeWithPatch() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        int databaseSizeBeforeUpdate = postTypeRepository.findAll().size();

        // Update the postType using partial update
        PostType partialUpdatedPostType = new PostType();
        partialUpdatedPostType.setId(postType.getId());

        partialUpdatedPostType.name(UPDATED_NAME).icon(UPDATED_ICON).description(UPDATED_DESCRIPTION);

        restPostTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostType))
            )
            .andExpect(status().isOk());

        // Validate the PostType in the database
        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeUpdate);
        PostType testPostType = postTypeList.get(postTypeList.size() - 1);
        assertThat(testPostType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPostType.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testPostType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingPostType() throws Exception {
        int databaseSizeBeforeUpdate = postTypeRepository.findAll().size();
        postType.setId(count.incrementAndGet());

        // Create the PostType
        PostTypeDTO postTypeDTO = postTypeMapper.toDto(postType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostType in the database
        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPostType() throws Exception {
        int databaseSizeBeforeUpdate = postTypeRepository.findAll().size();
        postType.setId(count.incrementAndGet());

        // Create the PostType
        PostTypeDTO postTypeDTO = postTypeMapper.toDto(postType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostType in the database
        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPostType() throws Exception {
        int databaseSizeBeforeUpdate = postTypeRepository.findAll().size();
        postType.setId(count.incrementAndGet());

        // Create the PostType
        PostTypeDTO postTypeDTO = postTypeMapper.toDto(postType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(postTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PostType in the database
        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePostType() throws Exception {
        // Initialize the database
        postTypeRepository.saveAndFlush(postType);

        int databaseSizeBeforeDelete = postTypeRepository.findAll().size();

        // Delete the postType
        restPostTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, postType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostType> postTypeList = postTypeRepository.findAll();
        assertThat(postTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
