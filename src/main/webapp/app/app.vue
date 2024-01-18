<template>
  <div id="app">
    <!--    <ribbon></ribbon>-->
    <div id="">
      <jhi-navbar></jhi-navbar>
    </div>
    <div class="">
      <div class="">
        <router-view></router-view>
      </div>
      <b-modal id="login-page" hide-footer lazy>
        <span data-cy="loginTitle" slot="modal-title" id="login-title">Sign in</span>
        <login-form></login-form>
      </b-modal>

      <jhi-footer></jhi-footer>
    </div>
  </div>
</template>

<script lang="ts" src="./app.component.ts"></script>
