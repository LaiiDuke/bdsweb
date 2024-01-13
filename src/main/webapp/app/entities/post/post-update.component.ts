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
    status: {},
    hash: {},
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
          const message = 'A Post is updated with identifier ' + param.id;
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
          const message = 'A Post is created with identifier ' + param.id;
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
  }
}
