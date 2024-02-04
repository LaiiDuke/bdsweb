<template>
  <div>
    <h2 id="page-heading" data-cy="PostTypeHeading">
      <span v-text="$t('bdswebApp.postType.home.title')" id="post-type-heading">Post Types</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('bdswebApp.postType.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PostTypeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-post-type"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('bdswebApp.postType.home.createLabel')"> Create a new Post Type </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && postTypes && postTypes.length === 0">
      <span v-text="$t('bdswebApp.postType.home.notFound')">No postTypes found</span>
    </div>
    <div class="table-responsive" v-if="postTypes && postTypes.length > 0">
      <table class="table table-striped" aria-describedby="postTypes">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('bdswebApp.postType.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('icon')">
              <span v-text="$t('bdswebApp.postType.icon')">Icon</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'icon'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('bdswebApp.postType.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="postType in postTypes" :key="postType.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PostTypeView', params: { postTypeId: postType.id } }">{{ postType.id }}</router-link>
            </td>
            <td>{{ postType.name }}</td>
            <td>{{ postType.icon }}</td>
            <td>{{ postType.description }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PostTypeView', params: { postTypeId: postType.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PostTypeEdit', params: { postTypeId: postType.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(postType)"
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
        ><span id="bdswebApp.postType.delete.question" data-cy="postTypeDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-postType-heading" v-text="$t('bdswebApp.postType.delete.question', { id: removeId })">
          Are you sure you want to delete this Post Type?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-postType"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePostType()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="postTypes && postTypes.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./post-type.component.ts"></script>
