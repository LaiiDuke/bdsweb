<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="postType">
        <h2 class="jh-entity-heading" data-cy="postTypeDetailsHeading">
          <span v-text="$t('bdswebApp.postType.detail.title')">PostType</span> {{ postType.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('bdswebApp.postType.name')">Name</span>
          </dt>
          <dd>
            <span>{{ postType.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.postType.description')">Description</span>
          </dt>
          <dd>
            <span>{{ postType.description }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="postType.id" :to="{ name: 'PostTypeEdit', params: { postTypeId: postType.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./post-type-details.component.ts"></script>
