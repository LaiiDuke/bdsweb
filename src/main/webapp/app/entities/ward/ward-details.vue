<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ward">
        <h2 class="jh-entity-heading" data-cy="wardDetailsHeading">
          <span v-text="$t('bdswebApp.ward.detail.title')">Ward</span> {{ ward.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('bdswebApp.ward.name')">Name</span>
          </dt>
          <dd>
            <span>{{ ward.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.ward.district')">District</span>
          </dt>
          <dd>
            <div v-if="ward.district">
              <router-link :to="{ name: 'DistrictView', params: { districtId: ward.district.id } }">{{ ward.district.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="ward.id" :to="{ name: 'WardEdit', params: { wardId: ward.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ward-details.component.ts"></script>
