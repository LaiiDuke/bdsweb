<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="street">
        <h2 class="jh-entity-heading" data-cy="streetDetailsHeading"><span>Street</span> {{ street.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ street.name }}</span>
          </dd>
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ street.status }}</span>
          </dd>
          <dt>
            <span>Ward</span>
          </dt>
          <dd>
            <div v-if="street.ward">
              <router-link :to="{ name: 'WardView', params: { wardId: street.ward.id } }">{{ street.ward.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>District</span>
          </dt>
          <dd>
            <div v-if="street.district">
              <router-link :to="{ name: 'DistrictView', params: { districtId: street.district.id } }">{{ street.district.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="street.id" :to="{ name: 'StreetEdit', params: { streetId: street.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./street-details.component.ts"></script>
