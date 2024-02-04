<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="street">
        <h2 class="jh-entity-heading" data-cy="streetDetailsHeading">
          <span v-text="$t('bdswebApp.street.detail.title')">Street</span> {{ street.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('bdswebApp.street.name')">Name</span>
          </dt>
          <dd>
            <span>{{ street.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.street.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('bdswebApp.PostStatus.' + street.status)">{{ street.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.street.ward')">Ward</span>
          </dt>
          <dd>
            <div v-if="street.ward">
              <router-link :to="{ name: 'WardView', params: { wardId: street.ward.id } }">{{ street.ward.name }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.street.district')">District</span>
          </dt>
          <dd>
            <div v-if="street.district">
              <router-link :to="{ name: 'DistrictView', params: { districtId: street.district.id } }">{{
                street.district.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="street.id" :to="{ name: 'StreetEdit', params: { streetId: street.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./street-details.component.ts"></script>
