import Vue from 'vue';
import Component from 'vue-class-component';
import Ribbon from '@/core/ribbon/ribbon.vue';
import JhiFooter from '@/core/jhi-footer/jhi-footer.vue';
import JhiNavbar from '@/core/jhi-navbar/jhi-navbar.vue';
import LoginForm from '@/account/login-form/login-form.vue';
import UserFooter from '@/core/user-footer/user-footer.vue';
import UserNavbar from '@/core/user-navbar/user-navbar.vue';

import '@/shared/config/dayjs';
import { Provide } from 'vue-property-decorator';
import CategoryService from '@/entities/category/category.service';
import PostService from '@/entities/post/post.service';

@Component({
  components: {
    ribbon: Ribbon,
    'jhi-navbar': JhiNavbar,
    'login-form': LoginForm,
    'jhi-footer': JhiFooter,
    'user-navbar': UserNavbar,
    'user-footer': UserFooter,
  },
})
export default class App extends Vue {
  @Provide('categoryService') private categoryService = () => new CategoryService();
  @Provide('postService') private postService = () => new PostService();
}
