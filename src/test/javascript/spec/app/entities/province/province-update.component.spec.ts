/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ProvinceUpdateComponent from '@/entities/province/province-update.vue';
import ProvinceClass from '@/entities/province/province-update.component';
import ProvinceService from '@/entities/province/province.service';

import DistrictService from '@/entities/district/district.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
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
  describe('Province Management Update Component', () => {
    let wrapper: Wrapper<ProvinceClass>;
    let comp: ProvinceClass;
    let provinceServiceStub: SinonStubbedInstance<ProvinceService>;

    beforeEach(() => {
      provinceServiceStub = sinon.createStubInstance<ProvinceService>(ProvinceService);

      wrapper = shallowMount<ProvinceClass>(ProvinceUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          provinceService: () => provinceServiceStub,
          alertService: () => new AlertService(),

          districtService: () =>
            sinon.createStubInstance<DistrictService>(DistrictService, {
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
        comp.province = entity;
        provinceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(provinceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.province = entity;
        provinceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(provinceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProvince = { id: 123 };
        provinceServiceStub.find.resolves(foundProvince);
        provinceServiceStub.retrieve.resolves([foundProvince]);

        // WHEN
        comp.beforeRouteEnter({ params: { provinceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.province).toBe(foundProvince);
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