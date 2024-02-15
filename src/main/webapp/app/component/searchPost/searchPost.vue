<template>
  <!-- Search-Section -->
  <div class="search-section">
    <div class="container">
      <form v-on:submit.prevent="search()">
        <div class="row">
          <div class="col-lg-2 col-md-3 col-sm-4">
            <p>Loại bài đăng</p>
            <b-form-select v-model="mutablePostType" :options="postTypeOptions" class="mb-3"> </b-form-select>
          </div>
          <div class="col-lg-2 col-md-3 col-sm-4">
            <p>Loại BDS</p>
            <b-form-select v-model="mutableCategory" :options="categoryOptions" class="mb-3"> </b-form-select>
          </div>
          <div class="col-lg-2 col-md-3 col-sm-4">
            <p>Tỉnh/Thành phố</p>
            <b-form-select v-model="mutableProvince" :options="provinceOptions" @change="changeProvince" class="mb-3">
              <template #first>
                <b-form-select-option :value="null">Tất cả</b-form-select-option>
              </template>
            </b-form-select>
          </div>
          <div class="col-lg-2 col-md-3 col-sm-4">
            <p>Quận/Huyện</p>
            <b-form-select v-model="mutableDistrict" :options="districtOptions" @change="changeDistrict" class="mb-3">
              <template #first>
                <b-form-select-option :value="null">Tất cả</b-form-select-option>
              </template>
            </b-form-select>
          </div>
          <div class="col-lg-2 col-md-3 col-sm-4">
            <p>Phường/Xã</p>
            <b-form-select v-model="mutableWard" :options="wardOptions" @change="changeWard" class="mb-3">
              <template #first>
                <b-form-select-option :value="null">Tất cả</b-form-select-option>
              </template>
            </b-form-select>
          </div>
          <div class="col-lg-2 col-md-3 col-sm-4">
            <p>Giá</p>
            <b-form-select v-model="priceRange" :options="priceOptions" class="mb-3"> </b-form-select>
          </div>
          <div class="col-lg-2 col-md-3 col-sm-4">
            <p>Diện tích</p>
            <b-form-select v-model="areaRange" :options="areaOptions" class="mb-3"> </b-form-select>
          </div>
          <div class="col-lg-2 col-md-3 col-sm-4">
            <p>Hướng</p>
            <b-form-select v-model="mutableDirection" :options="directionOptions" class="mb-3">
              <template #first>
                <b-form-select-option :value="null">Tất cả</b-form-select-option>
              </template>
            </b-form-select>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-2 col-md-3 col-sm-4">
            <input type="submit" value="Tìm kiếm" class="yellow-btn" />
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<!--<script lang="ts" src="./post.component.ts"></script>-->
<!-- Scripts -->
<script>
import PostTypeService from '../../entities/post-type/post-type.service';
import CategoryService from '../../entities/category/category.service';
import ProvinceService from '../../entities/province/province.service';
import DistrictService from '../../entities/district/district.service';
import WardService from '../../entities/ward/ward.service';
import PostService from '../../entities/post/post.service';

export default {
  name: 'SearchPostComponent',
  props: {
    postType: { type: String | Number, default: null },
    category: { type: String | Number, default: null },
    province: { type: String | Number, default: null },
    district: { type: String | Number, default: null },
    ward: { type: String | Number, default: null },
    price: { type: String | Number, default: null },
    area: { type: String | Number, default: null },
    direction: { type: String | Number, default: null },
  },
  data() {
    return {
      fetched: false,
      categoryService: new CategoryService(),
      postTypeService: new PostTypeService(),
      provinceService: new ProvinceService(),
      districtService: new DistrictService(),
      wardService: new WardService(),
      postService: new PostService(),
      postTypeOptions: [],
      categoryOptions: [],
      provinceOptions: [],
      districtOptions: [],
      districts: [],
      wardOptions: [],
      wards: [],
      page: 1,
      previousPage: 1,
      itemsPerPage: 3,
      queryCount: null,
      totalItems: 0,
      postResult: [],
      mutablePostType: this.postType,
      mutableCategory: this.category,
      mutableProvince: this.province,
      mutableDistrict: this.district,
      mutableWard: this.ward,
      priceRange: this.price,
      areaRange: this.area,
      mutableDirection: this.direction,
      priceOptions: [
        {
          value: null,
          text: 'Tất cả',
        },
        {
          value: 1,
          text: '0 - 1 triệu',
        },
        {
          value: 2,
          text: '1 - 3 triệu',
        },
        {
          value: 3,
          text: '3 - 5 triệu',
        },
        {
          value: 4,
          text: '5 - 10 triệu',
        },
        {
          value: 5,
          text: '10 - 15 triệu',
        },
        {
          value: 6,
          text: '15 - 20 triệu',
        },
        {
          value: 7,
          text: '20 - 30 triệu',
        },
        {
          value: 8,
          text: '30 - 40 triệu',
        },
        {
          value: 9,
          text: '40 - 60 triệu',
        },
        {
          value: 10,
          text: '60 - 80 triệu',
        },
        {
          value: 11,
          text: '80 - 100 triệu',
        },
        {
          value: 12,
          text: '100 - 300 triệu',
        },
        {
          value: 13,
          text: '300 - 500 triệu',
        },
        {
          value: 14,
          text: '500 - 800 triệu',
        },
        {
          value: 15,
          text: '800 - 1 tỷ',
        },
        {
          value: 16,
          text: '1 - 2 tỷ',
        },
        {
          value: 17,
          text: '2 - 3 tỷ',
        },
        {
          value: 18,
          text: '3 - 4 tỷ',
        },
        {
          value: 19,
          text: '4 - 6 tỷ',
        },
        {
          value: 20,
          text: '6 - 8 tỷ',
        },
        {
          value: 21,
          text: '8 - 10 tỷ',
        },
        {
          value: 22,
          text: '10 - 15 tỷ',
        },
        {
          value: 23,
          text: '15 - 20 tỷ',
        },
        {
          value: 24,
          text: '20 - 30 tỷ',
        },
        {
          value: 25,
          text: '30 - 60 tỷ',
        },
        {
          value: 26,
          text: 'Trên 60 tỷ',
        },
      ],
      areaOptions: [
        {
          value: null,
          text: 'Tất cả',
        },
        {
          value: 1,
          text: '0 - 30m2',
        },
        {
          value: 2,
          text: '30 - 50m2',
        },
        {
          value: 3,
          text: '50 - 70m2',
        },
        {
          value: 4,
          text: '70 - 100m2',
        },
        {
          value: 5,
          text: '100 - 150m2',
        },
        {
          value: 6,
          text: '150 - 200m2',
        },
        {
          value: 7,
          text: '200 - 250m2',
        },
        {
          value: 8,
          text: '250 - 300m2',
        },
        {
          value: 9,
          text: '300 - 350m2',
        },
        {
          value: 10,
          text: '350 - 400m2',
        },
        {
          value: 11,
          text: '400 - 600m2',
        },
        {
          value: 12,
          text: '600 - 800m2',
        },
        {
          value: 13,
          text: '800 - 1000m2',
        },
        {
          value: 14,
          text: 'Trên 1000m2',
        },
      ],
      directionOptions: [
        {
          value: 'D',
          text: 'Đông',
        },
        {
          value: 'DN',
          text: 'Đông Nam',
        },
        {
          value: 'DB',
          text: 'Đông Bắc',
        },
        {
          value: 'N',
          text: 'Nam',
        },
        {
          value: 'T',
          text: 'Tây',
        },
        {
          value: 'TN',
          text: 'Tây Nam',
        },
        {
          value: 'TB',
          text: 'Tây Bắc',
        },
        {
          value: 'B',
          text: 'Bắc',
        },
      ],
    };
  },
  created() {
    this.getPostType();
    this.getCategory();
    this.getProvince();
    this.getDistrict();
    // this.getWard();
  },
  watch: {},
  methods: {
    getPostType() {
      this.postTypeService.retrieve().then(
        res => {
          this.postTypeOptions = res.data.map(item => {
            return { value: item.id, text: item.name };
          });
        },
        err => {
          this.alertService().showHttpError(this, err.response);
        }
      );
    },
    getCategory() {
      this.categoryService.retrieve().then(
        res => {
          this.categoryOptions = res.data.map(item => {
            return { value: item.id, text: item.name };
          });
        },
        err => {
          this.alertService().showHttpError(this, err.response);
        }
      );
    },
    getProvince() {
      this.provinceService.retrieve().then(
        res => {
          this.provinceOptions = res.data.map(item => {
            return { value: item.id, text: item.name };
          });
        },
        err => {
          this.alertService().showHttpError(this, err.response);
        }
      );
    },
    getDistrict() {
      this.districtService
        .retrieve()
        .then(
          res => {
            this.districts = res.data;
            if (this.mutableProvince == null) {
              this.districtOptions = res.data.map(item => {
                return { value: item.id, text: item.name };
              });
            } else {
              //loc huyen theo tinh
              this.districtOptions = res.data
                .filter(element => element.province.id === +this.mutableProvince)
                .map(item => {
                  return { value: item.id, text: item.name };
                });
            }
          },
          err => {
            this.alertService().showHttpError(this, err.response);
          }
        )
        .then(() => {
          this.getWard();
        });
    },
    getWard() {
      this.wardService.retrieve().then(
        res => {
          this.wards = res.data;
          if (this.mutableDistrict == null) {
            if (this.districtOptions == null || this.districtOptions.length === 0) {
              this.wardOptions = res.data.map(item => {
                return { value: item.id, text: item.name };
              });
            } else {
              this.wardOptions = res.data
                .filter(element => this.districtOptions.some(district => district.value === element.district.id))
                .map(item => {
                  return { value: item.id, text: item.name };
                });
            }
          } else {
            //loc xa theo huyen
            this.wardOptions = res.data
              .filter(element => element.district.id === +this.mutableDistrict)
              .map(item => {
                return { value: item.id, text: item.name };
              });
          }
        },
        err => {
          this.alertService().showHttpError(this, err.response);
        }
      );
    },
    search() {
      const queryParam = {
        ward: this.mutableWard,
        district: this.mutableDistrict,
        province: this.mutableProvince,
        price: this.priceRange,
        area: this.areaRange,
        direction: this.mutableDirection,
      };
      this.$router.replace({
        name: 'ListProduct',
        params: { postTypeId: this.mutablePostType, categoryId: this.mutableCategory },
        query: queryParam,
      });
    },
    changeProvince() {
      this.mutableDistrict = null;
      this.mutableWard = null;
      //loc huyen theo tinh
      const arrDistrict = this.districts
        .filter(element => element.province.id === +this.mutableProvince)
        .map(item => {
          return { value: item.id, text: item.name };
        });
      this.districtOptions = arrDistrict;
      //loc xa theo huyen
      this.wardOptions = this.wards
        .filter(element => arrDistrict.some(district => district.id === element.district.id))
        .map(item => {
          return { value: item.id, text: item.name };
        });
    },
    changeDistrict() {
      this.mutableWard = null;
      //loc xa theo huyen
      this.wardOptions = this.wards
        .filter(element => element.district.id === +this.mutableDistrict)
        .map(item => {
          return { value: item.id, text: item.name };
        });

      if (this.mutableProvince == null) {
        const provinceId = this.districts.find(element => element.id === +this.mutableDistrict)?.province.id;
        this.mutableProvince = provinceId;
        //loc huyen theo tinh
        this.districtOptions = this.districts
          .filter(element => element.province.id === provinceId)
          .map(item => {
            return { value: item.id, text: item.name };
          });
      }
    },
    changeWard() {
      if (this.mutableDistrict == null) {
        //lay ma huyen tuong ung
        const districtId = this.wards.find(element => element.id === +this.mutableWard)?.district.id;
        this.mutableDistrict = districtId;

        //loc xa theo huyen
        this.wardOptions = this.wards
          .filter(element => element.district.id === districtId)
          .map(item => {
            return { value: item.id, text: item.name };
          });

        if (this.mutableProvince == null) {
          const provinceId = this.districts.find(element => element.id === districtId)?.province.id;
          this.mutableProvince = provinceId;
          //loc huyen theo tinh
          this.districtOptions = this.districts
            .filter(element => element.province.id === provinceId)
            .map(item => {
              return { value: item.id, text: item.name };
            });
        }
      }
    },
    sort() {
      return ['postingTime,desc'];
    },
  },
};
</script>
