<template>
  <div>
    <div class="page-heading">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="top-text header-text">
              <h6>Keep in touch with us</h6>
              <h2>Feel free to send us a message about your business needs</h2>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- content-Section -->
    <div class="content-section">
      <div class="container">
        <div class="row">
          <div class="col-md-8 page-content">
            <div class="inner-wrapper">
              <div class="property-images-slider">
                <b-carousel
                  id="carousel-1"
                  v-model="slide"
                  :interval="4000"
                  controls
                  indicators
                  background="#ababab"
                  img-width="680px"
                  img-height="300px"
                  style="text-shadow: 1px 1px 2px #333"
                >
                  <b-carousel-slide v-for="(item, index) in images" :key="index" :img-src="`data:image/png;base64,` + item.data">
                  </b-carousel-slide>
                </b-carousel>
              </div>
              <div class="property-desc">
                <h3>2211 Summer Ridge Dr</h3>
                <ul class="slide-item-features item-features">
                  <li><span class="fa fa-arrows-alt"></span>Diện tích: {{ postObj.square }} m2</li>
                  <li><span class="fa fa-building-o"></span>{{ postObj.numberOfFloors }} tầng</li>
                  <li><span class="fa fa-inbox"></span>{{ postObj.numberOfBedroom }} phòng ngủ</li>
                </ul>
                <p class="first-paragraph">
                  {{ postObj.content }}
                </p>

                <div class="additional-features">
                  <h3>Thông tin liên quan</h3>
                  <ul class="features">
                    <li class="single-feature">
                      <router-link
                        v-if="postObj.type.id"
                        :to="{ name: 'ListProduct', params: { postTypeId: postObj.type.id } }"
                        custom
                        v-slot="{ navigate }"
                        :key="postObj.id"
                      >
                        <a @click="navigate"><i class="fa fa-check-circle"></i>{{ postObj.type.name }}</a>
                      </router-link>
                    </li>
                    <li class="single-feature">
                      <router-link
                        v-if="postObj.category.id"
                        :to="{ name: 'CategoryPost', params: { categoryId: postObj.category.id } }"
                        custom
                        v-slot="{ navigate }"
                        :key="postObj.id"
                      >
                        <a @click="navigate"><i class="fa fa-check-circle"></i>{{ postObj.category.name }}</a>
                      </router-link>
                    </li>
                    <li class="single-feature">
                      <a href="#"><i class="fa fa-check-circle"></i>Hướng {{ postObj.direction }}</a>
                    </li>
                    <li class="single-feature">
                      <a href="#"><i class="fa fa-check-circle"></i>Đường trước nhà {{ postObj.distance }}m</a>
                    </li>
                    <li class="single-feature" v-if="postObj.hasDinningRoom">
                      <a href="#"><i class="fa fa-check-circle"></i>Có phòng ăn</a>
                    </li>
                    <li class="single-feature" v-if="postObj.hasKitchen">
                      <a href="#"><i class="fa fa-check-circle"></i>Có nhà bếp</a>
                    </li>
                    <li class="single-feature" v-if="postObj.hasRooftop">
                      <a href="#"><i class="fa fa-check-circle"></i>Có sân thượng</a>
                    </li>
                    <li class="single-feature" v-if="postObj.hasGarage">
                      <a href="#"><i class="fa fa-check-circle"></i>Có chỗ để oto</a>
                    </li>
                  </ul>
                </div>
                <div class="property-location">
                  <h3>Property Location</h3>
                  <div id="property-location-map"></div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-4 blog-sidebar">
            <div class="sidebar-widget author-profile">
              <h4 class="widget-title">Liên hệ</h4>
              <div class="image-box">
                <img src="../../assets/images/property/49_author-avatar.png" alt="agent" />
                <!--                <ul class="social-icons">-->
                <!--                  <li><a href="#" class="fa fa-google-plus"></a></li>-->
                <!--                  <li><a href="#" class="fa fa-twitter"></a></li>-->
                <!--                  <li><a href="#" class="fa fa-pinterest"></a></li>-->
                <!--                  <li><a href="#" class="fa fa-dribbble"></a></li>-->
                <!--                  <li><a href="#" class="fa fa-linkedin"></a></li>-->
                <!--                  <li><a href="#" class="fa fa-facebook"></a></li>-->
                <!--                </ul>-->
              </div>
              <div class="desc-box">
                <h4>{{ userInfo.name }}</h4>
                <p class="person-number"><i class="fa fa-phone"></i> {{ userInfo.phone }}</p>
              </div>
            </div>
            <!--            <div class="sidebar-widget similar-listings-widget">-->
            <!--              <h4 class="widget-title">similar listings</h4>-->
            <!--              <ul class="similar-listings">-->
            <!--                <li class="tab-content-item">-->
            <!--                  <div class="pull-left thumb">-->
            <!--                    <img src="../../assets/images/blog/25_blog-thumb_1.png" alt="thumbnail" />-->
            <!--                  </div>-->
            <!--                  <h5><a href="#">Envato’s Most Wanted: Courses & Learning Themes - $7,000</a></h5>-->
            <!--                </li>-->
            <!--                <li class="tab-content-item">-->
            <!--                  <div class="pull-left thumb">-->
            <!--                    <img src="../../assets/images/blog/26_blog-thumb_2.png" alt="thumbnail" />-->
            <!--                  </div>-->
            <!--                  <h5><a href="#">Envato’s Most Wanted: Courses & Learning Themes - $7,000</a></h5>-->
            <!--                </li>-->
            <!--                <li class="tab-content-item">-->
            <!--                  <div class="pull-left thumb">-->
            <!--                    <img src="../../assets/images/blog/27_blog-thumb_3.png" alt="thumbnail" />-->
            <!--                  </div>-->
            <!--                  <h5><a href="#">Envato’s Most Wanted: Courses & Learning Themes - $7,000</a></h5>-->
            <!--                </li>-->
            <!--              </ul>-->
            <!--            </div>-->
            <!--            <div class="sidebar-widget text-widget">-->
            <!--              <h4 class="widget-title">Text Widget</h4>-->
            <!--              <p class="first-paragraph">-->
            <!--                This is Photoshop's version of Lorem Ipsum. Proin gravida nibh vel velit auctor aliquet. Aenean sollicitudin, lorem quis-->
            <!--                bibendum-->
            <!--              </p>-->
            <!--              <p>auctor, nisi elit consequat ipsum, nec sagittis sem nibh id elit. Duis sed odio sit</p>-->
            <!--            </div>-->
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
import 'owl.carousel';
import '../../assets/css/bootstrap.min.css';
import '../../assets/css/bootstrap-theme.min.css';
import '../../assets/css/font-awesome.min.css';
import '../../assets/css/flexslider.css';
import '../../assets/css/select-theme-default.css';
import '../../assets/css/style.css';
import '../../assets/css/master.scss';
import PostService from '../../entities/post/post.service';
import ImageService from '../../entities/image/image.service';
import UserService from '../../entities/user/user.service';
import UserInfoService from '../../entities/user-info/user-info.service';
export default {
  components: {},
  data() {
    return {
      slide: 0,
      postService: new PostService(),
      imageService: new ImageService(),
      userInfoService: new UserInfoService(),
      postObj: { type: {} },
      images: [{ data: '' }],
      userInfo: {},
    };
  },
  created() {
    jQuery(document).ready(function ($) {});
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.postId) {
        vm.getDetailPost(to.params.postId);
        vm.getImagesOfPost(to.params.postId);
      }
    });
  },
  methods: {
    getDetailPost(postId) {
      this.isFetching = true;
      this.postService.find(postId).then(
        res => {
          this.postObj = res;
          this.getUserInfo(res.user.id);
        },
        err => {}
      );
    },

    getImagesOfPost(postId) {
      this.imageService.findByPostId(postId).then(
        res => {
          this.images = res;
        },
        err => {}
      );
    },

    getUserInfo(userId) {
      this.userInfoService.find(userId).then(
        res => {
          this.userInfo = res;
        },
        err => {}
      );
    },
  },

  watch: {},
};
</script>
