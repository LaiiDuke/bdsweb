<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="bdswebApp.config.home.createOrEditLabel" data-cy="ConfigCreateUpdateHeading">Create or edit a Config</h2>
        <div>
          <div class="form-group" v-if="config.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="config.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="config-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="config-code"
              data-cy="code"
              :class="{ valid: !$v.config.code.$invalid, invalid: $v.config.code.$invalid }"
              v-model="$v.config.code.$model"
              required
            />
            <div v-if="$v.config.code.$anyDirty && $v.config.code.$invalid">
              <small class="form-text text-danger" v-if="!$v.config.code.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="config-value">Value</label>
            <input
              type="text"
              class="form-control"
              name="value"
              id="config-value"
              data-cy="value"
              :class="{ valid: !$v.config.value.$invalid, invalid: $v.config.value.$invalid }"
              v-model="$v.config.value.$model"
              required
            />
            <div v-if="$v.config.value.$anyDirty && $v.config.value.$invalid">
              <small class="form-text text-danger" v-if="!$v.config.value.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="config-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="config-description"
              data-cy="description"
              :class="{ valid: !$v.config.description.$invalid, invalid: $v.config.description.$invalid }"
              v-model="$v.config.description.$model"
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
            :disabled="$v.config.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./config-update.component.ts"></script>
