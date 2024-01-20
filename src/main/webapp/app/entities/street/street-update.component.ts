import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import WardService from '@/entities/ward/ward.service';
import { IWard } from '@/shared/model/ward.model';

import DistrictService from '@/entities/district/district.service';
import { IDistrict } from '@/shared/model/district.model';

import { IStreet, Street } from '@/shared/model/street.model';
import StreetService from './street.service';
import { PostStatus } from '@/shared/model/enumerations/post-status.model';

const validations: any = {
  street: {
    name: {
      required,
    },
    status: {},
    district: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class StreetUpdate extends Vue {
  @Inject('streetService') private streetService: () => StreetService;
  @Inject('alertService') private alertService: () => AlertService;

  public street: IStreet = new Street();

  @Inject('wardService') private wardService: () => WardService;

  public wards: IWard[] = [];

  @Inject('districtService') private districtService: () => DistrictService;

  public districts: IDistrict[] = [];
  public postStatusValues: string[] = Object.keys(PostStatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.streetId) {
        vm.retrieveStreet(to.params.streetId);
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
    if (this.street.id) {
      this.streetService()
        .update(this.street)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('bdswebApp.street.updated', { param: param.id });
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
      this.streetService()
        .create(this.street)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('bdswebApp.street.created', { param: param.id });
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

  public retrieveStreet(streetId): void {
    this.streetService()
      .find(streetId)
      .then(res => {
        this.street = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.wardService()
      .retrieve()
      .then(res => {
        this.wards = res.data;
      });
    this.districtService()
      .retrieve()
      .then(res => {
        this.districts = res.data;
      });
  }
}
