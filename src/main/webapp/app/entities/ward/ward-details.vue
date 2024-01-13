<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ward">
        <h2 class="jh-entity-heading" data-cy="wardDetailsHeading"><span>Ward</span> {{ ward.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ ward.name }}</span>
          </dd>
          <dt>
            <span>District</span>
          </dt>
          <dd>
            <div v-if="ward.district">
              <router-link :to="{ name: 'DistrictView', params: { districtId: ward.district.id } }">{{ ward.district.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="ward.id" :to="{ name: 'WardEdit', params: { wardId: ward.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ward-details.component.ts"></script>
