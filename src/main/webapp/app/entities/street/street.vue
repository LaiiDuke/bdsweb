<template>
  <div>
    <h2 id="page-heading" data-cy="StreetHeading">
      <span id="street-heading">Streets</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'StreetCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-street"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Street </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && streets && streets.length === 0">
      <span>No streets found</span>
    </div>
    <div class="table-responsive" v-if="streets && streets.length > 0">
      <table class="table table-striped" aria-describedby="streets">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span>Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span>Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ward.id')">
              <span>Ward</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ward.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('district.id')">
              <span>District</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'district.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="street in streets" :key="street.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StreetView', params: { streetId: street.id } }">{{ street.id }}</router-link>
            </td>
            <td>{{ street.name }}</td>
            <td>{{ street.status }}</td>
            <td>
              <div v-if="street.ward">
                <router-link :to="{ name: 'WardView', params: { wardId: street.ward.id } }">{{ street.ward.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="street.district">
                <router-link :to="{ name: 'DistrictView', params: { districtId: street.district.id } }">{{
                  street.district.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StreetView', params: { streetId: street.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StreetEdit', params: { streetId: street.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(street)"
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
        ><span id="bdswebApp.street.delete.question" data-cy="streetDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-street-heading">Are you sure you want to delete this Street?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-street"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeStreet()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="streets && streets.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./street.component.ts"></script>
