<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="bdswebApp.street.home.createOrEditLabel"
          data-cy="StreetCreateUpdateHeading"
          v-text="$t('bdswebApp.street.home.createOrEditLabel')"
        >
          Create or edit a Street
        </h2>
        <div>
          <div class="form-group" v-if="street.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="street.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bdswebApp.street.name')" for="street-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="street-name"
              data-cy="name"
              :class="{ valid: !$v.street.name.$invalid, invalid: $v.street.name.$invalid }"
              v-model="$v.street.name.$model"
              required
            />
            <div v-if="$v.street.name.$anyDirty && $v.street.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.street.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bdswebApp.street.status')" for="street-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.street.status.$invalid, invalid: $v.street.status.$invalid }"
              v-model="$v.street.status.$model"
              id="street-status"
              data-cy="status"
            >
              <option
                v-for="postStatus in postStatusValues"
                :key="postStatus"
                v-bind:value="postStatus"
                v-bind:label="$t('bdswebApp.PostStatus.' + postStatus)"
              >
                {{ postStatus }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bdswebApp.street.ward')" for="street-ward">Ward</label>
            <select class="form-control" id="street-ward" data-cy="ward" name="ward" v-model="street.ward">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="street.ward && wardOption.id === street.ward.id ? street.ward : wardOption"
                v-for="wardOption in wards"
                :key="wardOption.id"
              >
                {{ wardOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bdswebApp.street.district')" for="street-district">District</label>
            <select class="form-control" id="street-district" data-cy="district" name="district" v-model="street.district" required>
              <option v-if="!street.district" v-bind:value="null" selected></option>
              <option
                v-bind:value="street.district && districtOption.id === street.district.id ? street.district : districtOption"
                v-for="districtOption in districts"
                :key="districtOption.id"
              >
                {{ districtOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.street.district.$anyDirty && $v.street.district.$invalid">
            <small class="form-text text-danger" v-if="!$v.street.district.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.street.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./street-update.component.ts"></script>
