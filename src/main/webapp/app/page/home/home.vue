<template>
  <div>
    <div class="main-banner">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="top-text header-text">
              <h6>Over 36,500+ Active Listings</h6>
              <h2>Find Nearby Places &amp; Things</h2>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="popular-categories">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="section-heading">
              <h2>Popular Categories</h2>
              <h6>Check Them Out</h6>
            </div>
          </div>
          <div class="col-lg-12">
            <div class="naccs">
              <div class="grid">
                <div class="row">
                  <div class="col-lg-3">
                    <div class="menu">
                      <div v-for="(item, index) in lstType" :key="index" :class="index === 0 ? 'active' : ''">
                        <div class="thumb">
                          <span class="icon"><img src="../../assets/images/search-icon-01.png" alt="" /></span>
                          {{ item.name }}
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-lg-9 align-self-center">
                    <ul class="nacc">
                      <li v-for="(item, index) in lstType" :key="index" :class="index === 0 ? 'active' : ''">
                        <div>
                          <div class="thumb">
                            <div class="row">
                              <div class="col-lg-5 align-self-center">
                                <div class="left-text">
                                  <h4>{{ item.name }}</h4>
                                  <p>
                                    {{ item.description }}
                                  </p>
                                  <router-link :to="{ name: 'PostTypeList', params: { postTypeId: item.id } }" custom v-slot="{ navigate }">
                                    <div class="main-white-button">
                                      <a @click="navigate"><i class="fa fa-eye"></i>Xem ngay </a>
                                    </div>
                                  </router-link>
                                </div>
                              </div>
                              <div class="col-lg-7 align-self-center">
                                <div class="right-image">
                                  <img src="../../assets/images/tabs-image-01.jpg" alt="" />
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="recent-listing">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="section-heading">
              <h2>Recent Listing</h2>
              <h6>Check Them Out</h6>
            </div>
          </div>
          <div class="col-lg-12">
            <div class="owl-carousel owl-listing">
              <div class="item">
                <div class="row">
                  <post-component v-for="(item, ind) in vipPost.slice(0, 3)" :key="ind" :post-obj="item"></post-component>
                </div>
              </div>
              <div class="item">
                <div class="row">
                  <post-component v-for="(item, ind) in vipPost.slice(3, 6)" :key="ind" :post-obj="item"></post-component>
                </div>
              </div>
              <div class="item">
                <div class="row">
                  <post-component v-for="(item, ind) in vipPost.slice(6, 9)" :key="ind" :post-obj="item"></post-component>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="recent-listing">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="section-heading">
              <h2>post</h2>
              <h6>Check Them Out</h6>
            </div>
          </div>
          <div class="owl-carousel owl-listing">
            <div class="item" v-if="fetched">
              <div class="row">
                <post-component v-for="(item, ind) in lstPost" :key="ind" :post-obj="item"></post-component>
              </div>
            </div>
            <div v-show="lstPost && lstPost.length > 0">
              <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
              </div>
              <div class="row justify-content-center">
                <b-pagination
                  size="md"
                  :total-rows="totalItems"
                  v-model="page"
                  pills
                  :per-page="itemsPerPage"
                  :change="loadPage(page)"
                ></b-pagination>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<!--<script lang="ts" src="./home.component.ts"></script>-->
<!-- Scripts -->
<script>
import jQuery from '../../vendor/jquery/jquery.min.js';
import PostComponent from '@/component/post/post.vue';
import '../../assets/js/owl-carousel';
import 'owl.carousel';
import PostService from '../../entities/post/post.service';
import PostTypeService from '../../entities/post-type/post-type.service';
import CategoryService from '../../entities/category/category.service';

export default {
  components: {
    PostComponent,
  },
  data() {
    return {
      vipPost: [],
      fetched: false,
      refresh: 0,
      numberOfSlide: 0,
      postService: new PostService(),
      postTypeService: new PostTypeService(),
      categoryService: new CategoryService(),
      lstPost: [],
      lstType: [],
      lstCategory: [],
      searchObj: {},
      page: 1,
      previousPage: 1,
      itemsPerPage: 3,
      queryCount: null,
      totalItems: 0,
    };
  },
  setup() {},
  created() {
    this.getPaginatePost();
    this.getVipPost();
    this.getPostType();
    this.getCategory();
    jQuery(document).ready(function ($) {
      // Acc
      $(document).on('click', '.naccs .menu div', function () {
        var numberIndex = $(this).index();
        console.log('clicked');
        if (!$(this).is('active')) {
          $('.naccs .menu div').removeClass('active');
          $('.naccs ul li').removeClass('active');

          $(this).addClass('active');
          $('.naccs ul')
            .find('li:eq(' + numberIndex + ')')
            .addClass('active');

          var listItemHeight = $('.naccs ul')
            .find('li:eq(' + numberIndex + ')')
            .innerHeight();
          $('.naccs ul').height(listItemHeight + 'px');
        }
      });

      // Menu Dropdown Toggle
      if ($('.menu-trigger').length) {
        $('.menu-trigger').on('click', function () {
          $(this).toggleClass('active');
          $('.header-area .nav').slideToggle(200);
        });
      }

      // Page loading animation
      $(window).on('load', function () {
        $('#js-preloader').addClass('loaded');
      });
    });
  },
  watch: {},
  methods: {
    getVipPost() {
      const paginationQuery = {
        page: 0,
        size: 12,
        sort: this.sort(),
      };
      this.postService.getVipPost(paginationQuery).then(
        res => {
          this.vipPost = res.data;
          this.queryCount = this.totalItems;
          this.numberOfSlide = res.data.length / 2;
          this.refresh++;
        },
        err => {
          this.alertService().showHttpError(this, err.response);
        }
      );
    },
    getPaginatePost() {
      const paginationQuery = {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      };
      this.postService.retrieve(paginationQuery).then(
        res => {
          this.lstPost = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.fetched = true;
        },
        err => {
          this.alertService().showHttpError(this, err.response);
        }
      );
    },
    getPostType() {
      this.postTypeService.retrieve().then(res => {
        this.lstType = res.data;
      });
    },
    getCategory() {
      this.categoryService.retrieve().then(_res => {
        this.lstCategory = _res.data;
      });
    },
    sort() {
      return ['postingTime,desc'];
    },
    loadPage(page) {
      if (page !== this.previousPage) {
        this.previousPage = page;
        this.transition();
      }
    },
    transition() {
      this.getPaginatePost();
    },
    search() {},
  },
};
</script>
