/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PostUpdateComponent from '@/entities/post/post-update.vue';
import PostClass from '@/entities/post/post-update.component';
import PostService from '@/entities/post/post.service';

import ImageService from '@/entities/image/image.service';

import PostTypeService from '@/entities/post-type/post-type.service';

import CategoryService from '@/entities/category/category.service';

import UserService from '@/entities/user/user.service';

import ProvinceService from '@/entities/province/province.service';

import DistrictService from '@/entities/district/district.service';

import WardService from '@/entities/ward/ward.service';

import StreetService from '@/entities/street/street.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Post Management Update Component', () => {
    let wrapper: Wrapper<PostClass>;
    let comp: PostClass;
    let postServiceStub: SinonStubbedInstance<PostService>;

    beforeEach(() => {
      postServiceStub = sinon.createStubInstance<PostService>(PostService);

      wrapper = shallowMount<PostClass>(PostUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          postService: () => postServiceStub,
          alertService: () => new AlertService(),

          imageService: () =>
            sinon.createStubInstance<ImageService>(ImageService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          postTypeService: () =>
            sinon.createStubInstance<PostTypeService>(PostTypeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          categoryService: () =>
            sinon.createStubInstance<CategoryService>(CategoryService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          userService: () => new UserService(),

          provinceService: () =>
            sinon.createStubInstance<ProvinceService>(ProvinceService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          districtService: () =>
            sinon.createStubInstance<DistrictService>(DistrictService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          wardService: () =>
            sinon.createStubInstance<WardService>(WardService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          streetService: () =>
            sinon.createStubInstance<StreetService>(StreetService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.post = entity;
        postServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(postServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.post = entity;
        postServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(postServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPost = { id: 123 };
        postServiceStub.find.resolves(foundPost);
        postServiceStub.retrieve.resolves([foundPost]);

        // WHEN
        comp.beforeRouteEnter({ params: { postId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.post).toBe(foundPost);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
