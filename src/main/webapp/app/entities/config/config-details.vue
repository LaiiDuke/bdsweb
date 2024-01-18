<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="config">
        <h2 class="jh-entity-heading" data-cy="configDetailsHeading">
          <span v-text="$t('bdswebApp.config.detail.title')">Config</span> {{ config.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('bdswebApp.config.code')">Code</span>
          </dt>
          <dd>
            <span>{{ config.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.config.value')">Value</span>
          </dt>
          <dd>
            <span>{{ config.value }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.config.description')">Description</span>
          </dt>
          <dd>
            <span>{{ config.description }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="config.id" :to="{ name: 'ConfigEdit', params: { configId: config.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./config-details.component.ts"></script>
