<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="post">
        <h2 class="jh-entity-heading" data-cy="postDetailsHeading">
          <span v-text="$t('bdswebApp.post.detail.title')">Post</span> {{ post.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('bdswebApp.post.title')">Title</span>
          </dt>
          <dd>
            <span>{{ post.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.content')">Content</span>
          </dt>
          <dd>
            <span>{{ post.content }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.price')">Price</span>
          </dt>
          <dd>
            <span>{{ post.price }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.square')">Square</span>
          </dt>
          <dd>
            <span>{{ post.square }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.address')">Address</span>
          </dt>
          <dd>
            <span>{{ post.address }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.googleMapsLocation')">Google Maps Location</span>
          </dt>
          <dd>
            <span>{{ post.googleMapsLocation }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.width')">Width</span>
          </dt>
          <dd>
            <span>{{ post.width }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.length')">Length</span>
          </dt>
          <dd>
            <span>{{ post.length }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.direction')">Direction</span>
          </dt>
          <dd>
            <span>{{ post.direction }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.distance')">Distance</span>
          </dt>
          <dd>
            <span>{{ post.distance }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.legal')">Legal</span>
          </dt>
          <dd>
            <span>{{ post.legal }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.numberOfFloors')">Number Of Floors</span>
          </dt>
          <dd>
            <span>{{ post.numberOfFloors }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.numberOfBedroom')">Number Of Bedroom</span>
          </dt>
          <dd>
            <span>{{ post.numberOfBedroom }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.hasKitchen')">Has Kitchen</span>
          </dt>
          <dd>
            <span>{{ post.hasKitchen }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.hasDinningRoom')">Has Dinning Room</span>
          </dt>
          <dd>
            <span>{{ post.hasDinningRoom }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.hasRooftop')">Has Rooftop</span>
          </dt>
          <dd>
            <span>{{ post.hasRooftop }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.hasGarage')">Has Garage</span>
          </dt>
          <dd>
            <span>{{ post.hasGarage }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.isVip')">Is Vip</span>
          </dt>
          <dd>
            <span>{{ post.isVip }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.postingTime')">Posting Time</span>
          </dt>
          <dd>
            <span>{{ post.postingTime }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.expiredTime')">Expired Time</span>
          </dt>
          <dd>
            <span>{{ post.expiredTime }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.brokerageFees')">Brokerage Fees</span>
          </dt>
          <dd>
            <span>{{ post.brokerageFees }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('bdswebApp.PostStatus.' + post.status)">{{ post.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.star')">Star</span>
          </dt>
          <dd>
            <span>{{ post.star }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.hash')">Hash</span>
          </dt>
          <dd>
            <span>{{ post.hash }}</span>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.type')">Type</span>
          </dt>
          <dd>
            <div v-if="post.type">
              <router-link :to="{ name: 'PostTypeView', params: { postTypeId: post.type.id } }">{{ post.type.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.category')">Category</span>
          </dt>
          <dd>
            <div v-if="post.category">
              <router-link :to="{ name: 'CategoryView', params: { categoryId: post.category.id } }">{{ post.category.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.user')">User</span>
          </dt>
          <dd>
            {{ post.user ? post.user.id : '' }}
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.province')">Province</span>
          </dt>
          <dd>
            <div v-if="post.province">
              <router-link :to="{ name: 'ProvinceView', params: { provinceId: post.province.id } }">{{ post.province.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.district')">District</span>
          </dt>
          <dd>
            <div v-if="post.district">
              <router-link :to="{ name: 'DistrictView', params: { districtId: post.district.id } }">{{ post.district.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.ward')">Ward</span>
          </dt>
          <dd>
            <div v-if="post.ward">
              <router-link :to="{ name: 'WardView', params: { wardId: post.ward.id } }">{{ post.ward.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('bdswebApp.post.street')">Street</span>
          </dt>
          <dd>
            <div v-if="post.street">
              <router-link :to="{ name: 'StreetView', params: { streetId: post.street.id } }">{{ post.street.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="post.id" :to="{ name: 'PostEdit', params: { postId: post.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./post-details.component.ts"></script>
