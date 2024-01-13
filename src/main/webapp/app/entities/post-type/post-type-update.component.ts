import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IPostType, PostType } from '@/shared/model/post-type.model';
import PostTypeService from './post-type.service';

const validations: any = {
  postType: {
    name: {
      required,
    },
    description: {},
  },
};

@Component({
  validations,
})
export default class PostTypeUpdate extends Vue {
  @Inject('postTypeService') private postTypeService: () => PostTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public postType: IPostType = new PostType();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.postTypeId) {
        vm.retrievePostType(to.params.postTypeId);
      }
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
    if (this.postType.id) {
      this.postTypeService()
        .update(this.postType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A PostType is updated with identifier ' + param.id;
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
      this.postTypeService()
        .create(this.postType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A PostType is created with identifier ' + param.id;
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

  public retrievePostType(postTypeId): void {
    this.postTypeService()
      .find(postTypeId)
      .then(res => {
        this.postType = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
