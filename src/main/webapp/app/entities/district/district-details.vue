<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="district">
        <h2 class="jh-entity-heading" data-cy="districtDetailsHeading">
          <span v-text="$t('bdswebApp.district.detail.title')">District</span> {{ district.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('bdswebApp.district.name')">Name</span>
          </dt>
          <dd>
            <span>{{ district.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.district.province')">Province</span>
          </dt>
          <dd>
            <div v-if="district.province">
              <router-link :to="{ name: 'ProvinceView', params: { provinceId: district.province.id } }">{{
                district.province.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="district.id" :to="{ name: 'DistrictEdit', params: { districtId: district.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./district-details.component.ts"></script>
