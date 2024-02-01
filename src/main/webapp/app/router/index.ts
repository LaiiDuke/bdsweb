import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate', // for vue-router 2.2+
]);
import Router, { RouteConfig } from 'vue-router';

const Home = () => import('@/core/home/home.vue');
const HomePage = () => import('@/page/home/home.vue');
const ListProductPage = () => import('@/page/listProduct/listProduct.vue');
const ContactPage = () => import('@/page/contact/contact.vue');
const LoginPage = () => import('@/account/login-form/login-form.vue');
const CategoryPage = () => import('@/page/category/category.vue');
const DetailPage = () => import('@/page/detail/detail.vue');
const NewPostPage = () => import('@/page/new-post/new-post.vue');
const Error = () => import('@/core/error/error.vue');
import account from '@/router/account';
import admin from '@/router/admin';
import entities from '@/router/entities';
import pages from '@/router/pages';

Vue.use(Router);

// prettier-ignore
const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/admin',
      name: 'Home admin',
      component: Home
    },
    {
      path: '/',
      name: 'Home',
      component: HomePage
    },
    {
      path: '/list-product/:postTypeId',
      name: 'ListProduct',
      component: ListProductPage
    },
    {
      path: '/contact',
      name: 'Contact',
      component: ContactPage
    },
    {
      path: '/category/:categoryId',
      name: 'CategoryPost',
      component: CategoryPage
    },
    {
      path: '/detail/:postId',
      name: 'Detail',
      component: DetailPage
    },
    {
      path: '/post/new',
      name: 'Post',
      component: NewPostPage,
    },
    {
      path: '/login',
      name: 'Login',
      component: LoginPage
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    ...account,
    ...admin,
    entities,
    ...pages
  ]
});

export default router;
