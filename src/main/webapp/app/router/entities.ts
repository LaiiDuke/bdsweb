import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const UserInfo = () => import('@/entities/user-info/user-info.vue');
// prettier-ignore
const UserInfoUpdate = () => import('@/entities/user-info/user-info-update.vue');
// prettier-ignore
const UserInfoDetails = () => import('@/entities/user-info/user-info-details.vue');
// prettier-ignore
const PostType = () => import('@/entities/post-type/post-type.vue');
// prettier-ignore
const PostTypeUpdate = () => import('@/entities/post-type/post-type-update.vue');
// prettier-ignore
const PostTypeDetails = () => import('@/entities/post-type/post-type-details.vue');
// prettier-ignore
const Category = () => import('@/entities/category/category.vue');
// prettier-ignore
const CategoryUpdate = () => import('@/entities/category/category-update.vue');
// prettier-ignore
const CategoryDetails = () => import('@/entities/category/category-details.vue');
// prettier-ignore
const Post = () => import('@/entities/post/post.vue');
// prettier-ignore
const PostUpdate = () => import('@/entities/post/post-update.vue');
// prettier-ignore
const PostDetails = () => import('@/entities/post/post-details.vue');
// prettier-ignore
const Province = () => import('@/entities/province/province.vue');
// prettier-ignore
const ProvinceUpdate = () => import('@/entities/province/province-update.vue');
// prettier-ignore
const ProvinceDetails = () => import('@/entities/province/province-details.vue');
// prettier-ignore
const District = () => import('@/entities/district/district.vue');
// prettier-ignore
const DistrictUpdate = () => import('@/entities/district/district-update.vue');
// prettier-ignore
const DistrictDetails = () => import('@/entities/district/district-details.vue');
// prettier-ignore
const Ward = () => import('@/entities/ward/ward.vue');
// prettier-ignore
const WardUpdate = () => import('@/entities/ward/ward-update.vue');
// prettier-ignore
const WardDetails = () => import('@/entities/ward/ward-details.vue');
// prettier-ignore
const Street = () => import('@/entities/street/street.vue');
// prettier-ignore
const StreetUpdate = () => import('@/entities/street/street-update.vue');
// prettier-ignore
const StreetDetails = () => import('@/entities/street/street-details.vue');
// prettier-ignore
const Image = () => import('@/entities/image/image.vue');
// prettier-ignore
const ImageUpdate = () => import('@/entities/image/image-update.vue');
// prettier-ignore
const ImageDetails = () => import('@/entities/image/image-details.vue');
// prettier-ignore
const Config = () => import('@/entities/config/config.vue');
// prettier-ignore
const ConfigUpdate = () => import('@/entities/config/config-update.vue');
// prettier-ignore
const ConfigDetails = () => import('@/entities/config/config-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/admin',
  component: Entities,
  children: [
    {
      path: 'user-info',
      name: 'UserInfo',
      component: UserInfo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-info/new',
      name: 'UserInfoCreate',
      component: UserInfoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-info/:userInfoId/edit',
      name: 'UserInfoEdit',
      component: UserInfoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'user-info/:userInfoId/view',
      name: 'UserInfoView',
      component: UserInfoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'post-type',
      name: 'PostType',
      component: PostType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'post-type/new',
      name: 'PostTypeCreate',
      component: PostTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'post-type/:postTypeId/edit',
      name: 'PostTypeEdit',
      component: PostTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'post-type/:postTypeId/view',
      name: 'PostTypeView',
      component: PostTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category',
      name: 'Category',
      component: Category,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/new',
      name: 'CategoryCreate',
      component: CategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/:categoryId/edit',
      name: 'CategoryEdit',
      component: CategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/:categoryId/view',
      name: 'CategoryView',
      component: CategoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'post',
      name: 'Post',
      component: Post,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'post/new',
      name: 'PostCreate',
      component: PostUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'post/:postId/edit',
      name: 'PostEdit',
      component: PostUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'post/:postId/view',
      name: 'PostView',
      component: PostDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province',
      name: 'Province',
      component: Province,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province/new',
      name: 'ProvinceCreate',
      component: ProvinceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province/:provinceId/edit',
      name: 'ProvinceEdit',
      component: ProvinceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province/:provinceId/view',
      name: 'ProvinceView',
      component: ProvinceDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district',
      name: 'District',
      component: District,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district/new',
      name: 'DistrictCreate',
      component: DistrictUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district/:districtId/edit',
      name: 'DistrictEdit',
      component: DistrictUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district/:districtId/view',
      name: 'DistrictView',
      component: DistrictDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ward',
      name: 'Ward',
      component: Ward,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ward/new',
      name: 'WardCreate',
      component: WardUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ward/:wardId/edit',
      name: 'WardEdit',
      component: WardUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ward/:wardId/view',
      name: 'WardView',
      component: WardDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'street',
      name: 'Street',
      component: Street,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'street/new',
      name: 'StreetCreate',
      component: StreetUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'street/:streetId/edit',
      name: 'StreetEdit',
      component: StreetUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'street/:streetId/view',
      name: 'StreetView',
      component: StreetDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'image',
      name: 'Image',
      component: Image,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'image/new',
      name: 'ImageCreate',
      component: ImageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'image/:imageId/edit',
      name: 'ImageEdit',
      component: ImageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'image/:imageId/view',
      name: 'ImageView',
      component: ImageDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'config',
      name: 'Config',
      component: Config,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'config/new',
      name: 'ConfigCreate',
      component: ConfigUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'config/:configId/edit',
      name: 'ConfigEdit',
      component: ConfigUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'config/:configId/view',
      name: 'ConfigView',
      component: ConfigDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
