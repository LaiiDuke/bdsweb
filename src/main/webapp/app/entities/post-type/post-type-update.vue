<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="bdswebApp.postType.home.createOrEditLabel" data-cy="PostTypeCreateUpdateHeading">Create or edit a PostType</h2>
        <div>
          <div class="form-group" v-if="postType.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="postType.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-type-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="post-type-name"
              data-cy="name"
              :class="{ valid: !$v.postType.name.$invalid, invalid: $v.postType.name.$invalid }"
              v-model="$v.postType.name.$model"
              required
            />
            <div v-if="$v.postType.name.$anyDirty && $v.postType.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.postType.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-type-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="post-type-description"
              data-cy="description"
              :class="{ valid: !$v.postType.description.$invalid, invalid: $v.postType.description.$invalid }"
              v-model="$v.postType.description.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.postType.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./post-type-update.component.ts"></script>
