<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="post">
        <h2 class="jh-entity-heading" data-cy="postDetailsHeading"><span>Post</span> {{ post.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Title</span>
          </dt>
          <dd>
            <span>{{ post.title }}</span>
          </dd>
          <dt>
            <span>Content</span>
          </dt>
          <dd>
            <span>{{ post.content }}</span>
          </dd>
          <dt>
            <span>Price</span>
          </dt>
          <dd>
            <span>{{ post.price }}</span>
          </dd>
          <dt>
            <span>Square</span>
          </dt>
          <dd>
            <span>{{ post.square }}</span>
          </dd>
          <dt>
            <span>Address</span>
          </dt>
          <dd>
            <span>{{ post.address }}</span>
          </dd>
          <dt>
            <span>Google Maps Location</span>
          </dt>
          <dd>
            <span>{{ post.googleMapsLocation }}</span>
          </dd>
          <dt>
            <span>Width</span>
          </dt>
          <dd>
            <span>{{ post.width }}</span>
          </dd>
          <dt>
            <span>Length</span>
          </dt>
          <dd>
            <span>{{ post.length }}</span>
          </dd>
          <dt>
            <span>Direction</span>
          </dt>
          <dd>
            <span>{{ post.direction }}</span>
          </dd>
          <dt>
            <span>Distance</span>
          </dt>
          <dd>
            <span>{{ post.distance }}</span>
          </dd>
          <dt>
            <span>Legal</span>
          </dt>
          <dd>
            <span>{{ post.legal }}</span>
          </dd>
          <dt>
            <span>Number Of Floors</span>
          </dt>
          <dd>
            <span>{{ post.numberOfFloors }}</span>
          </dd>
          <dt>
            <span>Number Of Bedroom</span>
          </dt>
          <dd>
            <span>{{ post.numberOfBedroom }}</span>
          </dd>
          <dt>
            <span>Has Kitchen</span>
          </dt>
          <dd>
            <span>{{ post.hasKitchen }}</span>
          </dd>
          <dt>
            <span>Has Dinning Room</span>
          </dt>
          <dd>
            <span>{{ post.hasDinningRoom }}</span>
          </dd>
          <dt>
            <span>Has Rooftop</span>
          </dt>
          <dd>
            <span>{{ post.hasRooftop }}</span>
          </dd>
          <dt>
            <span>Has Garage</span>
          </dt>
          <dd>
            <span>{{ post.hasGarage }}</span>
          </dd>
          <dt>
            <span>Is Vip</span>
          </dt>
          <dd>
            <span>{{ post.isVip }}</span>
          </dd>
          <dt>
            <span>Posting Time</span>
          </dt>
          <dd>
            <span>{{ post.postingTime }}</span>
          </dd>
          <dt>
            <span>Expired Time</span>
          </dt>
          <dd>
            <span>{{ post.expiredTime }}</span>
          </dd>
          <dt>
            <span>Brokerage Fees</span>
          </dt>
          <dd>
            <span>{{ post.brokerageFees }}</span>
          </dd>
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ post.status }}</span>
          </dd>
          <dt>
            <span>Star</span>
          </dt>
          <dd>
            <span>{{ post.star }}</span>
          </dd>
          <dt>
            <span>Hash</span>
          </dt>
          <dd>
            <span>{{ post.hash }}</span>
          </dd>
          <dt>
            <span>Type</span>
          </dt>
          <dd>
            <div v-if="post.type">
              <router-link :to="{ name: 'PostTypeView', params: { postTypeId: post.type.id } }">{{ post.type.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Category</span>
          </dt>
          <dd>
            <div v-if="post.category">
              <router-link :to="{ name: 'CategoryView', params: { categoryId: post.category.id } }">{{ post.category.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>User</span>
          </dt>
          <dd>
            {{ post.user ? post.user.id : '' }}
          </dd>
          <dt>
            <span>Province</span>
          </dt>
          <dd>
            <div v-if="post.province">
              <router-link :to="{ name: 'ProvinceView', params: { provinceId: post.province.id } }">{{ post.province.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>District</span>
          </dt>
          <dd>
            <div v-if="post.district">
              <router-link :to="{ name: 'DistrictView', params: { districtId: post.district.id } }">{{ post.district.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Ward</span>
          </dt>
          <dd>
            <div v-if="post.ward">
              <router-link :to="{ name: 'WardView', params: { wardId: post.ward.id } }">{{ post.ward.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Street</span>
          </dt>
          <dd>
            <div v-if="post.street">
              <router-link :to="{ name: 'StreetView', params: { streetId: post.street.id } }">{{ post.street.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="post.id" :to="{ name: 'PostEdit', params: { postId: post.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./post-details.component.ts"></script>
