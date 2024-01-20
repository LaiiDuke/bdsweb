/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import WardDetailComponent from '@/entities/ward/ward-details.vue';
import WardClass from '@/entities/ward/ward-details.component';
import WardService from '@/entities/ward/ward.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Ward Management Detail Component', () => {
    let wrapper: Wrapper<WardClass>;
    let comp: WardClass;
    let wardServiceStub: SinonStubbedInstance<WardService>;

    beforeEach(() => {
      wardServiceStub = sinon.createStubInstance<WardService>(WardService);

      wrapper = shallowMount<WardClass>(WardDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { wardService: () => wardServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundWard = { id: 123 };
        wardServiceStub.find.resolves(foundWard);

        // WHEN
        comp.retrieveWard(123);
        await comp.$nextTick();

        // THEN
        expect(comp.ward).toBe(foundWard);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundWard = { id: 123 };
        wardServiceStub.find.resolves(foundWard);

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
