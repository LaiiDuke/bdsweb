import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';
import LoginService from '@/account/login.service';
import { loadScript } from 'vue-plugin-load-script';
loadScript('../../vendor/jquery/jquery.min.js');
loadScript('../../vendor/bootstrap/js/bootstrap.bundle.min.js');
loadScript('../../assets/js/owl-carousel.js');
loadScript('../../assets/js/animation.js');
loadScript('../../assets/js/imagesloaded.js');
loadScript('../../assets/js/custom.js');
@Component
export default class Home extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public get username(): string {
    return this.$store.getters.account?.login ?? '';
  }
}
