import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required, maxLength, numeric } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import ImageService from '@/entities/image/image.service';
import { IImage } from '@/shared/model/image.model';

import PostTypeService from '@/entities/post-type/post-type.service';
import { IPostType } from '@/shared/model/post-type.model';

import CategoryService from '@/entities/category/category.service';
import { ICategory } from '@/shared/model/category.model';

import UserService from '@/entities/user/user.service';

import ProvinceService from '@/entities/province/province.service';
import { IProvince } from '@/shared/model/province.model';

import DistrictService from '@/entities/district/district.service';
import { IDistrict } from '@/shared/model/district.model';

import WardService from '@/entities/ward/ward.service';
import { IWard } from '@/shared/model/ward.model';

import StreetService from '@/entities/street/street.service';
import { IStreet } from '@/shared/model/street.model';

import { IPost, Post } from '@/shared/model/post.model';
import PostService from './post.service';
import { PostStatus } from '@/shared/model/enumerations/post-status.model';

const validations: any = {
  post: {
    title: {
      required,
      maxLength: maxLength(200),
    },
    content: {},
    price: {
      required,
      numeric,
    },
    square: {},
    address: {},
    googleMapsLocation: {},
    width: {},
    length: {},
    direction: {},
    distance: {},
    legal: {},
    numberOfFloors: {},
    numberOfBedroom: {},
    hasKitchen: {},
    hasDinningRoom: {},
    hasRooftop: {},
    hasGarage: {},
    isVip: {},
    postingTime: {},
    expiredTime: {},
    brokerageFees: {},
    status: {},
    star: {},
    hash: {},
    type: {
      required,
    },
    category: {
      required,
    },
    user: {
      required,
    },
    province: {
      required,
    },
    district: {
      required,
    },
    street: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class PostUpdate extends mixins(JhiDataUtils) {
  @Inject('postService') private postService: () => PostService;
  @Inject('alertService') private alertService: () => AlertService;

  public post: IPost = new Post();

  @Inject('imageService') private imageService: () => ImageService;

  public images: IImage[] = [];

  @Inject('postTypeService') private postTypeService: () => PostTypeService;

  public postTypes: IPostType[] = [];

  @Inject('categoryService') private categoryService: () => CategoryService;

  public categories: ICategory[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('provinceService') private provinceService: () => ProvinceService;

  public provinces: IProvince[] = [];

  @Inject('districtService') private districtService: () => DistrictService;

  public districts: IDistrict[] = [];

  @Inject('wardService') private wardService: () => WardService;

  public wards: IWard[] = [];

  @Inject('streetService') private streetService: () => StreetService;

  public streets: IStreet[] = [];
  public postStatusValues: string[] = Object.keys(PostStatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.postId) {
        vm.retrievePost(to.params.postId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.post.id) {
      this.postService()
        .update(this.post)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('bdswebApp.post.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.postService()
        .create(this.post)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('bdswebApp.post.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrievePost(postId): void {
    this.postService()
      .find(postId)
      .then(res => {
        this.post = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.imageService()
      .retrieve()
      .then(res => {
        this.images = res.data;
      });
    this.postTypeService()
      .retrieve()
      .then(res => {
        this.postTypes = res.data;
      });
    this.categoryService()
      .retrieve()
      .then(res => {
        this.categories = res.data;
      });

    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.provinceService()
      .retrieve()
      .then(res => {
        this.provinces = res.data;
      });
    this.districtService()
      .retrieve()
      .then(res => {
        this.districts = res.data;
      });
    this.wardService()
      .retrieve()
      .then(res => {
        this.wards = res.data;
      });
    this.streetService()
      .retrieve()
      .then(res => {
        this.streets = res.data;
      });
  }
}
