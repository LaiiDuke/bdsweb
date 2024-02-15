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

    <div class="listing-page">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="naccs">
              <div class="grid">
                <div class="row">
                  <div class="col-lg-12">
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
import 'owl.carousel';
import { inject } from 'vue';
import PostService from '../../entities/post/post.service';
import PostTypeService from '../../entities/post-type/post-type.service';
export default {
  components: {
    PostComponent,
  },
  data() {
    return {
      lstPost: [],
      lstType: [],
      fetched: false,
      postTypeId: this.$route.params.postTypeId,
      postService: new PostService(),
      postTypeService: new PostTypeService(),
      page: 1,
      previousPage: 1,
      itemsPerPage: 3,
      queryCount: null,
      totalItems: 0,
    };
  },
  created() {
    this.getPostType();
  },
  watch: {},
  beforeRouteEnter(to, from, next) {
    next(vm => {});
  },
  methods: {
    getPaginatePost(postTypeId) {
      const paginationQuery = {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      };
      this.postService.retrieve(paginationQuery, postTypeId).then(
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
      this.postTypeService.retrieve().then(
        res => {
          this.lstType = res.data;
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
      return id === +this.postTypeId;
    },
  },
};
</script>
