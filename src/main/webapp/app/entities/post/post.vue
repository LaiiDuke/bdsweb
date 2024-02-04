<template>
  <div>
    <h2 id="page-heading" data-cy="PostHeading">
      <span v-text="$t('bdswebApp.post.home.title')" id="post-heading">Posts</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('bdswebApp.post.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PostCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-post">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('bdswebApp.post.home.createLabel')"> Create a new Post </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && posts && posts.length === 0">
      <span v-text="$t('bdswebApp.post.home.notFound')">No posts found</span>
    </div>
    <div class="table-responsive" v-if="posts && posts.length > 0">
      <table class="table table-striped" aria-describedby="posts">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('bdswebApp.post.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('content')">
              <span v-text="$t('bdswebApp.post.content')">Content</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'content'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('price')">
              <span v-text="$t('bdswebApp.post.price')">Price</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'price'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('square')">
              <span v-text="$t('bdswebApp.post.square')">Square</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'square'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('address')">
              <span v-text="$t('bdswebApp.post.address')">Address</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'address'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('phone')">
              <span v-text="$t('bdswebApp.post.phone')">Phone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phone'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('googleMapsLocation')">
              <span v-text="$t('bdswebApp.post.googleMapsLocation')">Google Maps Location</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'googleMapsLocation'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('width')">
              <span v-text="$t('bdswebApp.post.width')">Width</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'width'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('length')">
              <span v-text="$t('bdswebApp.post.length')">Length</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'length'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('direction')">
              <span v-text="$t('bdswebApp.post.direction')">Direction</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'direction'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('distance')">
              <span v-text="$t('bdswebApp.post.distance')">Distance</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'distance'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('legal')">
              <span v-text="$t('bdswebApp.post.legal')">Legal</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'legal'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numberOfFloors')">
              <span v-text="$t('bdswebApp.post.numberOfFloors')">Number Of Floors</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numberOfFloors'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numberOfBedroom')">
              <span v-text="$t('bdswebApp.post.numberOfBedroom')">Number Of Bedroom</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numberOfBedroom'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hasKitchen')">
              <span v-text="$t('bdswebApp.post.hasKitchen')">Has Kitchen</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hasKitchen'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hasDinningRoom')">
              <span v-text="$t('bdswebApp.post.hasDinningRoom')">Has Dinning Room</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hasDinningRoom'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hasRooftop')">
              <span v-text="$t('bdswebApp.post.hasRooftop')">Has Rooftop</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hasRooftop'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hasGarage')">
              <span v-text="$t('bdswebApp.post.hasGarage')">Has Garage</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hasGarage'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isVip')">
              <span v-text="$t('bdswebApp.post.isVip')">Is Vip</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isVip'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('postingTime')">
              <span v-text="$t('bdswebApp.post.postingTime')">Posting Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'postingTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('expiredTime')">
              <span v-text="$t('bdswebApp.post.expiredTime')">Expired Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expiredTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('brokerageFees')">
              <span v-text="$t('bdswebApp.post.brokerageFees')">Brokerage Fees</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'brokerageFees'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="$t('bdswebApp.post.status')">Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('star')">
              <span v-text="$t('bdswebApp.post.star')">Star</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'star'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hash')">
              <span v-text="$t('bdswebApp.post.hash')">Hash</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hash'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type.name')">
              <span v-text="$t('bdswebApp.post.type')">Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('category.name')">
              <span v-text="$t('bdswebApp.post.category')">Category</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'category.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('user.id')">
              <span v-text="$t('bdswebApp.post.user')">User</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'user.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('province.name')">
              <span v-text="$t('bdswebApp.post.province')">Province</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'province.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('district.name')">
              <span v-text="$t('bdswebApp.post.district')">District</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'district.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ward.name')">
              <span v-text="$t('bdswebApp.post.ward')">Ward</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ward.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('street.name')">
              <span v-text="$t('bdswebApp.post.street')">Street</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'street.name'"></jhi-sort-indicator>
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
            <td>{{ post.phone }}</td>
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
            <td v-text="$t('bdswebApp.PostStatus.' + post.status)">{{ post.status }}</td>
            <td>{{ post.star }}</td>
            <td>{{ post.hash }}</td>
            <td>
              <div v-if="post.type">
                <router-link :to="{ name: 'PostTypeView', params: { postTypeId: post.type.id } }">{{ post.type.name }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="post.category">
                <router-link :to="{ name: 'CategoryView', params: { categoryId: post.category.id } }">{{ post.category.name }}</router-link>
              </div>
            </td>
            <td>
              {{ post.user ? post.user.id : '' }}
            </td>
            <td>
              <div v-if="post.province">
                <router-link :to="{ name: 'ProvinceView', params: { provinceId: post.province.id } }">{{ post.province.name }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="post.district">
                <router-link :to="{ name: 'DistrictView', params: { districtId: post.district.id } }">{{ post.district.name }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="post.ward">
                <router-link :to="{ name: 'WardView', params: { wardId: post.ward.id } }">{{ post.ward.name }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="post.street">
                <router-link :to="{ name: 'StreetView', params: { streetId: post.street.id } }">{{ post.street.name }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PostView', params: { postId: post.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PostEdit', params: { postId: post.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
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
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="bdswebApp.post.delete.question" data-cy="postDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-post-heading" v-text="$t('bdswebApp.post.delete.question', { id: removeId })">
          Are you sure you want to delete this Post?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-post"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
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
