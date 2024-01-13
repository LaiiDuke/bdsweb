import { Component, Vue, Inject } from 'vue-property-decorator';

import { IWard } from '@/shared/model/ward.model';
import WardService from './ward.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class WardDetails extends Vue {
  @Inject('wardService') private wardService: () => WardService;
  @Inject('alertService') private alertService: () => AlertService;

  public ward: IWard = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.wardId) {
        vm.retrieveWard(to.params.wardId);
      }
    });
  }

  public retrieveWard(wardId) {
    this.wardService()
      .find(wardId)
      .then(res => {
        this.ward = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
