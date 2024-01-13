import { Component, Vue, Inject } from 'vue-property-decorator';

import { IConfig } from '@/shared/model/config.model';
import ConfigService from './config.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ConfigDetails extends Vue {
  @Inject('configService') private configService: () => ConfigService;
  @Inject('alertService') private alertService: () => AlertService;

  public config: IConfig = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.configId) {
        vm.retrieveConfig(to.params.configId);
      }
    });
  }

  public retrieveConfig(configId) {
    this.configService()
      .find(configId)
      .then(res => {
        this.config = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
