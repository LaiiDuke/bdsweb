package com.duke.bds.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.duke.bds.IntegrationTest;
import com.duke.bds.domain.Category;
import com.duke.bds.domain.District;
import com.duke.bds.domain.Image;
import com.duke.bds.domain.Post;
import com.duke.bds.domain.PostType;
import com.duke.bds.domain.Province;
import com.duke.bds.domain.Street;
import com.duke.bds.domain.User;
import com.duke.bds.domain.Ward;
import com.duke.bds.domain.enumeration.PostStatus;
import com.duke.bds.repository.PostRepository;
import com.duke.bds.service.PostService;
import com.duke.bds.service.criteria.PostCriteria;
import com.duke.bds.service.dto.PostDTO;
import com.duke.bds.service.mapper.PostMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PostResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PostResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;
    private static final Long SMALLER_PRICE = 1L - 1L;

    private static final Long DEFAULT_SQUARE = 1L;
    private static final Long UPDATED_SQUARE = 2L;
    private static final Long SMALLER_SQUARE = 1L - 1L;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_MAPS_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_MAPS_LOCATION = "BBBBBBBBBB";

    private static final Double DEFAULT_WIDTH = 1D;
    private static final Double UPDATED_WIDTH = 2D;
    private static final Double SMALLER_WIDTH = 1D - 1D;

    private static final Double DEFAULT_LENGTH = 1D;
    private static final Double UPDATED_LENGTH = 2D;
    private static final Double SMALLER_LENGTH = 1D - 1D;

    private static final String DEFAULT_DIRECTION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTION = "BBBBBBBBBB";

    private static final String DEFAULT_DISTANCE = "AAAAAAAAAA";
    private static final String UPDATED_DISTANCE = "BBBBBBBBBB";

    private static final String DEFAULT_LEGAL = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMBER_OF_FLOORS = 1L;
    private static final Long UPDATED_NUMBER_OF_FLOORS = 2L;
    private static final Long SMALLER_NUMBER_OF_FLOORS = 1L - 1L;

    private static final Long DEFAULT_NUMBER_OF_BEDROOM = 1L;
    private static final Long UPDATED_NUMBER_OF_BEDROOM = 2L;
    private static final Long SMALLER_NUMBER_OF_BEDROOM = 1L - 1L;

    private static final Boolean DEFAULT_HAS_KITCHEN = false;
    private static final Boolean UPDATED_HAS_KITCHEN = true;

    private static final Boolean DEFAULT_HAS_DINNING_ROOM = false;
    private static final Boolean UPDATED_HAS_DINNING_ROOM = true;

    private static final Boolean DEFAULT_HAS_ROOFTOP = false;
    private static final Boolean UPDATED_HAS_ROOFTOP = true;

    private static final Boolean DEFAULT_HAS_GARAGE = false;
    private static final Boolean UPDATED_HAS_GARAGE = true;

    private static final Boolean DEFAULT_IS_VIP = false;
    private static final Boolean UPDATED_IS_VIP = true;

    private static final LocalDate DEFAULT_POSTING_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POSTING_TIME = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_POSTING_TIME = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_EXPIRED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRED_TIME = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EXPIRED_TIME = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_BROKERAGE_FEES = 1D;
    private static final Double UPDATED_BROKERAGE_FEES = 2D;
    private static final Double SMALLER_BROKERAGE_FEES = 1D - 1D;

    private static final PostStatus DEFAULT_STATUS = PostStatus.WAITING;
    private static final PostStatus UPDATED_STATUS = PostStatus.VERIFIED;

    private static final Double DEFAULT_STAR = 1D;
    private static final Double UPDATED_STAR = 2D;
    private static final Double SMALLER_STAR = 1D - 1D;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/posts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PostRepository postRepository;

    @Mock
    private PostRepository postRepositoryMock;

    @Autowired
    private PostMapper postMapper;

    @Mock
    private PostService postServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostMockMvc;

    private Post post;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createEntity(EntityManager em) {
        Post post = new Post()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .price(DEFAULT_PRICE)
            .square(DEFAULT_SQUARE)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .googleMapsLocation(DEFAULT_GOOGLE_MAPS_LOCATION)
            .width(DEFAULT_WIDTH)
            .length(DEFAULT_LENGTH)
            .direction(DEFAULT_DIRECTION)
            .distance(DEFAULT_DISTANCE)
            .legal(DEFAULT_LEGAL)
            .numberOfFloors(DEFAULT_NUMBER_OF_FLOORS)
            .numberOfBedroom(DEFAULT_NUMBER_OF_BEDROOM)
            .hasKitchen(DEFAULT_HAS_KITCHEN)
            .hasDinningRoom(DEFAULT_HAS_DINNING_ROOM)
            .hasRooftop(DEFAULT_HAS_ROOFTOP)
            .hasGarage(DEFAULT_HAS_GARAGE)
            .isVip(DEFAULT_IS_VIP)
            .postingTime(DEFAULT_POSTING_TIME)
            .expiredTime(DEFAULT_EXPIRED_TIME)
            .brokerageFees(DEFAULT_BROKERAGE_FEES)
            .status(DEFAULT_STATUS)
            .star(DEFAULT_STAR)
            .hash(DEFAULT_HASH);
        // Add required entity
        PostType postType;
        if (TestUtil.findAll(em, PostType.class).isEmpty()) {
            postType = PostTypeResourceIT.createEntity(em);
            em.persist(postType);
            em.flush();
        } else {
            postType = TestUtil.findAll(em, PostType.class).get(0);
        }
        post.setType(postType);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        post.setCategory(category);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        post.setUser(user);
        // Add required entity
        Province province;
        if (TestUtil.findAll(em, Province.class).isEmpty()) {
            province = ProvinceResourceIT.createEntity(em);
            em.persist(province);
            em.flush();
        } else {
            province = TestUtil.findAll(em, Province.class).get(0);
        }
        post.setProvince(province);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        post.setDistrict(district);
        // Add required entity
        Street street;
        if (TestUtil.findAll(em, Street.class).isEmpty()) {
            street = StreetResourceIT.createEntity(em);
            em.persist(street);
            em.flush();
        } else {
            street = TestUtil.findAll(em, Street.class).get(0);
        }
        post.setStreet(street);
        return post;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createUpdatedEntity(EntityManager em) {
        Post post = new Post()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .price(UPDATED_PRICE)
            .square(UPDATED_SQUARE)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .googleMapsLocation(UPDATED_GOOGLE_MAPS_LOCATION)
            .width(UPDATED_WIDTH)
            .length(UPDATED_LENGTH)
            .direction(UPDATED_DIRECTION)
            .distance(UPDATED_DISTANCE)
            .legal(UPDATED_LEGAL)
            .numberOfFloors(UPDATED_NUMBER_OF_FLOORS)
            .numberOfBedroom(UPDATED_NUMBER_OF_BEDROOM)
            .hasKitchen(UPDATED_HAS_KITCHEN)
            .hasDinningRoom(UPDATED_HAS_DINNING_ROOM)
            .hasRooftop(UPDATED_HAS_ROOFTOP)
            .hasGarage(UPDATED_HAS_GARAGE)
            .isVip(UPDATED_IS_VIP)
            .postingTime(UPDATED_POSTING_TIME)
            .expiredTime(UPDATED_EXPIRED_TIME)
            .brokerageFees(UPDATED_BROKERAGE_FEES)
            .status(UPDATED_STATUS)
            .star(UPDATED_STAR)
            .hash(UPDATED_HASH);
        // Add required entity
        PostType postType;
        if (TestUtil.findAll(em, PostType.class).isEmpty()) {
            postType = PostTypeResourceIT.createUpdatedEntity(em);
            em.persist(postType);
            em.flush();
        } else {
            postType = TestUtil.findAll(em, PostType.class).get(0);
        }
        post.setType(postType);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createUpdatedEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        post.setCategory(category);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        post.setUser(user);
        // Add required entity
        Province province;
        if (TestUtil.findAll(em, Province.class).isEmpty()) {
            province = ProvinceResourceIT.createUpdatedEntity(em);
            em.persist(province);
            em.flush();
        } else {
            province = TestUtil.findAll(em, Province.class).get(0);
        }
        post.setProvince(province);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createUpdatedEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        post.setDistrict(district);
        // Add required entity
        Street street;
        if (TestUtil.findAll(em, Street.class).isEmpty()) {
            street = StreetResourceIT.createUpdatedEntity(em);
            em.persist(street);
            em.flush();
        } else {
            street = TestUtil.findAll(em, Street.class).get(0);
        }
        post.setStreet(street);
        return post;
    }

    @BeforeEach
    public void initTest() {
        post = createEntity(em);
    }

    @Test
    @Transactional
    void createPost() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();
        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);
        restPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isCreated());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate + 1);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPost.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPost.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPost.getSquare()).isEqualTo(DEFAULT_SQUARE);
        assertThat(testPost.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPost.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPost.getGoogleMapsLocation()).isEqualTo(DEFAULT_GOOGLE_MAPS_LOCATION);
        assertThat(testPost.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testPost.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testPost.getDirection()).isEqualTo(DEFAULT_DIRECTION);
        assertThat(testPost.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testPost.getLegal()).isEqualTo(DEFAULT_LEGAL);
        assertThat(testPost.getNumberOfFloors()).isEqualTo(DEFAULT_NUMBER_OF_FLOORS);
        assertThat(testPost.getNumberOfBedroom()).isEqualTo(DEFAULT_NUMBER_OF_BEDROOM);
        assertThat(testPost.getHasKitchen()).isEqualTo(DEFAULT_HAS_KITCHEN);
        assertThat(testPost.getHasDinningRoom()).isEqualTo(DEFAULT_HAS_DINNING_ROOM);
        assertThat(testPost.getHasRooftop()).isEqualTo(DEFAULT_HAS_ROOFTOP);
        assertThat(testPost.getHasGarage()).isEqualTo(DEFAULT_HAS_GARAGE);
        assertThat(testPost.getIsVip()).isEqualTo(DEFAULT_IS_VIP);
        assertThat(testPost.getPostingTime()).isEqualTo(DEFAULT_POSTING_TIME);
        assertThat(testPost.getExpiredTime()).isEqualTo(DEFAULT_EXPIRED_TIME);
        assertThat(testPost.getBrokerageFees()).isEqualTo(DEFAULT_BROKERAGE_FEES);
        assertThat(testPost.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPost.getStar()).isEqualTo(DEFAULT_STAR);
        assertThat(testPost.getHash()).isEqualTo(DEFAULT_HASH);
    }

    @Test
    @Transactional
    void createPostWithExistingId() throws Exception {
        // Create the Post with an existing ID
        post.setId(1L);
        PostDTO postDTO = postMapper.toDto(post);

        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setTitle(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().size();
        // set the field null
        post.setPrice(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        restPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPosts() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList
        restPostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].square").value(hasItem(DEFAULT_SQUARE.intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].googleMapsLocation").value(hasItem(DEFAULT_GOOGLE_MAPS_LOCATION)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].direction").value(hasItem(DEFAULT_DIRECTION)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE)))
            .andExpect(jsonPath("$.[*].legal").value(hasItem(DEFAULT_LEGAL)))
            .andExpect(jsonPath("$.[*].numberOfFloors").value(hasItem(DEFAULT_NUMBER_OF_FLOORS.intValue())))
            .andExpect(jsonPath("$.[*].numberOfBedroom").value(hasItem(DEFAULT_NUMBER_OF_BEDROOM.intValue())))
            .andExpect(jsonPath("$.[*].hasKitchen").value(hasItem(DEFAULT_HAS_KITCHEN.booleanValue())))
            .andExpect(jsonPath("$.[*].hasDinningRoom").value(hasItem(DEFAULT_HAS_DINNING_ROOM.booleanValue())))
            .andExpect(jsonPath("$.[*].hasRooftop").value(hasItem(DEFAULT_HAS_ROOFTOP.booleanValue())))
            .andExpect(jsonPath("$.[*].hasGarage").value(hasItem(DEFAULT_HAS_GARAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].isVip").value(hasItem(DEFAULT_IS_VIP.booleanValue())))
            .andExpect(jsonPath("$.[*].postingTime").value(hasItem(DEFAULT_POSTING_TIME.toString())))
            .andExpect(jsonPath("$.[*].expiredTime").value(hasItem(DEFAULT_EXPIRED_TIME.toString())))
            .andExpect(jsonPath("$.[*].brokerageFees").value(hasItem(DEFAULT_BROKERAGE_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].star").value(hasItem(DEFAULT_STAR.doubleValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostsWithEagerRelationshipsIsEnabled() throws Exception {
        when(postServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(postServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(postServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(postRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get the post
        restPostMockMvc
            .perform(get(ENTITY_API_URL_ID, post.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(post.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.square").value(DEFAULT_SQUARE.intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.googleMapsLocation").value(DEFAULT_GOOGLE_MAPS_LOCATION))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.doubleValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.direction").value(DEFAULT_DIRECTION))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE))
            .andExpect(jsonPath("$.legal").value(DEFAULT_LEGAL))
            .andExpect(jsonPath("$.numberOfFloors").value(DEFAULT_NUMBER_OF_FLOORS.intValue()))
            .andExpect(jsonPath("$.numberOfBedroom").value(DEFAULT_NUMBER_OF_BEDROOM.intValue()))
            .andExpect(jsonPath("$.hasKitchen").value(DEFAULT_HAS_KITCHEN.booleanValue()))
            .andExpect(jsonPath("$.hasDinningRoom").value(DEFAULT_HAS_DINNING_ROOM.booleanValue()))
            .andExpect(jsonPath("$.hasRooftop").value(DEFAULT_HAS_ROOFTOP.booleanValue()))
            .andExpect(jsonPath("$.hasGarage").value(DEFAULT_HAS_GARAGE.booleanValue()))
            .andExpect(jsonPath("$.isVip").value(DEFAULT_IS_VIP.booleanValue()))
            .andExpect(jsonPath("$.postingTime").value(DEFAULT_POSTING_TIME.toString()))
            .andExpect(jsonPath("$.expiredTime").value(DEFAULT_EXPIRED_TIME.toString()))
            .andExpect(jsonPath("$.brokerageFees").value(DEFAULT_BROKERAGE_FEES.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.star").value(DEFAULT_STAR.doubleValue()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH));
    }

    @Test
    @Transactional
    void getPostsByIdFiltering() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        Long id = post.getId();

        defaultPostShouldBeFound("id.equals=" + id);
        defaultPostShouldNotBeFound("id.notEquals=" + id);

        defaultPostShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPostShouldNotBeFound("id.greaterThan=" + id);

        defaultPostShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPostShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPostsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title equals to DEFAULT_TITLE
        defaultPostShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the postList where title equals to UPDATED_TITLE
        defaultPostShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllPostsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultPostShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the postList where title equals to UPDATED_TITLE
        defaultPostShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllPostsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title is not null
        defaultPostShouldBeFound("title.specified=true");

        // Get all the postList where title is null
        defaultPostShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByTitleContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title contains DEFAULT_TITLE
        defaultPostShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the postList where title contains UPDATED_TITLE
        defaultPostShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllPostsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title does not contain DEFAULT_TITLE
        defaultPostShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the postList where title does not contain UPDATED_TITLE
        defaultPostShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllPostsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price equals to DEFAULT_PRICE
        defaultPostShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the postList where price equals to UPDATED_PRICE
        defaultPostShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllPostsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultPostShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the postList where price equals to UPDATED_PRICE
        defaultPostShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllPostsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price is not null
        defaultPostShouldBeFound("price.specified=true");

        // Get all the postList where price is null
        defaultPostShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price is greater than or equal to DEFAULT_PRICE
        defaultPostShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the postList where price is greater than or equal to UPDATED_PRICE
        defaultPostShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllPostsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price is less than or equal to DEFAULT_PRICE
        defaultPostShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the postList where price is less than or equal to SMALLER_PRICE
        defaultPostShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllPostsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price is less than DEFAULT_PRICE
        defaultPostShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the postList where price is less than UPDATED_PRICE
        defaultPostShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllPostsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where price is greater than DEFAULT_PRICE
        defaultPostShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the postList where price is greater than SMALLER_PRICE
        defaultPostShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllPostsBySquareIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where square equals to DEFAULT_SQUARE
        defaultPostShouldBeFound("square.equals=" + DEFAULT_SQUARE);

        // Get all the postList where square equals to UPDATED_SQUARE
        defaultPostShouldNotBeFound("square.equals=" + UPDATED_SQUARE);
    }

    @Test
    @Transactional
    void getAllPostsBySquareIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where square in DEFAULT_SQUARE or UPDATED_SQUARE
        defaultPostShouldBeFound("square.in=" + DEFAULT_SQUARE + "," + UPDATED_SQUARE);

        // Get all the postList where square equals to UPDATED_SQUARE
        defaultPostShouldNotBeFound("square.in=" + UPDATED_SQUARE);
    }

    @Test
    @Transactional
    void getAllPostsBySquareIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where square is not null
        defaultPostShouldBeFound("square.specified=true");

        // Get all the postList where square is null
        defaultPostShouldNotBeFound("square.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsBySquareIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where square is greater than or equal to DEFAULT_SQUARE
        defaultPostShouldBeFound("square.greaterThanOrEqual=" + DEFAULT_SQUARE);

        // Get all the postList where square is greater than or equal to UPDATED_SQUARE
        defaultPostShouldNotBeFound("square.greaterThanOrEqual=" + UPDATED_SQUARE);
    }

    @Test
    @Transactional
    void getAllPostsBySquareIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where square is less than or equal to DEFAULT_SQUARE
        defaultPostShouldBeFound("square.lessThanOrEqual=" + DEFAULT_SQUARE);

        // Get all the postList where square is less than or equal to SMALLER_SQUARE
        defaultPostShouldNotBeFound("square.lessThanOrEqual=" + SMALLER_SQUARE);
    }

    @Test
    @Transactional
    void getAllPostsBySquareIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where square is less than DEFAULT_SQUARE
        defaultPostShouldNotBeFound("square.lessThan=" + DEFAULT_SQUARE);

        // Get all the postList where square is less than UPDATED_SQUARE
        defaultPostShouldBeFound("square.lessThan=" + UPDATED_SQUARE);
    }

    @Test
    @Transactional
    void getAllPostsBySquareIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where square is greater than DEFAULT_SQUARE
        defaultPostShouldNotBeFound("square.greaterThan=" + DEFAULT_SQUARE);

        // Get all the postList where square is greater than SMALLER_SQUARE
        defaultPostShouldBeFound("square.greaterThan=" + SMALLER_SQUARE);
    }

    @Test
    @Transactional
    void getAllPostsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where address equals to DEFAULT_ADDRESS
        defaultPostShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the postList where address equals to UPDATED_ADDRESS
        defaultPostShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPostsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultPostShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the postList where address equals to UPDATED_ADDRESS
        defaultPostShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPostsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where address is not null
        defaultPostShouldBeFound("address.specified=true");

        // Get all the postList where address is null
        defaultPostShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByAddressContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where address contains DEFAULT_ADDRESS
        defaultPostShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the postList where address contains UPDATED_ADDRESS
        defaultPostShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPostsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where address does not contain DEFAULT_ADDRESS
        defaultPostShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the postList where address does not contain UPDATED_ADDRESS
        defaultPostShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPostsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where phone equals to DEFAULT_PHONE
        defaultPostShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the postList where phone equals to UPDATED_PHONE
        defaultPostShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllPostsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultPostShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the postList where phone equals to UPDATED_PHONE
        defaultPostShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllPostsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where phone is not null
        defaultPostShouldBeFound("phone.specified=true");

        // Get all the postList where phone is null
        defaultPostShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where phone contains DEFAULT_PHONE
        defaultPostShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the postList where phone contains UPDATED_PHONE
        defaultPostShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllPostsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where phone does not contain DEFAULT_PHONE
        defaultPostShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the postList where phone does not contain UPDATED_PHONE
        defaultPostShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllPostsByGoogleMapsLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where googleMapsLocation equals to DEFAULT_GOOGLE_MAPS_LOCATION
        defaultPostShouldBeFound("googleMapsLocation.equals=" + DEFAULT_GOOGLE_MAPS_LOCATION);

        // Get all the postList where googleMapsLocation equals to UPDATED_GOOGLE_MAPS_LOCATION
        defaultPostShouldNotBeFound("googleMapsLocation.equals=" + UPDATED_GOOGLE_MAPS_LOCATION);
    }

    @Test
    @Transactional
    void getAllPostsByGoogleMapsLocationIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where googleMapsLocation in DEFAULT_GOOGLE_MAPS_LOCATION or UPDATED_GOOGLE_MAPS_LOCATION
        defaultPostShouldBeFound("googleMapsLocation.in=" + DEFAULT_GOOGLE_MAPS_LOCATION + "," + UPDATED_GOOGLE_MAPS_LOCATION);

        // Get all the postList where googleMapsLocation equals to UPDATED_GOOGLE_MAPS_LOCATION
        defaultPostShouldNotBeFound("googleMapsLocation.in=" + UPDATED_GOOGLE_MAPS_LOCATION);
    }

    @Test
    @Transactional
    void getAllPostsByGoogleMapsLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where googleMapsLocation is not null
        defaultPostShouldBeFound("googleMapsLocation.specified=true");

        // Get all the postList where googleMapsLocation is null
        defaultPostShouldNotBeFound("googleMapsLocation.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByGoogleMapsLocationContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where googleMapsLocation contains DEFAULT_GOOGLE_MAPS_LOCATION
        defaultPostShouldBeFound("googleMapsLocation.contains=" + DEFAULT_GOOGLE_MAPS_LOCATION);

        // Get all the postList where googleMapsLocation contains UPDATED_GOOGLE_MAPS_LOCATION
        defaultPostShouldNotBeFound("googleMapsLocation.contains=" + UPDATED_GOOGLE_MAPS_LOCATION);
    }

    @Test
    @Transactional
    void getAllPostsByGoogleMapsLocationNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where googleMapsLocation does not contain DEFAULT_GOOGLE_MAPS_LOCATION
        defaultPostShouldNotBeFound("googleMapsLocation.doesNotContain=" + DEFAULT_GOOGLE_MAPS_LOCATION);

        // Get all the postList where googleMapsLocation does not contain UPDATED_GOOGLE_MAPS_LOCATION
        defaultPostShouldBeFound("googleMapsLocation.doesNotContain=" + UPDATED_GOOGLE_MAPS_LOCATION);
    }

    @Test
    @Transactional
    void getAllPostsByWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where width equals to DEFAULT_WIDTH
        defaultPostShouldBeFound("width.equals=" + DEFAULT_WIDTH);

        // Get all the postList where width equals to UPDATED_WIDTH
        defaultPostShouldNotBeFound("width.equals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllPostsByWidthIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where width in DEFAULT_WIDTH or UPDATED_WIDTH
        defaultPostShouldBeFound("width.in=" + DEFAULT_WIDTH + "," + UPDATED_WIDTH);

        // Get all the postList where width equals to UPDATED_WIDTH
        defaultPostShouldNotBeFound("width.in=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllPostsByWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where width is not null
        defaultPostShouldBeFound("width.specified=true");

        // Get all the postList where width is null
        defaultPostShouldNotBeFound("width.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where width is greater than or equal to DEFAULT_WIDTH
        defaultPostShouldBeFound("width.greaterThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the postList where width is greater than or equal to UPDATED_WIDTH
        defaultPostShouldNotBeFound("width.greaterThanOrEqual=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllPostsByWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where width is less than or equal to DEFAULT_WIDTH
        defaultPostShouldBeFound("width.lessThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the postList where width is less than or equal to SMALLER_WIDTH
        defaultPostShouldNotBeFound("width.lessThanOrEqual=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllPostsByWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where width is less than DEFAULT_WIDTH
        defaultPostShouldNotBeFound("width.lessThan=" + DEFAULT_WIDTH);

        // Get all the postList where width is less than UPDATED_WIDTH
        defaultPostShouldBeFound("width.lessThan=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllPostsByWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where width is greater than DEFAULT_WIDTH
        defaultPostShouldNotBeFound("width.greaterThan=" + DEFAULT_WIDTH);

        // Get all the postList where width is greater than SMALLER_WIDTH
        defaultPostShouldBeFound("width.greaterThan=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllPostsByLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where length equals to DEFAULT_LENGTH
        defaultPostShouldBeFound("length.equals=" + DEFAULT_LENGTH);

        // Get all the postList where length equals to UPDATED_LENGTH
        defaultPostShouldNotBeFound("length.equals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllPostsByLengthIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where length in DEFAULT_LENGTH or UPDATED_LENGTH
        defaultPostShouldBeFound("length.in=" + DEFAULT_LENGTH + "," + UPDATED_LENGTH);

        // Get all the postList where length equals to UPDATED_LENGTH
        defaultPostShouldNotBeFound("length.in=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllPostsByLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where length is not null
        defaultPostShouldBeFound("length.specified=true");

        // Get all the postList where length is null
        defaultPostShouldNotBeFound("length.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where length is greater than or equal to DEFAULT_LENGTH
        defaultPostShouldBeFound("length.greaterThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the postList where length is greater than or equal to UPDATED_LENGTH
        defaultPostShouldNotBeFound("length.greaterThanOrEqual=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllPostsByLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where length is less than or equal to DEFAULT_LENGTH
        defaultPostShouldBeFound("length.lessThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the postList where length is less than or equal to SMALLER_LENGTH
        defaultPostShouldNotBeFound("length.lessThanOrEqual=" + SMALLER_LENGTH);
    }

    @Test
    @Transactional
    void getAllPostsByLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where length is less than DEFAULT_LENGTH
        defaultPostShouldNotBeFound("length.lessThan=" + DEFAULT_LENGTH);

        // Get all the postList where length is less than UPDATED_LENGTH
        defaultPostShouldBeFound("length.lessThan=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllPostsByLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where length is greater than DEFAULT_LENGTH
        defaultPostShouldNotBeFound("length.greaterThan=" + DEFAULT_LENGTH);

        // Get all the postList where length is greater than SMALLER_LENGTH
        defaultPostShouldBeFound("length.greaterThan=" + SMALLER_LENGTH);
    }

    @Test
    @Transactional
    void getAllPostsByDirectionIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where direction equals to DEFAULT_DIRECTION
        defaultPostShouldBeFound("direction.equals=" + DEFAULT_DIRECTION);

        // Get all the postList where direction equals to UPDATED_DIRECTION
        defaultPostShouldNotBeFound("direction.equals=" + UPDATED_DIRECTION);
    }

    @Test
    @Transactional
    void getAllPostsByDirectionIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where direction in DEFAULT_DIRECTION or UPDATED_DIRECTION
        defaultPostShouldBeFound("direction.in=" + DEFAULT_DIRECTION + "," + UPDATED_DIRECTION);

        // Get all the postList where direction equals to UPDATED_DIRECTION
        defaultPostShouldNotBeFound("direction.in=" + UPDATED_DIRECTION);
    }

    @Test
    @Transactional
    void getAllPostsByDirectionIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where direction is not null
        defaultPostShouldBeFound("direction.specified=true");

        // Get all the postList where direction is null
        defaultPostShouldNotBeFound("direction.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByDirectionContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where direction contains DEFAULT_DIRECTION
        defaultPostShouldBeFound("direction.contains=" + DEFAULT_DIRECTION);

        // Get all the postList where direction contains UPDATED_DIRECTION
        defaultPostShouldNotBeFound("direction.contains=" + UPDATED_DIRECTION);
    }

    @Test
    @Transactional
    void getAllPostsByDirectionNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where direction does not contain DEFAULT_DIRECTION
        defaultPostShouldNotBeFound("direction.doesNotContain=" + DEFAULT_DIRECTION);

        // Get all the postList where direction does not contain UPDATED_DIRECTION
        defaultPostShouldBeFound("direction.doesNotContain=" + UPDATED_DIRECTION);
    }

    @Test
    @Transactional
    void getAllPostsByDistanceIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where distance equals to DEFAULT_DISTANCE
        defaultPostShouldBeFound("distance.equals=" + DEFAULT_DISTANCE);

        // Get all the postList where distance equals to UPDATED_DISTANCE
        defaultPostShouldNotBeFound("distance.equals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllPostsByDistanceIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where distance in DEFAULT_DISTANCE or UPDATED_DISTANCE
        defaultPostShouldBeFound("distance.in=" + DEFAULT_DISTANCE + "," + UPDATED_DISTANCE);

        // Get all the postList where distance equals to UPDATED_DISTANCE
        defaultPostShouldNotBeFound("distance.in=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllPostsByDistanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where distance is not null
        defaultPostShouldBeFound("distance.specified=true");

        // Get all the postList where distance is null
        defaultPostShouldNotBeFound("distance.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByDistanceContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where distance contains DEFAULT_DISTANCE
        defaultPostShouldBeFound("distance.contains=" + DEFAULT_DISTANCE);

        // Get all the postList where distance contains UPDATED_DISTANCE
        defaultPostShouldNotBeFound("distance.contains=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllPostsByDistanceNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where distance does not contain DEFAULT_DISTANCE
        defaultPostShouldNotBeFound("distance.doesNotContain=" + DEFAULT_DISTANCE);

        // Get all the postList where distance does not contain UPDATED_DISTANCE
        defaultPostShouldBeFound("distance.doesNotContain=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllPostsByLegalIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where legal equals to DEFAULT_LEGAL
        defaultPostShouldBeFound("legal.equals=" + DEFAULT_LEGAL);

        // Get all the postList where legal equals to UPDATED_LEGAL
        defaultPostShouldNotBeFound("legal.equals=" + UPDATED_LEGAL);
    }

    @Test
    @Transactional
    void getAllPostsByLegalIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where legal in DEFAULT_LEGAL or UPDATED_LEGAL
        defaultPostShouldBeFound("legal.in=" + DEFAULT_LEGAL + "," + UPDATED_LEGAL);

        // Get all the postList where legal equals to UPDATED_LEGAL
        defaultPostShouldNotBeFound("legal.in=" + UPDATED_LEGAL);
    }

    @Test
    @Transactional
    void getAllPostsByLegalIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where legal is not null
        defaultPostShouldBeFound("legal.specified=true");

        // Get all the postList where legal is null
        defaultPostShouldNotBeFound("legal.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByLegalContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where legal contains DEFAULT_LEGAL
        defaultPostShouldBeFound("legal.contains=" + DEFAULT_LEGAL);

        // Get all the postList where legal contains UPDATED_LEGAL
        defaultPostShouldNotBeFound("legal.contains=" + UPDATED_LEGAL);
    }

    @Test
    @Transactional
    void getAllPostsByLegalNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where legal does not contain DEFAULT_LEGAL
        defaultPostShouldNotBeFound("legal.doesNotContain=" + DEFAULT_LEGAL);

        // Get all the postList where legal does not contain UPDATED_LEGAL
        defaultPostShouldBeFound("legal.doesNotContain=" + UPDATED_LEGAL);
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfFloorsIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfFloors equals to DEFAULT_NUMBER_OF_FLOORS
        defaultPostShouldBeFound("numberOfFloors.equals=" + DEFAULT_NUMBER_OF_FLOORS);

        // Get all the postList where numberOfFloors equals to UPDATED_NUMBER_OF_FLOORS
        defaultPostShouldNotBeFound("numberOfFloors.equals=" + UPDATED_NUMBER_OF_FLOORS);
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfFloorsIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfFloors in DEFAULT_NUMBER_OF_FLOORS or UPDATED_NUMBER_OF_FLOORS
        defaultPostShouldBeFound("numberOfFloors.in=" + DEFAULT_NUMBER_OF_FLOORS + "," + UPDATED_NUMBER_OF_FLOORS);

        // Get all the postList where numberOfFloors equals to UPDATED_NUMBER_OF_FLOORS
        defaultPostShouldNotBeFound("numberOfFloors.in=" + UPDATED_NUMBER_OF_FLOORS);
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfFloorsIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfFloors is not null
        defaultPostShouldBeFound("numberOfFloors.specified=true");

        // Get all the postList where numberOfFloors is null
        defaultPostShouldNotBeFound("numberOfFloors.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfFloorsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfFloors is greater than or equal to DEFAULT_NUMBER_OF_FLOORS
        defaultPostShouldBeFound("numberOfFloors.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_FLOORS);

        // Get all the postList where numberOfFloors is greater than or equal to UPDATED_NUMBER_OF_FLOORS
        defaultPostShouldNotBeFound("numberOfFloors.greaterThanOrEqual=" + UPDATED_NUMBER_OF_FLOORS);
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfFloorsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfFloors is less than or equal to DEFAULT_NUMBER_OF_FLOORS
        defaultPostShouldBeFound("numberOfFloors.lessThanOrEqual=" + DEFAULT_NUMBER_OF_FLOORS);

        // Get all the postList where numberOfFloors is less than or equal to SMALLER_NUMBER_OF_FLOORS
        defaultPostShouldNotBeFound("numberOfFloors.lessThanOrEqual=" + SMALLER_NUMBER_OF_FLOORS);
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfFloorsIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfFloors is less than DEFAULT_NUMBER_OF_FLOORS
        defaultPostShouldNotBeFound("numberOfFloors.lessThan=" + DEFAULT_NUMBER_OF_FLOORS);

        // Get all the postList where numberOfFloors is less than UPDATED_NUMBER_OF_FLOORS
        defaultPostShouldBeFound("numberOfFloors.lessThan=" + UPDATED_NUMBER_OF_FLOORS);
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfFloorsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfFloors is greater than DEFAULT_NUMBER_OF_FLOORS
        defaultPostShouldNotBeFound("numberOfFloors.greaterThan=" + DEFAULT_NUMBER_OF_FLOORS);

        // Get all the postList where numberOfFloors is greater than SMALLER_NUMBER_OF_FLOORS
        defaultPostShouldBeFound("numberOfFloors.greaterThan=" + SMALLER_NUMBER_OF_FLOORS);
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfBedroomIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfBedroom equals to DEFAULT_NUMBER_OF_BEDROOM
        defaultPostShouldBeFound("numberOfBedroom.equals=" + DEFAULT_NUMBER_OF_BEDROOM);

        // Get all the postList where numberOfBedroom equals to UPDATED_NUMBER_OF_BEDROOM
        defaultPostShouldNotBeFound("numberOfBedroom.equals=" + UPDATED_NUMBER_OF_BEDROOM);
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfBedroomIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfBedroom in DEFAULT_NUMBER_OF_BEDROOM or UPDATED_NUMBER_OF_BEDROOM
        defaultPostShouldBeFound("numberOfBedroom.in=" + DEFAULT_NUMBER_OF_BEDROOM + "," + UPDATED_NUMBER_OF_BEDROOM);

        // Get all the postList where numberOfBedroom equals to UPDATED_NUMBER_OF_BEDROOM
        defaultPostShouldNotBeFound("numberOfBedroom.in=" + UPDATED_NUMBER_OF_BEDROOM);
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfBedroomIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfBedroom is not null
        defaultPostShouldBeFound("numberOfBedroom.specified=true");

        // Get all the postList where numberOfBedroom is null
        defaultPostShouldNotBeFound("numberOfBedroom.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfBedroomIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfBedroom is greater than or equal to DEFAULT_NUMBER_OF_BEDROOM
        defaultPostShouldBeFound("numberOfBedroom.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_BEDROOM);

        // Get all the postList where numberOfBedroom is greater than or equal to UPDATED_NUMBER_OF_BEDROOM
        defaultPostShouldNotBeFound("numberOfBedroom.greaterThanOrEqual=" + UPDATED_NUMBER_OF_BEDROOM);
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfBedroomIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfBedroom is less than or equal to DEFAULT_NUMBER_OF_BEDROOM
        defaultPostShouldBeFound("numberOfBedroom.lessThanOrEqual=" + DEFAULT_NUMBER_OF_BEDROOM);

        // Get all the postList where numberOfBedroom is less than or equal to SMALLER_NUMBER_OF_BEDROOM
        defaultPostShouldNotBeFound("numberOfBedroom.lessThanOrEqual=" + SMALLER_NUMBER_OF_BEDROOM);
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfBedroomIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfBedroom is less than DEFAULT_NUMBER_OF_BEDROOM
        defaultPostShouldNotBeFound("numberOfBedroom.lessThan=" + DEFAULT_NUMBER_OF_BEDROOM);

        // Get all the postList where numberOfBedroom is less than UPDATED_NUMBER_OF_BEDROOM
        defaultPostShouldBeFound("numberOfBedroom.lessThan=" + UPDATED_NUMBER_OF_BEDROOM);
    }

    @Test
    @Transactional
    void getAllPostsByNumberOfBedroomIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where numberOfBedroom is greater than DEFAULT_NUMBER_OF_BEDROOM
        defaultPostShouldNotBeFound("numberOfBedroom.greaterThan=" + DEFAULT_NUMBER_OF_BEDROOM);

        // Get all the postList where numberOfBedroom is greater than SMALLER_NUMBER_OF_BEDROOM
        defaultPostShouldBeFound("numberOfBedroom.greaterThan=" + SMALLER_NUMBER_OF_BEDROOM);
    }

    @Test
    @Transactional
    void getAllPostsByHasKitchenIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hasKitchen equals to DEFAULT_HAS_KITCHEN
        defaultPostShouldBeFound("hasKitchen.equals=" + DEFAULT_HAS_KITCHEN);

        // Get all the postList where hasKitchen equals to UPDATED_HAS_KITCHEN
        defaultPostShouldNotBeFound("hasKitchen.equals=" + UPDATED_HAS_KITCHEN);
    }

    @Test
    @Transactional
    void getAllPostsByHasKitchenIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hasKitchen in DEFAULT_HAS_KITCHEN or UPDATED_HAS_KITCHEN
        defaultPostShouldBeFound("hasKitchen.in=" + DEFAULT_HAS_KITCHEN + "," + UPDATED_HAS_KITCHEN);

        // Get all the postList where hasKitchen equals to UPDATED_HAS_KITCHEN
        defaultPostShouldNotBeFound("hasKitchen.in=" + UPDATED_HAS_KITCHEN);
    }

    @Test
    @Transactional
    void getAllPostsByHasKitchenIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hasKitchen is not null
        defaultPostShouldBeFound("hasKitchen.specified=true");

        // Get all the postList where hasKitchen is null
        defaultPostShouldNotBeFound("hasKitchen.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByHasDinningRoomIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hasDinningRoom equals to DEFAULT_HAS_DINNING_ROOM
        defaultPostShouldBeFound("hasDinningRoom.equals=" + DEFAULT_HAS_DINNING_ROOM);

        // Get all the postList where hasDinningRoom equals to UPDATED_HAS_DINNING_ROOM
        defaultPostShouldNotBeFound("hasDinningRoom.equals=" + UPDATED_HAS_DINNING_ROOM);
    }

    @Test
    @Transactional
    void getAllPostsByHasDinningRoomIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hasDinningRoom in DEFAULT_HAS_DINNING_ROOM or UPDATED_HAS_DINNING_ROOM
        defaultPostShouldBeFound("hasDinningRoom.in=" + DEFAULT_HAS_DINNING_ROOM + "," + UPDATED_HAS_DINNING_ROOM);

        // Get all the postList where hasDinningRoom equals to UPDATED_HAS_DINNING_ROOM
        defaultPostShouldNotBeFound("hasDinningRoom.in=" + UPDATED_HAS_DINNING_ROOM);
    }

    @Test
    @Transactional
    void getAllPostsByHasDinningRoomIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hasDinningRoom is not null
        defaultPostShouldBeFound("hasDinningRoom.specified=true");

        // Get all the postList where hasDinningRoom is null
        defaultPostShouldNotBeFound("hasDinningRoom.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByHasRooftopIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hasRooftop equals to DEFAULT_HAS_ROOFTOP
        defaultPostShouldBeFound("hasRooftop.equals=" + DEFAULT_HAS_ROOFTOP);

        // Get all the postList where hasRooftop equals to UPDATED_HAS_ROOFTOP
        defaultPostShouldNotBeFound("hasRooftop.equals=" + UPDATED_HAS_ROOFTOP);
    }

    @Test
    @Transactional
    void getAllPostsByHasRooftopIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hasRooftop in DEFAULT_HAS_ROOFTOP or UPDATED_HAS_ROOFTOP
        defaultPostShouldBeFound("hasRooftop.in=" + DEFAULT_HAS_ROOFTOP + "," + UPDATED_HAS_ROOFTOP);

        // Get all the postList where hasRooftop equals to UPDATED_HAS_ROOFTOP
        defaultPostShouldNotBeFound("hasRooftop.in=" + UPDATED_HAS_ROOFTOP);
    }

    @Test
    @Transactional
    void getAllPostsByHasRooftopIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hasRooftop is not null
        defaultPostShouldBeFound("hasRooftop.specified=true");

        // Get all the postList where hasRooftop is null
        defaultPostShouldNotBeFound("hasRooftop.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByHasGarageIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hasGarage equals to DEFAULT_HAS_GARAGE
        defaultPostShouldBeFound("hasGarage.equals=" + DEFAULT_HAS_GARAGE);

        // Get all the postList where hasGarage equals to UPDATED_HAS_GARAGE
        defaultPostShouldNotBeFound("hasGarage.equals=" + UPDATED_HAS_GARAGE);
    }

    @Test
    @Transactional
    void getAllPostsByHasGarageIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hasGarage in DEFAULT_HAS_GARAGE or UPDATED_HAS_GARAGE
        defaultPostShouldBeFound("hasGarage.in=" + DEFAULT_HAS_GARAGE + "," + UPDATED_HAS_GARAGE);

        // Get all the postList where hasGarage equals to UPDATED_HAS_GARAGE
        defaultPostShouldNotBeFound("hasGarage.in=" + UPDATED_HAS_GARAGE);
    }

    @Test
    @Transactional
    void getAllPostsByHasGarageIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hasGarage is not null
        defaultPostShouldBeFound("hasGarage.specified=true");

        // Get all the postList where hasGarage is null
        defaultPostShouldNotBeFound("hasGarage.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByIsVipIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where isVip equals to DEFAULT_IS_VIP
        defaultPostShouldBeFound("isVip.equals=" + DEFAULT_IS_VIP);

        // Get all the postList where isVip equals to UPDATED_IS_VIP
        defaultPostShouldNotBeFound("isVip.equals=" + UPDATED_IS_VIP);
    }

    @Test
    @Transactional
    void getAllPostsByIsVipIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where isVip in DEFAULT_IS_VIP or UPDATED_IS_VIP
        defaultPostShouldBeFound("isVip.in=" + DEFAULT_IS_VIP + "," + UPDATED_IS_VIP);

        // Get all the postList where isVip equals to UPDATED_IS_VIP
        defaultPostShouldNotBeFound("isVip.in=" + UPDATED_IS_VIP);
    }

    @Test
    @Transactional
    void getAllPostsByIsVipIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where isVip is not null
        defaultPostShouldBeFound("isVip.specified=true");

        // Get all the postList where isVip is null
        defaultPostShouldNotBeFound("isVip.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByPostingTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where postingTime equals to DEFAULT_POSTING_TIME
        defaultPostShouldBeFound("postingTime.equals=" + DEFAULT_POSTING_TIME);

        // Get all the postList where postingTime equals to UPDATED_POSTING_TIME
        defaultPostShouldNotBeFound("postingTime.equals=" + UPDATED_POSTING_TIME);
    }

    @Test
    @Transactional
    void getAllPostsByPostingTimeIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where postingTime in DEFAULT_POSTING_TIME or UPDATED_POSTING_TIME
        defaultPostShouldBeFound("postingTime.in=" + DEFAULT_POSTING_TIME + "," + UPDATED_POSTING_TIME);

        // Get all the postList where postingTime equals to UPDATED_POSTING_TIME
        defaultPostShouldNotBeFound("postingTime.in=" + UPDATED_POSTING_TIME);
    }

    @Test
    @Transactional
    void getAllPostsByPostingTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where postingTime is not null
        defaultPostShouldBeFound("postingTime.specified=true");

        // Get all the postList where postingTime is null
        defaultPostShouldNotBeFound("postingTime.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByPostingTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where postingTime is greater than or equal to DEFAULT_POSTING_TIME
        defaultPostShouldBeFound("postingTime.greaterThanOrEqual=" + DEFAULT_POSTING_TIME);

        // Get all the postList where postingTime is greater than or equal to UPDATED_POSTING_TIME
        defaultPostShouldNotBeFound("postingTime.greaterThanOrEqual=" + UPDATED_POSTING_TIME);
    }

    @Test
    @Transactional
    void getAllPostsByPostingTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where postingTime is less than or equal to DEFAULT_POSTING_TIME
        defaultPostShouldBeFound("postingTime.lessThanOrEqual=" + DEFAULT_POSTING_TIME);

        // Get all the postList where postingTime is less than or equal to SMALLER_POSTING_TIME
        defaultPostShouldNotBeFound("postingTime.lessThanOrEqual=" + SMALLER_POSTING_TIME);
    }

    @Test
    @Transactional
    void getAllPostsByPostingTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where postingTime is less than DEFAULT_POSTING_TIME
        defaultPostShouldNotBeFound("postingTime.lessThan=" + DEFAULT_POSTING_TIME);

        // Get all the postList where postingTime is less than UPDATED_POSTING_TIME
        defaultPostShouldBeFound("postingTime.lessThan=" + UPDATED_POSTING_TIME);
    }

    @Test
    @Transactional
    void getAllPostsByPostingTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where postingTime is greater than DEFAULT_POSTING_TIME
        defaultPostShouldNotBeFound("postingTime.greaterThan=" + DEFAULT_POSTING_TIME);

        // Get all the postList where postingTime is greater than SMALLER_POSTING_TIME
        defaultPostShouldBeFound("postingTime.greaterThan=" + SMALLER_POSTING_TIME);
    }

    @Test
    @Transactional
    void getAllPostsByExpiredTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where expiredTime equals to DEFAULT_EXPIRED_TIME
        defaultPostShouldBeFound("expiredTime.equals=" + DEFAULT_EXPIRED_TIME);

        // Get all the postList where expiredTime equals to UPDATED_EXPIRED_TIME
        defaultPostShouldNotBeFound("expiredTime.equals=" + UPDATED_EXPIRED_TIME);
    }

    @Test
    @Transactional
    void getAllPostsByExpiredTimeIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where expiredTime in DEFAULT_EXPIRED_TIME or UPDATED_EXPIRED_TIME
        defaultPostShouldBeFound("expiredTime.in=" + DEFAULT_EXPIRED_TIME + "," + UPDATED_EXPIRED_TIME);

        // Get all the postList where expiredTime equals to UPDATED_EXPIRED_TIME
        defaultPostShouldNotBeFound("expiredTime.in=" + UPDATED_EXPIRED_TIME);
    }

    @Test
    @Transactional
    void getAllPostsByExpiredTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where expiredTime is not null
        defaultPostShouldBeFound("expiredTime.specified=true");

        // Get all the postList where expiredTime is null
        defaultPostShouldNotBeFound("expiredTime.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByExpiredTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where expiredTime is greater than or equal to DEFAULT_EXPIRED_TIME
        defaultPostShouldBeFound("expiredTime.greaterThanOrEqual=" + DEFAULT_EXPIRED_TIME);

        // Get all the postList where expiredTime is greater than or equal to UPDATED_EXPIRED_TIME
        defaultPostShouldNotBeFound("expiredTime.greaterThanOrEqual=" + UPDATED_EXPIRED_TIME);
    }

    @Test
    @Transactional
    void getAllPostsByExpiredTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where expiredTime is less than or equal to DEFAULT_EXPIRED_TIME
        defaultPostShouldBeFound("expiredTime.lessThanOrEqual=" + DEFAULT_EXPIRED_TIME);

        // Get all the postList where expiredTime is less than or equal to SMALLER_EXPIRED_TIME
        defaultPostShouldNotBeFound("expiredTime.lessThanOrEqual=" + SMALLER_EXPIRED_TIME);
    }

    @Test
    @Transactional
    void getAllPostsByExpiredTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where expiredTime is less than DEFAULT_EXPIRED_TIME
        defaultPostShouldNotBeFound("expiredTime.lessThan=" + DEFAULT_EXPIRED_TIME);

        // Get all the postList where expiredTime is less than UPDATED_EXPIRED_TIME
        defaultPostShouldBeFound("expiredTime.lessThan=" + UPDATED_EXPIRED_TIME);
    }

    @Test
    @Transactional
    void getAllPostsByExpiredTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where expiredTime is greater than DEFAULT_EXPIRED_TIME
        defaultPostShouldNotBeFound("expiredTime.greaterThan=" + DEFAULT_EXPIRED_TIME);

        // Get all the postList where expiredTime is greater than SMALLER_EXPIRED_TIME
        defaultPostShouldBeFound("expiredTime.greaterThan=" + SMALLER_EXPIRED_TIME);
    }

    @Test
    @Transactional
    void getAllPostsByBrokerageFeesIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where brokerageFees equals to DEFAULT_BROKERAGE_FEES
        defaultPostShouldBeFound("brokerageFees.equals=" + DEFAULT_BROKERAGE_FEES);

        // Get all the postList where brokerageFees equals to UPDATED_BROKERAGE_FEES
        defaultPostShouldNotBeFound("brokerageFees.equals=" + UPDATED_BROKERAGE_FEES);
    }

    @Test
    @Transactional
    void getAllPostsByBrokerageFeesIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where brokerageFees in DEFAULT_BROKERAGE_FEES or UPDATED_BROKERAGE_FEES
        defaultPostShouldBeFound("brokerageFees.in=" + DEFAULT_BROKERAGE_FEES + "," + UPDATED_BROKERAGE_FEES);

        // Get all the postList where brokerageFees equals to UPDATED_BROKERAGE_FEES
        defaultPostShouldNotBeFound("brokerageFees.in=" + UPDATED_BROKERAGE_FEES);
    }

    @Test
    @Transactional
    void getAllPostsByBrokerageFeesIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where brokerageFees is not null
        defaultPostShouldBeFound("brokerageFees.specified=true");

        // Get all the postList where brokerageFees is null
        defaultPostShouldNotBeFound("brokerageFees.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByBrokerageFeesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where brokerageFees is greater than or equal to DEFAULT_BROKERAGE_FEES
        defaultPostShouldBeFound("brokerageFees.greaterThanOrEqual=" + DEFAULT_BROKERAGE_FEES);

        // Get all the postList where brokerageFees is greater than or equal to UPDATED_BROKERAGE_FEES
        defaultPostShouldNotBeFound("brokerageFees.greaterThanOrEqual=" + UPDATED_BROKERAGE_FEES);
    }

    @Test
    @Transactional
    void getAllPostsByBrokerageFeesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where brokerageFees is less than or equal to DEFAULT_BROKERAGE_FEES
        defaultPostShouldBeFound("brokerageFees.lessThanOrEqual=" + DEFAULT_BROKERAGE_FEES);

        // Get all the postList where brokerageFees is less than or equal to SMALLER_BROKERAGE_FEES
        defaultPostShouldNotBeFound("brokerageFees.lessThanOrEqual=" + SMALLER_BROKERAGE_FEES);
    }

    @Test
    @Transactional
    void getAllPostsByBrokerageFeesIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where brokerageFees is less than DEFAULT_BROKERAGE_FEES
        defaultPostShouldNotBeFound("brokerageFees.lessThan=" + DEFAULT_BROKERAGE_FEES);

        // Get all the postList where brokerageFees is less than UPDATED_BROKERAGE_FEES
        defaultPostShouldBeFound("brokerageFees.lessThan=" + UPDATED_BROKERAGE_FEES);
    }

    @Test
    @Transactional
    void getAllPostsByBrokerageFeesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where brokerageFees is greater than DEFAULT_BROKERAGE_FEES
        defaultPostShouldNotBeFound("brokerageFees.greaterThan=" + DEFAULT_BROKERAGE_FEES);

        // Get all the postList where brokerageFees is greater than SMALLER_BROKERAGE_FEES
        defaultPostShouldBeFound("brokerageFees.greaterThan=" + SMALLER_BROKERAGE_FEES);
    }

    @Test
    @Transactional
    void getAllPostsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where status equals to DEFAULT_STATUS
        defaultPostShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the postList where status equals to UPDATED_STATUS
        defaultPostShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPostsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPostShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the postList where status equals to UPDATED_STATUS
        defaultPostShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPostsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where status is not null
        defaultPostShouldBeFound("status.specified=true");

        // Get all the postList where status is null
        defaultPostShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByStarIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where star equals to DEFAULT_STAR
        defaultPostShouldBeFound("star.equals=" + DEFAULT_STAR);

        // Get all the postList where star equals to UPDATED_STAR
        defaultPostShouldNotBeFound("star.equals=" + UPDATED_STAR);
    }

    @Test
    @Transactional
    void getAllPostsByStarIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where star in DEFAULT_STAR or UPDATED_STAR
        defaultPostShouldBeFound("star.in=" + DEFAULT_STAR + "," + UPDATED_STAR);

        // Get all the postList where star equals to UPDATED_STAR
        defaultPostShouldNotBeFound("star.in=" + UPDATED_STAR);
    }

    @Test
    @Transactional
    void getAllPostsByStarIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where star is not null
        defaultPostShouldBeFound("star.specified=true");

        // Get all the postList where star is null
        defaultPostShouldNotBeFound("star.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByStarIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where star is greater than or equal to DEFAULT_STAR
        defaultPostShouldBeFound("star.greaterThanOrEqual=" + DEFAULT_STAR);

        // Get all the postList where star is greater than or equal to UPDATED_STAR
        defaultPostShouldNotBeFound("star.greaterThanOrEqual=" + UPDATED_STAR);
    }

    @Test
    @Transactional
    void getAllPostsByStarIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where star is less than or equal to DEFAULT_STAR
        defaultPostShouldBeFound("star.lessThanOrEqual=" + DEFAULT_STAR);

        // Get all the postList where star is less than or equal to SMALLER_STAR
        defaultPostShouldNotBeFound("star.lessThanOrEqual=" + SMALLER_STAR);
    }

    @Test
    @Transactional
    void getAllPostsByStarIsLessThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where star is less than DEFAULT_STAR
        defaultPostShouldNotBeFound("star.lessThan=" + DEFAULT_STAR);

        // Get all the postList where star is less than UPDATED_STAR
        defaultPostShouldBeFound("star.lessThan=" + UPDATED_STAR);
    }

    @Test
    @Transactional
    void getAllPostsByStarIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where star is greater than DEFAULT_STAR
        defaultPostShouldNotBeFound("star.greaterThan=" + DEFAULT_STAR);

        // Get all the postList where star is greater than SMALLER_STAR
        defaultPostShouldBeFound("star.greaterThan=" + SMALLER_STAR);
    }

    @Test
    @Transactional
    void getAllPostsByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hash equals to DEFAULT_HASH
        defaultPostShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the postList where hash equals to UPDATED_HASH
        defaultPostShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllPostsByHashIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultPostShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the postList where hash equals to UPDATED_HASH
        defaultPostShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllPostsByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hash is not null
        defaultPostShouldBeFound("hash.specified=true");

        // Get all the postList where hash is null
        defaultPostShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllPostsByHashContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hash contains DEFAULT_HASH
        defaultPostShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the postList where hash contains UPDATED_HASH
        defaultPostShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllPostsByHashNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where hash does not contain DEFAULT_HASH
        defaultPostShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the postList where hash does not contain UPDATED_HASH
        defaultPostShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllPostsByImagesIsEqualToSomething() throws Exception {
        Image images;
        if (TestUtil.findAll(em, Image.class).isEmpty()) {
            postRepository.saveAndFlush(post);
            images = ImageResourceIT.createEntity(em);
        } else {
            images = TestUtil.findAll(em, Image.class).get(0);
        }
        em.persist(images);
        em.flush();
        post.addImages(images);
        postRepository.saveAndFlush(post);
        Long imagesId = images.getId();

        // Get all the postList where images equals to imagesId
        defaultPostShouldBeFound("imagesId.equals=" + imagesId);

        // Get all the postList where images equals to (imagesId + 1)
        defaultPostShouldNotBeFound("imagesId.equals=" + (imagesId + 1));
    }

    @Test
    @Transactional
    void getAllPostsByTypeIsEqualToSomething() throws Exception {
        PostType type;
        if (TestUtil.findAll(em, PostType.class).isEmpty()) {
            postRepository.saveAndFlush(post);
            type = PostTypeResourceIT.createEntity(em);
        } else {
            type = TestUtil.findAll(em, PostType.class).get(0);
        }
        em.persist(type);
        em.flush();
        post.setType(type);
        postRepository.saveAndFlush(post);
        Long typeId = type.getId();

        // Get all the postList where type equals to typeId
        defaultPostShouldBeFound("typeId.equals=" + typeId);

        // Get all the postList where type equals to (typeId + 1)
        defaultPostShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }

    @Test
    @Transactional
    void getAllPostsByCategoryIsEqualToSomething() throws Exception {
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            postRepository.saveAndFlush(post);
            category = CategoryResourceIT.createEntity(em);
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(category);
        em.flush();
        post.setCategory(category);
        postRepository.saveAndFlush(post);
        Long categoryId = category.getId();

        // Get all the postList where category equals to categoryId
        defaultPostShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the postList where category equals to (categoryId + 1)
        defaultPostShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    @Test
    @Transactional
    void getAllPostsByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            postRepository.saveAndFlush(post);
            user = UserResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        post.setUser(user);
        postRepository.saveAndFlush(post);
        Long userId = user.getId();

        // Get all the postList where user equals to userId
        defaultPostShouldBeFound("userId.equals=" + userId);

        // Get all the postList where user equals to (userId + 1)
        defaultPostShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllPostsByProvinceIsEqualToSomething() throws Exception {
        Province province;
        if (TestUtil.findAll(em, Province.class).isEmpty()) {
            postRepository.saveAndFlush(post);
            province = ProvinceResourceIT.createEntity(em);
        } else {
            province = TestUtil.findAll(em, Province.class).get(0);
        }
        em.persist(province);
        em.flush();
        post.setProvince(province);
        postRepository.saveAndFlush(post);
        Long provinceId = province.getId();

        // Get all the postList where province equals to provinceId
        defaultPostShouldBeFound("provinceId.equals=" + provinceId);

        // Get all the postList where province equals to (provinceId + 1)
        defaultPostShouldNotBeFound("provinceId.equals=" + (provinceId + 1));
    }

    @Test
    @Transactional
    void getAllPostsByDistrictIsEqualToSomething() throws Exception {
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            postRepository.saveAndFlush(post);
            district = DistrictResourceIT.createEntity(em);
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        em.persist(district);
        em.flush();
        post.setDistrict(district);
        postRepository.saveAndFlush(post);
        Long districtId = district.getId();

        // Get all the postList where district equals to districtId
        defaultPostShouldBeFound("districtId.equals=" + districtId);

        // Get all the postList where district equals to (districtId + 1)
        defaultPostShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    @Test
    @Transactional
    void getAllPostsByWardIsEqualToSomething() throws Exception {
        Ward ward;
        if (TestUtil.findAll(em, Ward.class).isEmpty()) {
            postRepository.saveAndFlush(post);
            ward = WardResourceIT.createEntity(em);
        } else {
            ward = TestUtil.findAll(em, Ward.class).get(0);
        }
        em.persist(ward);
        em.flush();
        post.setWard(ward);
        postRepository.saveAndFlush(post);
        Long wardId = ward.getId();

        // Get all the postList where ward equals to wardId
        defaultPostShouldBeFound("wardId.equals=" + wardId);

        // Get all the postList where ward equals to (wardId + 1)
        defaultPostShouldNotBeFound("wardId.equals=" + (wardId + 1));
    }

    @Test
    @Transactional
    void getAllPostsByStreetIsEqualToSomething() throws Exception {
        Street street;
        if (TestUtil.findAll(em, Street.class).isEmpty()) {
            postRepository.saveAndFlush(post);
            street = StreetResourceIT.createEntity(em);
        } else {
            street = TestUtil.findAll(em, Street.class).get(0);
        }
        em.persist(street);
        em.flush();
        post.setStreet(street);
        postRepository.saveAndFlush(post);
        Long streetId = street.getId();

        // Get all the postList where street equals to streetId
        defaultPostShouldBeFound("streetId.equals=" + streetId);

        // Get all the postList where street equals to (streetId + 1)
        defaultPostShouldNotBeFound("streetId.equals=" + (streetId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostShouldBeFound(String filter) throws Exception {
        restPostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].square").value(hasItem(DEFAULT_SQUARE.intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].googleMapsLocation").value(hasItem(DEFAULT_GOOGLE_MAPS_LOCATION)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].direction").value(hasItem(DEFAULT_DIRECTION)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE)))
            .andExpect(jsonPath("$.[*].legal").value(hasItem(DEFAULT_LEGAL)))
            .andExpect(jsonPath("$.[*].numberOfFloors").value(hasItem(DEFAULT_NUMBER_OF_FLOORS.intValue())))
            .andExpect(jsonPath("$.[*].numberOfBedroom").value(hasItem(DEFAULT_NUMBER_OF_BEDROOM.intValue())))
            .andExpect(jsonPath("$.[*].hasKitchen").value(hasItem(DEFAULT_HAS_KITCHEN.booleanValue())))
            .andExpect(jsonPath("$.[*].hasDinningRoom").value(hasItem(DEFAULT_HAS_DINNING_ROOM.booleanValue())))
            .andExpect(jsonPath("$.[*].hasRooftop").value(hasItem(DEFAULT_HAS_ROOFTOP.booleanValue())))
            .andExpect(jsonPath("$.[*].hasGarage").value(hasItem(DEFAULT_HAS_GARAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].isVip").value(hasItem(DEFAULT_IS_VIP.booleanValue())))
            .andExpect(jsonPath("$.[*].postingTime").value(hasItem(DEFAULT_POSTING_TIME.toString())))
            .andExpect(jsonPath("$.[*].expiredTime").value(hasItem(DEFAULT_EXPIRED_TIME.toString())))
            .andExpect(jsonPath("$.[*].brokerageFees").value(hasItem(DEFAULT_BROKERAGE_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].star").value(hasItem(DEFAULT_STAR.doubleValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)));

        // Check, that the count call also returns 1
        restPostMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostShouldNotBeFound(String filter) throws Exception {
        restPostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPost() throws Exception {
        // Get the post
        restPostMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Update the post
        Post updatedPost = postRepository.findById(post.getId()).get();
        // Disconnect from session so that the updates on updatedPost are not directly saved in db
        em.detach(updatedPost);
        updatedPost
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .price(UPDATED_PRICE)
            .square(UPDATED_SQUARE)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .googleMapsLocation(UPDATED_GOOGLE_MAPS_LOCATION)
            .width(UPDATED_WIDTH)
            .length(UPDATED_LENGTH)
            .direction(UPDATED_DIRECTION)
            .distance(UPDATED_DISTANCE)
            .legal(UPDATED_LEGAL)
            .numberOfFloors(UPDATED_NUMBER_OF_FLOORS)
            .numberOfBedroom(UPDATED_NUMBER_OF_BEDROOM)
            .hasKitchen(UPDATED_HAS_KITCHEN)
            .hasDinningRoom(UPDATED_HAS_DINNING_ROOM)
            .hasRooftop(UPDATED_HAS_ROOFTOP)
            .hasGarage(UPDATED_HAS_GARAGE)
            .isVip(UPDATED_IS_VIP)
            .postingTime(UPDATED_POSTING_TIME)
            .expiredTime(UPDATED_EXPIRED_TIME)
            .brokerageFees(UPDATED_BROKERAGE_FEES)
            .status(UPDATED_STATUS)
            .star(UPDATED_STAR)
            .hash(UPDATED_HASH);
        PostDTO postDTO = postMapper.toDto(updatedPost);

        restPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postDTO))
            )
            .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPost.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPost.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPost.getSquare()).isEqualTo(UPDATED_SQUARE);
        assertThat(testPost.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPost.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPost.getGoogleMapsLocation()).isEqualTo(UPDATED_GOOGLE_MAPS_LOCATION);
        assertThat(testPost.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testPost.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testPost.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testPost.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testPost.getLegal()).isEqualTo(UPDATED_LEGAL);
        assertThat(testPost.getNumberOfFloors()).isEqualTo(UPDATED_NUMBER_OF_FLOORS);
        assertThat(testPost.getNumberOfBedroom()).isEqualTo(UPDATED_NUMBER_OF_BEDROOM);
        assertThat(testPost.getHasKitchen()).isEqualTo(UPDATED_HAS_KITCHEN);
        assertThat(testPost.getHasDinningRoom()).isEqualTo(UPDATED_HAS_DINNING_ROOM);
        assertThat(testPost.getHasRooftop()).isEqualTo(UPDATED_HAS_ROOFTOP);
        assertThat(testPost.getHasGarage()).isEqualTo(UPDATED_HAS_GARAGE);
        assertThat(testPost.getIsVip()).isEqualTo(UPDATED_IS_VIP);
        assertThat(testPost.getPostingTime()).isEqualTo(UPDATED_POSTING_TIME);
        assertThat(testPost.getExpiredTime()).isEqualTo(UPDATED_EXPIRED_TIME);
        assertThat(testPost.getBrokerageFees()).isEqualTo(UPDATED_BROKERAGE_FEES);
        assertThat(testPost.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPost.getStar()).isEqualTo(UPDATED_STAR);
        assertThat(testPost.getHash()).isEqualTo(UPDATED_HASH);
    }

    @Test
    @Transactional
    void putNonExistingPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePostWithPatch() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Update the post using partial update
        Post partialUpdatedPost = new Post();
        partialUpdatedPost.setId(post.getId());

        partialUpdatedPost
            .square(UPDATED_SQUARE)
            .phone(UPDATED_PHONE)
            .googleMapsLocation(UPDATED_GOOGLE_MAPS_LOCATION)
            .width(UPDATED_WIDTH)
            .hasKitchen(UPDATED_HAS_KITCHEN)
            .hasDinningRoom(UPDATED_HAS_DINNING_ROOM)
            .postingTime(UPDATED_POSTING_TIME)
            .status(UPDATED_STATUS);

        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPost))
            )
            .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPost.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPost.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPost.getSquare()).isEqualTo(UPDATED_SQUARE);
        assertThat(testPost.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPost.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPost.getGoogleMapsLocation()).isEqualTo(UPDATED_GOOGLE_MAPS_LOCATION);
        assertThat(testPost.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testPost.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testPost.getDirection()).isEqualTo(DEFAULT_DIRECTION);
        assertThat(testPost.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testPost.getLegal()).isEqualTo(DEFAULT_LEGAL);
        assertThat(testPost.getNumberOfFloors()).isEqualTo(DEFAULT_NUMBER_OF_FLOORS);
        assertThat(testPost.getNumberOfBedroom()).isEqualTo(DEFAULT_NUMBER_OF_BEDROOM);
        assertThat(testPost.getHasKitchen()).isEqualTo(UPDATED_HAS_KITCHEN);
        assertThat(testPost.getHasDinningRoom()).isEqualTo(UPDATED_HAS_DINNING_ROOM);
        assertThat(testPost.getHasRooftop()).isEqualTo(DEFAULT_HAS_ROOFTOP);
        assertThat(testPost.getHasGarage()).isEqualTo(DEFAULT_HAS_GARAGE);
        assertThat(testPost.getIsVip()).isEqualTo(DEFAULT_IS_VIP);
        assertThat(testPost.getPostingTime()).isEqualTo(UPDATED_POSTING_TIME);
        assertThat(testPost.getExpiredTime()).isEqualTo(DEFAULT_EXPIRED_TIME);
        assertThat(testPost.getBrokerageFees()).isEqualTo(DEFAULT_BROKERAGE_FEES);
        assertThat(testPost.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPost.getStar()).isEqualTo(DEFAULT_STAR);
        assertThat(testPost.getHash()).isEqualTo(DEFAULT_HASH);
    }

    @Test
    @Transactional
    void fullUpdatePostWithPatch() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Update the post using partial update
        Post partialUpdatedPost = new Post();
        partialUpdatedPost.setId(post.getId());

        partialUpdatedPost
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .price(UPDATED_PRICE)
            .square(UPDATED_SQUARE)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .googleMapsLocation(UPDATED_GOOGLE_MAPS_LOCATION)
            .width(UPDATED_WIDTH)
            .length(UPDATED_LENGTH)
            .direction(UPDATED_DIRECTION)
            .distance(UPDATED_DISTANCE)
            .legal(UPDATED_LEGAL)
            .numberOfFloors(UPDATED_NUMBER_OF_FLOORS)
            .numberOfBedroom(UPDATED_NUMBER_OF_BEDROOM)
            .hasKitchen(UPDATED_HAS_KITCHEN)
            .hasDinningRoom(UPDATED_HAS_DINNING_ROOM)
            .hasRooftop(UPDATED_HAS_ROOFTOP)
            .hasGarage(UPDATED_HAS_GARAGE)
            .isVip(UPDATED_IS_VIP)
            .postingTime(UPDATED_POSTING_TIME)
            .expiredTime(UPDATED_EXPIRED_TIME)
            .brokerageFees(UPDATED_BROKERAGE_FEES)
            .status(UPDATED_STATUS)
            .star(UPDATED_STAR)
            .hash(UPDATED_HASH);

        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPost))
            )
            .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPost.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPost.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPost.getSquare()).isEqualTo(UPDATED_SQUARE);
        assertThat(testPost.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPost.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPost.getGoogleMapsLocation()).isEqualTo(UPDATED_GOOGLE_MAPS_LOCATION);
        assertThat(testPost.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testPost.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testPost.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testPost.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testPost.getLegal()).isEqualTo(UPDATED_LEGAL);
        assertThat(testPost.getNumberOfFloors()).isEqualTo(UPDATED_NUMBER_OF_FLOORS);
        assertThat(testPost.getNumberOfBedroom()).isEqualTo(UPDATED_NUMBER_OF_BEDROOM);
        assertThat(testPost.getHasKitchen()).isEqualTo(UPDATED_HAS_KITCHEN);
        assertThat(testPost.getHasDinningRoom()).isEqualTo(UPDATED_HAS_DINNING_ROOM);
        assertThat(testPost.getHasRooftop()).isEqualTo(UPDATED_HAS_ROOFTOP);
        assertThat(testPost.getHasGarage()).isEqualTo(UPDATED_HAS_GARAGE);
        assertThat(testPost.getIsVip()).isEqualTo(UPDATED_IS_VIP);
        assertThat(testPost.getPostingTime()).isEqualTo(UPDATED_POSTING_TIME);
        assertThat(testPost.getExpiredTime()).isEqualTo(UPDATED_EXPIRED_TIME);
        assertThat(testPost.getBrokerageFees()).isEqualTo(UPDATED_BROKERAGE_FEES);
        assertThat(testPost.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPost.getStar()).isEqualTo(UPDATED_STAR);
        assertThat(testPost.getHash()).isEqualTo(UPDATED_HASH);
    }

    @Test
    @Transactional
    void patchNonExistingPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeDelete = postRepository.findAll().size();

        // Delete the post
        restPostMockMvc
            .perform(delete(ENTITY_API_URL_ID, post.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
