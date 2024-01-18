<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="userInfo">
        <h2 class="jh-entity-heading" data-cy="userInfoDetailsHeading">
          <span v-text="$t('bdswebApp.userInfo.detail.title')">UserInfo</span> {{ userInfo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('bdswebApp.userInfo.name')">Name</span>
          </dt>
          <dd>
            <span>{{ userInfo.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.userInfo.phone')">Phone</span>
          </dt>
          <dd>
            <span>{{ userInfo.phone }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.userInfo.user')">User</span>
          </dt>
          <dd>
            {{ userInfo.user ? userInfo.user.id : '' }}
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="userInfo.id" :to="{ name: 'UserInfoEdit', params: { userInfoId: userInfo.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./user-info-details.component.ts"></script>
