<template>
  <div>
    <div class="page-heading">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="top-text header-text">
              <h6>Check Out Our Listings</h6>
              <h2>Item listings of Different Categories</h2>
            </div>
          </div>
        </div>
      </div>
    </div>
    <search-post-component
      :postType="postTypeId"
      :category="categoryId"
      :province="province"
      :district="district"
      :ward="ward"
      :price="price"
      :area="area"
      :direction="direction"
      @set-result="setLstPost"
      @post-type="setPostType"
      @category="setCategory"
    ></search-post-component>

    <div class="listing-page">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="naccs">
              <div class="grid">
                <div class="row">
                  <div class="col-lg-3">
                    <div class="list-product">
                      <div
                        v-for="(item, index) in lstCategory"
                        :key="index"
                        :class="checkActive(item.id) ? `active` : ''"
                        :title="item.name"
                      >
                        <router-link :to="{ name: 'ListProduct', params: { categoryId: item.id } }">
                          <div class="thumb">
                            <span class="icon"><img src="../../assets/images/search-icon-02.png" alt="" /></span>
                            {{ item.name }}
                          </div>
                        </router-link>
                      </div>
                    </div>
                  </div>
                  <div class="col-lg-9">
                    <ul class="nacc">
                      <!-- first category listing of items -->
                      <li class="active">
                        <div class="col-lg-12">
                          <div class="">
                            <div class="container">
                              <div class="row">
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
  </div>
</template>

<!--<script lang="ts" src="./home.component.ts"></script>-->
<!-- Scripts -->
<script>
import jQuery from '../../vendor/jquery/jquery.min.js';
import PostComponent from '@/component/post/post.vue';
import SearchPostComponent from '@/component/searchPost/searchPost.vue';
import 'owl.carousel';
import { inject } from 'vue';
import PostService from '../../entities/post/post.service';
import PostTypeService from '../../entities/post-type/post-type.service';
import CategoryService from '../../entities/category/category.service';
export default {
  components: {
    PostComponent,
    SearchPostComponent,
  },
  data() {
    return {
      lstPost: [],
      lstCategory: [],
      fetched: false,
      postTypeId: this.$route.params.postTypeId,
      categoryId: this.$route.params.categoryId,
      province: this.$route.query.province,
      district: this.$route.query.district,
      ward: this.$route.query.ward,
      price: this.$route.query.price,
      area: this.$route.query.area,
      direction: this.$route.query.direction,
      postService: new PostService(),
      postTypeService: new PostTypeService(),
      categoryService: new CategoryService(),
      page: 1,
      previousPage: 1,
      itemsPerPage: 3,
      queryCount: null,
      totalItems: 0,
      categoryOptions: [],
    };
  },
  created() {
    this.getCategory();
  },
  watch: {
    $route: {
      handler: async function (value) {
        console.log(value);
        this.postTypeId = this.$route.params.postTypeId;
        this.categoryId = this.$route.params.categoryId;
        this.province = this.$route.query.province;
        this.district = this.$route.query.district;
        this.ward = this.$route.query.ward;
        this.price = this.$route.query.price;
        this.area = this.$route.query.area;
        this.direction = this.$route.query.direction;
        await this.getPaginatePost();
      },
      deep: true,
      immediate: true,
    },
  },
  methods: {
    getPaginatePost() {
      const paginationQuery = {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      };
      let minPrice, maxPrice;
      switch (this.price) {
        case 0:
          minPrice = maxPrice = null;
          break;
        case 1:
          minPrice = 0;
          maxPrice = 1000000;
          break;
        case 2:
          minPrice = 1000000;
          maxPrice = 3000000;
          break;
        case 3:
          minPrice = 3000000;
          maxPrice = 5000000;
          break;
        case 4:
          minPrice = 5000000;
          maxPrice = 10000000;
          break;
        case 5:
          minPrice = 10000000;
          maxPrice = 15000000;
          break;
        case 6:
          minPrice = 15000000;
          maxPrice = 20000000;
          break;
        case 7:
          minPrice = 20000000;
          maxPrice = 30000000;
          break;
        case 8:
          minPrice = 30000000;
          maxPrice = 40000000;
          break;
        case 9:
          minPrice = 40000000;
          maxPrice = 60000000;
          break;
        case 10:
          minPrice = 60000000;
          maxPrice = 80000000;
          break;
        case 11:
          minPrice = 80000000;
          maxPrice = 100000000;
          break;
        case 12:
          minPrice = 100000000;
          maxPrice = 300000000;
          break;
        case 13:
          minPrice = 300000000;
          maxPrice = 500000000;
          break;
        case 14:
          minPrice = 500000000;
          maxPrice = 800000000;
          break;
        case 15:
          minPrice = 800000000;
          maxPrice = 1000000000;
          break;
        case 16:
          minPrice = 1000000000;
          maxPrice = 2000000000;
          break;
        case 17:
          minPrice = 2000000000;
          maxPrice = 3000000000;
          break;
        case 18:
          minPrice = 3000000000;
          maxPrice = 4000000000;
          break;
        case 19:
          minPrice = 4000000000;
          maxPrice = 6000000000;
          break;
        case 20:
          minPrice = 6000000000;
          maxPrice = 8000000000;
          break;
        case 21:
          minPrice = 8000000000;
          maxPrice = 10000000000;
          break;
        case 22:
          minPrice = 10000000000;
          maxPrice = 15000000000;
          break;
        case 23:
          minPrice = 15000000000;
          maxPrice = 20000000000;
          break;
        case 24:
          minPrice = 20000000000;
          maxPrice = 30000000000;
          break;
        case 25:
          minPrice = 30000000000;
          maxPrice = 60000000000;
          break;
        case 26:
          minPrice = 60000000000;
          maxPrice = null;
          break;
      }

      let minArea, maxArea;
      switch (this.area) {
        case 0:
          minArea = maxArea = null;
          break;
        case 1:
          minArea = 0;
          maxArea = 30;
          break;
        case 2:
          minArea = 30;
          maxArea = 50;
          break;
        case 3:
          minArea = 50;
          maxArea = 70;
          break;
        case 4:
          minArea = 70;
          maxArea = 100;
          break;
        case 5:
          minArea = 100;
          maxArea = 150;
          break;
        case 6:
          minArea = 150;
          maxArea = 200;
          break;
        case 7:
          minArea = 200;
          maxArea = 250;
          break;
        case 8:
          minArea = 250;
          maxArea = 300;
          break;
        case 9:
          minArea = 300;
          maxArea = 350;
          break;
        case 10:
          minArea = 350;
          maxArea = 400;
          break;
        case 11:
          minArea = 400;
          maxArea = 600;
          break;
        case 12:
          minArea = 600;
          maxArea = 800;
          break;
        case 13:
          minArea = 800;
          maxArea = 1000;
          break;
        case 14:
          minArea = 1000;
          maxArea = null;
          break;
      }
      const queryParam = {
        'postTypeId.equals': this.postTypeId,
        'categoryId.equals': this.categoryId,
        'wardId.equals': this.ward,
        'districtId.equals': this.district,
        'provinceId.equals': this.province,
        'direction.equals': this.direction,
        'price.greaterThanOrEqual': minPrice,
        'price.lessThanOrEqual': maxPrice,
        'square.greaterThanOrEqual': minArea,
        'square.lessThanOrEqual': maxArea,
      };
      this.postService.retrieve(paginationQuery, queryParam).then(
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
    getCategory() {
      this.categoryService.retrieve().then(
        res => {
          this.lstCategory = res.data;
          this.categoryOptions = res.data.map(item => {
            return { value: item.id, text: item.name };
          });
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
    checkActive(id) {
      return id === +this.categoryId;
    },
    setLstPost(posts) {
      this.lstPost = posts;
    },
    setPostType(type) {
      this.postTypeId = type;
    },
    setCategory(category) {
      this.categoryId = category;
    },
  },
};
</script>
