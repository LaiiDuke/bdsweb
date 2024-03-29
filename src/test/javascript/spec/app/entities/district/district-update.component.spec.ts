/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DistrictUpdateComponent from '@/entities/district/district-update.vue';
import DistrictClass from '@/entities/district/district-update.component';
import DistrictService from '@/entities/district/district.service';

import WardService from '@/entities/ward/ward.service';

import StreetService from '@/entities/street/street.service';

import ProvinceService from '@/entities/province/province.service';
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
  describe('District Management Update Component', () => {
    let wrapper: Wrapper<DistrictClass>;
    let comp: DistrictClass;
    let districtServiceStub: SinonStubbedInstance<DistrictService>;

    beforeEach(() => {
      districtServiceStub = sinon.createStubInstance<DistrictService>(DistrictService);

      wrapper = shallowMount<DistrictClass>(DistrictUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          districtService: () => districtServiceStub,
          alertService: () => new AlertService(),

          wardService: () =>
            sinon.createStubInstance<WardService>(WardService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          streetService: () =>
            sinon.createStubInstance<StreetService>(StreetService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          provinceService: () =>
            sinon.createStubInstance<ProvinceService>(ProvinceService, {
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
        comp.district = entity;
        districtServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(districtServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.district = entity;
        districtServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(districtServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDistrict = { id: 123 };
        districtServiceStub.find.resolves(foundDistrict);
        districtServiceStub.retrieve.resolves([foundDistrict]);

        // WHEN
        comp.beforeRouteEnter({ params: { districtId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.district).toBe(foundDistrict);
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
