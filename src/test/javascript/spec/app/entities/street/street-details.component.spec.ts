/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import StreetDetailComponent from '@/entities/street/street-details.vue';
import StreetClass from '@/entities/street/street-details.component';
import StreetService from '@/entities/street/street.service';
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
  describe('Street Management Detail Component', () => {
    let wrapper: Wrapper<StreetClass>;
    let comp: StreetClass;
    let streetServiceStub: SinonStubbedInstance<StreetService>;

    beforeEach(() => {
      streetServiceStub = sinon.createStubInstance<StreetService>(StreetService);

      wrapper = shallowMount<StreetClass>(StreetDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { streetService: () => streetServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStreet = { id: 123 };
        streetServiceStub.find.resolves(foundStreet);

        // WHEN
        comp.retrieveStreet(123);
        await comp.$nextTick();

        // THEN
        expect(comp.street).toBe(foundStreet);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundStreet = { id: 123 };
        streetServiceStub.find.resolves(foundStreet);

        // WHEN
        comp.beforeRouteEnter({ params: { streetId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.street).toBe(foundStreet);
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
