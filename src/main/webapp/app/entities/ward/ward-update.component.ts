import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import StreetService from '@/entities/street/street.service';
import { IStreet } from '@/shared/model/street.model';

import DistrictService from '@/entities/district/district.service';
import { IDistrict } from '@/shared/model/district.model';

import { IWard, Ward } from '@/shared/model/ward.model';
import WardService from './ward.service';

const validations: any = {
  ward: {
    name: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class WardUpdate extends Vue {
  @Inject('wardService') private wardService: () => WardService;
  @Inject('alertService') private alertService: () => AlertService;

  public ward: IWard = new Ward();

  @Inject('streetService') private streetService: () => StreetService;

  public streets: IStreet[] = [];

  @Inject('districtService') private districtService: () => DistrictService;

  public districts: IDistrict[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.wardId) {
        vm.retrieveWard(to.params.wardId);
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
    if (this.ward.id) {
      this.wardService()
        .update(this.ward)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Ward is updated with identifier ' + param.id;
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
      this.wardService()
        .create(this.ward)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Ward is created with identifier ' + param.id;
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

  public retrieveWard(wardId): void {
    this.wardService()
      .find(wardId)
      .then(res => {
        this.ward = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.streetService()
      .retrieve()
      .then(res => {
        this.streets = res.data;
      });
    this.districtService()
      .retrieve()
      .then(res => {
        this.districts = res.data;
      });
  }
}
