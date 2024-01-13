import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUserInfo } from '@/shared/model/user-info.model';
import UserInfoService from './user-info.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class UserInfoDetails extends Vue {
  @Inject('userInfoService') private userInfoService: () => UserInfoService;
  @Inject('alertService') private alertService: () => AlertService;

  public userInfo: IUserInfo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userInfoId) {
        vm.retrieveUserInfo(to.params.userInfoId);
      }
    });
  }

  public retrieveUserInfo(userInfoId) {
    this.userInfoService()
      .find(userInfoId)
      .then(res => {
        this.userInfo = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
