import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import UserInfoService from './user-info/user-info.service';
import PostTypeService from './post-type/post-type.service';
import CategoryService from './category/category.service';
import PostService from './post/post.service';
import ProvinceService from './province/province.service';
import DistrictService from './district/district.service';
import WardService from './ward/ward.service';
import StreetService from './street/street.service';
import ImageService from './image/image.service';
import ConfigService from './config/config.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('userInfoService') private userInfoService = () => new UserInfoService();
  @Provide('postTypeService') private postTypeService = () => new PostTypeService();
  @Provide('categoryService') private categoryService = () => new CategoryService();
  @Provide('postService') private postService = () => new PostService();
  @Provide('provinceService') private provinceService = () => new ProvinceService();
  @Provide('districtService') private districtService = () => new DistrictService();
  @Provide('wardService') private wardService = () => new WardService();
  @Provide('streetService') private streetService = () => new StreetService();
  @Provide('imageService') private imageService = () => new ImageService();
  @Provide('configService') private configService = () => new ConfigService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
