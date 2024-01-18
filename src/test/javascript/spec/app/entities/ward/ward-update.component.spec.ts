/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import WardUpdateComponent from '@/entities/ward/ward-update.vue';
import WardClass from '@/entities/ward/ward-update.component';
import WardService from '@/entities/ward/ward.service';

import StreetService from '@/entities/street/street.service';

import DistrictService from '@/entities/district/district.service';
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
  describe('Ward Management Update Component', () => {
    let wrapper: Wrapper<WardClass>;
    let comp: WardClass;
    let wardServiceStub: SinonStubbedInstance<WardService>;

    beforeEach(() => {
      wardServiceStub = sinon.createStubInstance<WardService>(WardService);

      wrapper = shallowMount<WardClass>(WardUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          wardService: () => wardServiceStub,
          alertService: () => new AlertService(),

          streetService: () =>
            sinon.createStubInstance<StreetService>(StreetService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

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
        comp.ward = entity;
        wardServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(wardServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.ward = entity;
        wardServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(wardServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundWard = { id: 123 };
        wardServiceStub.find.resolves(foundWard);
        wardServiceStub.retrieve.resolves([foundWard]);

        // WHEN
        comp.beforeRouteEnter({ params: { wardId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.ward).toBe(foundWard);
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
