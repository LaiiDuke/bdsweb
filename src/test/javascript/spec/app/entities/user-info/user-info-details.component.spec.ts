/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import UserInfoDetailComponent from '@/entities/user-info/user-info-details.vue';
import UserInfoClass from '@/entities/user-info/user-info-details.component';
import UserInfoService from '@/entities/user-info/user-info.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('UserInfo Management Detail Component', () => {
    let wrapper: Wrapper<UserInfoClass>;
    let comp: UserInfoClass;
    let userInfoServiceStub: SinonStubbedInstance<UserInfoService>;

    beforeEach(() => {
      userInfoServiceStub = sinon.createStubInstance<UserInfoService>(UserInfoService);

      wrapper = shallowMount<UserInfoClass>(UserInfoDetailComponent, {
        store,
        localVue,
        router,
        provide: { userInfoService: () => userInfoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUserInfo = { id: 123 };
        userInfoServiceStub.find.resolves(foundUserInfo);

        // WHEN
        comp.retrieveUserInfo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.userInfo).toBe(foundUserInfo);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUserInfo = { id: 123 };
        userInfoServiceStub.find.resolves(foundUserInfo);

        // WHEN
        comp.beforeRouteEnter({ params: { userInfoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.userInfo).toBe(foundUserInfo);
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
