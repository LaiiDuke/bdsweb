package com.duke.bds.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.duke.bds.IntegrationTest;
import com.duke.bds.domain.Category;
import com.duke.bds.domain.District;
import com.duke.bds.domain.Post;
import com.duke.bds.domain.PostType;
import com.duke.bds.domain.Province;
import com.duke.bds.domain.Street;
import com.duke.bds.domain.User;
import com.duke.bds.domain.enumeration.PostStatus;
import com.duke.bds.repository.PostRepository;
import com.duke.bds.service.PostService;
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

    private static final Long DEFAULT_SQUARE = 1L;
    private static final Long UPDATED_SQUARE = 2L;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_MAPS_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_MAPS_LOCATION = "BBBBBBBBBB";

    private static final Double DEFAULT_WIDTH = 1D;
    private static final Double UPDATED_WIDTH = 2D;

    private static final Double DEFAULT_LENGTH = 1D;
    private static final Double UPDATED_LENGTH = 2D;

    private static final String DEFAULT_DIRECTION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTION = "BBBBBBBBBB";

    private static final String DEFAULT_DISTANCE = "AAAAAAAAAA";
    private static final String UPDATED_DISTANCE = "BBBBBBBBBB";

    private static final String DEFAULT_LEGAL = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMBER_OF_FLOORS = 1L;
    private static final Long UPDATED_NUMBER_OF_FLOORS = 2L;

    private static final Long DEFAULT_NUMBER_OF_BEDROOM = 1L;
    private static final Long UPDATED_NUMBER_OF_BEDROOM = 2L;

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

    private static final LocalDate DEFAULT_EXPIRED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRED_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_BROKERAGE_FEES = 1D;
    private static final Double UPDATED_BROKERAGE_FEES = 2D;

    private static final PostStatus DEFAULT_STATUS = PostStatus.WAITING;
    private static final PostStatus UPDATED_STATUS = PostStatus.VERIFIED;

    private static final Double DEFAULT_STAR = 1D;
    private static final Double UPDATED_STAR = 2D;

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
