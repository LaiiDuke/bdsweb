import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStreet } from '@/shared/model/street.model';
import StreetService from './street.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class StreetDetails extends Vue {
  @Inject('streetService') private streetService: () => StreetService;
  @Inject('alertService') private alertService: () => AlertService;

  public street: IStreet = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.streetId) {
        vm.retrieveStreet(to.params.streetId);
      }
    });
  }

  public retrieveStreet(streetId) {
    this.streetService()
      .find(streetId)
      .then(res => {
        this.street = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
