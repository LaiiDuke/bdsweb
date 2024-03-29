import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPostType } from '@/shared/model/post-type.model';
import PostTypeService from './post-type.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PostTypeDetails extends Vue {
  @Inject('postTypeService') private postTypeService: () => PostTypeService;
  @Inject('alertService') private alertService: () => AlertService;

  public postType: IPostType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.postTypeId) {
        vm.retrievePostType(to.params.postTypeId);
      }
    });
  }

  public retrievePostType(postTypeId) {
    this.postTypeService()
      .find(postTypeId)
      .then(res => {
        this.postType = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
