/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ConfigDetailComponent from '@/entities/config/config-details.vue';
import ConfigClass from '@/entities/config/config-details.component';
import ConfigService from '@/entities/config/config.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Config Management Detail Component', () => {
    let wrapper: Wrapper<ConfigClass>;
    let comp: ConfigClass;
    let configServiceStub: SinonStubbedInstance<ConfigService>;

    beforeEach(() => {
      configServiceStub = sinon.createStubInstance<ConfigService>(ConfigService);

      wrapper = shallowMount<ConfigClass>(ConfigDetailComponent, {
        store,
        localVue,
        router,
        provide: { configService: () => configServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundConfig = { id: 123 };
        configServiceStub.find.resolves(foundConfig);

        // WHEN
        comp.retrieveConfig(123);
        await comp.$nextTick();

        // THEN
        expect(comp.config).toBe(foundConfig);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundConfig = { id: 123 };
        configServiceStub.find.resolves(foundConfig);

        // WHEN
        comp.beforeRouteEnter({ params: { configId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.config).toBe(foundConfig);
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