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
          <div class="col-lg-12">
            <form id="search-form" name="gs" method="submit" role="search" action="#">
              <div class="row">
                <div class="col-lg-3 align-self-center">
                  <fieldset>
                    <select name="area" class="form-select" aria-label="Area" id="chooseCategory" onchange="this.form.click()">
                      <option selected>All Areas</option>
                      <option value="New Village">New Village</option>
                      <option value="Old Town">Old Town</option>
                      <option value="Modern City">Modern City</option>
                    </select>
                  </fieldset>
                </div>
                <div class="col-lg-3 align-self-center">
                  <fieldset>
                    <input type="address" name="address" class="searchText" placeholder="Enter a location" autocomplete="on" required />
                  </fieldset>
                </div>
                <div class="col-lg-3 align-self-center">
                  <fieldset>
                    <select
                      name="price"
                      class="form-select"
                      aria-label="Default select example"
                      id="chooseCategory"
                      onchange="this.form.click()"
                    >
                      <option selected>Price Range</option>
                      <option value="$100 - $250">$100 - $250</option>
                      <option value="$250 - $500">$250 - $500</option>
                      <option value="$500 - $1000">$500 - $1,000</option>
                      <option value="$1000+">$1,000 or more</option>
                    </select>
                  </fieldset>
                </div>
                <div class="col-lg-3">
                  <fieldset>
                    <button class="main-button"><i class="fa fa-search"></i> Search Now</button>
                  </fieldset>
                </div>
              </div>
            </form>
          </div>
          <div class="col-lg-10 offset-lg-1">
            <ul class="categories">
              <li>
                <a href="#"
                  ><span class="icon"><img src="../../assets/images/search-icon-01.png" alt="Home" /></span> Apartments</a
                >
              </li>
              <li>
                <a href="/"
                  ><span class="icon"><img src="../../assets/images/search-icon-02.png" alt="Food" /></span> Food &amp; Life</a
                >
              </li>
              <li>
                <a href="#"
                  ><span class="icon"><img src="../../assets/images/search-icon-03.png" alt="Vehicle" /></span> Cars</a
                >
              </li>
              <li>
                <a href="#"
                  ><span class="icon"><img src="../../assets/images/search-icon-04.png" alt="Shopping" /></span> Shopping</a
                >
              </li>
              <li>
                <a href="#"
                  ><span class="icon"><img src="../../assets/images/search-icon-05.png" alt="Travel" /></span> Traveling</a
                >
              </li>
            </ul>
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
                      <div class="first-thumb">
                        <div class="thumb">
                          <span class="icon"><img src="../../assets/images/search-icon-01.png" alt="" /></span>
                          Apartments
                        </div>
                      </div>
                      <div>
                        <div class="thumb">
                          <span class="icon"><img src="../../assets/images/search-icon-02.png" alt="" /></span>
                          Food &amp; Life
                        </div>
                      </div>
                      <div>
                        <div class="thumb active">
                          <span class="icon"><img src="../../assets/images/search-icon-03.png" alt="" /></span>
                          Cars
                        </div>
                      </div>
                      <div>
                        <div class="thumb">
                          <span class="icon"><img src="../../assets/images/search-icon-04.png" alt="" /></span>
                          Shopping
                        </div>
                      </div>
                      <div class="last-thumb">
                        <div class="thumb">
                          <span class="icon"><img src="../../assets/images/search-icon-05.png" alt="" /></span>
                          Traveling
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-lg-9 align-self-center">
                    <ul class="nacc">
                      <li class="active">
                        <div>
                          <div class="thumb">
                            <div class="row">
                              <div class="col-lg-5 align-self-center">
                                <div class="left-text">
                                  <h4>One Of The Most Trending Stuffs Right Now!</h4>
                                  <p>
                                    Plot Listing is a responsive Bootstrap 5 website template that included 4 different HTML pages. This
                                    template is provided by TemplateMo website. You can apply this layout for your static or dynamic CMS
                                    websites.
                                  </p>
                                  <div class="main-white-button">
                                    <a href="#"><i class="fa fa-eye"></i> Discover More</a>
                                  </div>
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
                      <li>
                        <div>
                          <div class="thumb">
                            <div class="row">
                              <div class="col-lg-5 align-self-center">
                                <div class="left-text">
                                  <h4>Food and Lifestyle category is here</h4>
                                  <p>
                                    You can feel free to download, edit and apply this template for your website. Please tell your friends
                                    about TemplateMo website.
                                  </p>
                                  <div class="main-white-button">
                                    <a href="#"><i class="fa fa-eye"></i> Explore More</a>
                                  </div>
                                </div>
                              </div>
                              <div class="col-lg-7 align-self-center">
                                <div class="right-image">
                                  <img src="../../assets/images/tabs-image-02.jpg" alt="Foods on the table" />
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </li>
                      <li>
                        <div>
                          <div class="thumb">
                            <div class="row">
                              <div class="col-lg-5 align-self-center">
                                <div class="left-text">
                                  <h4>Best car rentals for your trips!</h4>
                                  <p>
                                    Did you know? You can get the best free HTML templates on Too CSS blog. Visit the blog pages and explore
                                    fresh and latest website templates.
                                  </p>
                                  <div class="main-white-button">
                                    <a href="#"><i class="fa fa-eye"></i> More Listing</a>
                                  </div>
                                </div>
                              </div>
                              <div class="col-lg-7 align-self-center">
                                <div class="right-image">
                                  <img src="../../assets/images/tabs-image-03.jpg" alt="cars in the city" />
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </li>
                      <li>
                        <div>
                          <div class="thumb">
                            <div class="row">
                              <div class="col-lg-5 align-self-center">
                                <div class="left-text">
                                  <h4>Shopping List: Images from Unsplash</h4>
                                  <p>
                                    Image credits go to Unsplash website that provides free stock photos for anyone. Images used in this
                                    Plot Listing template are from Unsplash.
                                  </p>
                                  <div class="main-white-button">
                                    <a href="#"><i class="fa fa-eye"></i> Discover More</a>
                                  </div>
                                </div>
                              </div>
                              <div class="col-lg-7 align-self-center">
                                <div class="right-image">
                                  <img src="../../assets/images/tabs-image-04.jpg" alt="Shopping Girl" />
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </li>
                      <li>
                        <div>
                          <div class="thumb">
                            <div class="row">
                              <div class="col-lg-5 align-self-center">
                                <div class="left-text">
                                  <h4>Information and Safety Tips for Traveling</h4>
                                  <p>
                                    You are allowed to use this template for your commercial websites. You are NOT allowed to redistribute
                                    this template ZIP file on any Free CSS collection websites.
                                  </p>
                                  <div class="main-white-button">
                                    <a rel="nofollow" href="https://templatemo.com/contact"><i class="fa fa-eye"></i> Read More</a>
                                  </div>
                                </div>
                              </div>
                              <div class="col-lg-7 align-self-center">
                                <div class="right-image">
                                  <img src="../../assets/images/tabs-image-05.jpg" alt="Traveling Beach" />
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
              <!--              <div class="row justify-content-center">-->
              <!--                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>-->
              <!--              </div>-->
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
      lstPost: [],
      page: 1,
      previousPage: 1,
      itemsPerPage: 10,
      queryCount: null,
      totalItems: 0,
    };
  },
  setup() {},
  created() {
    this.getPaginatePost();
    this.getVipPost();
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
  watch: {
    lstPost() {
      $('.owl-listing').owlCarousel({
        items: 1,
        loop: true,
        dots: true,
        nav: false,
        autoplay: true,
        margin: 30,
        responsive: {
          0: {
            items: 3,
          },
          600: {
            items: 2,
          },
          1000: {
            items: 1,
          },
          1600: {
            items: 1,
          },
        },
      });
    },
  },
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
          this.queryCount = this.totalItems;
          this.fetched = true;
        },
        err => {
          this.alertService().showHttpError(this, err.response);
        }
      );
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
  },
};
</script>
