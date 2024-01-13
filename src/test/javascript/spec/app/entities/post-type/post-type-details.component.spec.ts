/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PostTypeDetailComponent from '@/entities/post-type/post-type-details.vue';
import PostTypeClass from '@/entities/post-type/post-type-details.component';
import PostTypeService from '@/entities/post-type/post-type.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PostType Management Detail Component', () => {
    let wrapper: Wrapper<PostTypeClass>;
    let comp: PostTypeClass;
    let postTypeServiceStub: SinonStubbedInstance<PostTypeService>;

    beforeEach(() => {
      postTypeServiceStub = sinon.createStubInstance<PostTypeService>(PostTypeService);

      wrapper = shallowMount<PostTypeClass>(PostTypeDetailComponent, {
        store,
        localVue,
        router,
        provide: { postTypeService: () => postTypeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPostType = { id: 123 };
        postTypeServiceStub.find.resolves(foundPostType);

        // WHEN
        comp.retrievePostType(123);
        await comp.$nextTick();

        // THEN
        expect(comp.postType).toBe(foundPostType);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPostType = { id: 123 };
        postTypeServiceStub.find.resolves(foundPostType);

        // WHEN
        comp.beforeRouteEnter({ params: { postTypeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.postType).toBe(foundPostType);
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
