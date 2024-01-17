<template>
  <div>
    <h2 id="page-heading" data-cy="PostHeading">
      <span id="post-heading">Posts</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'PostCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-post">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Post </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && posts && posts.length === 0">
      <span>No posts found</span>
    </div>
    <div class="table-responsive" v-if="posts && posts.length > 0">
      <table class="table table-striped" aria-describedby="posts">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span>Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('content')">
              <span>Content</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'content'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('price')">
              <span>Price</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'price'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('square')">
              <span>Square</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'square'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('address')">
              <span>Address</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'address'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('googleMapsLocation')">
              <span>Google Maps Location</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'googleMapsLocation'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('width')">
              <span>Width</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'width'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('length')">
              <span>Length</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'length'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('direction')">
              <span>Direction</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'direction'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('distance')">
              <span>Distance</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'distance'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('legal')">
              <span>Legal</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'legal'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numberOfFloors')">
              <span>Number Of Floors</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numberOfFloors'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numberOfBedroom')">
              <span>Number Of Bedroom</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numberOfBedroom'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hasKitchen')">
              <span>Has Kitchen</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hasKitchen'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hasDinningRoom')">
              <span>Has Dinning Room</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hasDinningRoom'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hasRooftop')">
              <span>Has Rooftop</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hasRooftop'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hasGarage')">
              <span>Has Garage</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hasGarage'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isVip')">
              <span>Is Vip</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isVip'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('postingTime')">
              <span>Posting Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'postingTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('expiredTime')">
              <span>Expired Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expiredTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('brokerageFees')">
              <span>Brokerage Fees</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'brokerageFees'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span>Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('star')">
              <span>Star</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'star'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hash')">
              <span>Hash</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hash'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type.id')">
              <span>Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('category.id')">
              <span>Category</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'category.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('user.id')">
              <span>User</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'user.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('province.id')">
              <span>Province</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'province.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('district.id')">
              <span>District</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'district.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ward.id')">
              <span>Ward</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ward.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('street.id')">
              <span>Street</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'street.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="post in posts" :key="post.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PostView', params: { postId: post.id } }">{{ post.id }}</router-link>
            </td>
            <td>{{ post.title }}</td>
            <td>{{ post.content }}</td>
            <td>{{ post.price }}</td>
            <td>{{ post.square }}</td>
            <td>{{ post.address }}</td>
            <td>{{ post.googleMapsLocation }}</td>
            <td>{{ post.width }}</td>
            <td>{{ post.length }}</td>
            <td>{{ post.direction }}</td>
            <td>{{ post.distance }}</td>
            <td>{{ post.legal }}</td>
            <td>{{ post.numberOfFloors }}</td>
            <td>{{ post.numberOfBedroom }}</td>
            <td>{{ post.hasKitchen }}</td>
            <td>{{ post.hasDinningRoom }}</td>
            <td>{{ post.hasRooftop }}</td>
            <td>{{ post.hasGarage }}</td>
            <td>{{ post.isVip }}</td>
            <td>{{ post.postingTime }}</td>
            <td>{{ post.expiredTime }}</td>
            <td>{{ post.brokerageFees }}</td>
            <td>{{ post.status }}</td>
            <td>{{ post.star }}</td>
            <td>{{ post.hash }}</td>
            <td>
              <div v-if="post.type">
                <router-link :to="{ name: 'PostTypeView', params: { postTypeId: post.type.id } }">{{ post.type.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="post.category">
                <router-link :to="{ name: 'CategoryView', params: { categoryId: post.category.id } }">{{ post.category.id }}</router-link>
              </div>
            </td>
            <td>
              {{ post.user ? post.user.id : '' }}
            </td>
            <td>
              <div v-if="post.province">
                <router-link :to="{ name: 'ProvinceView', params: { provinceId: post.province.id } }">{{ post.province.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="post.district">
                <router-link :to="{ name: 'DistrictView', params: { districtId: post.district.id } }">{{ post.district.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="post.ward">
                <router-link :to="{ name: 'WardView', params: { wardId: post.ward.id } }">{{ post.ward.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="post.street">
                <router-link :to="{ name: 'StreetView', params: { streetId: post.street.id } }">{{ post.street.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PostView', params: { postId: post.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PostEdit', params: { postId: post.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(post)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="bdswebApp.post.delete.question" data-cy="postDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-post-heading">Are you sure you want to delete this Post?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-post"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removePost()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="posts && posts.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./post.component.ts"></script>
