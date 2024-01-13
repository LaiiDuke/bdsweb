import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IConfig, Config } from '@/shared/model/config.model';
import ConfigService from './config.service';

const validations: any = {
  config: {
    code: {
      required,
    },
    value: {
      required,
    },
    description: {},
  },
};

@Component({
  validations,
})
export default class ConfigUpdate extends Vue {
  @Inject('configService') private configService: () => ConfigService;
  @Inject('alertService') private alertService: () => AlertService;

  public config: IConfig = new Config();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.configId) {
        vm.retrieveConfig(to.params.configId);
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
    if (this.config.id) {
      this.configService()
        .update(this.config)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Config is updated with identifier ' + param.id;
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
      this.configService()
        .create(this.config)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Config is created with identifier ' + param.id;
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

  public retrieveConfig(configId): void {
    this.configService()
      .find(configId)
      .then(res => {
        this.config = res;
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
