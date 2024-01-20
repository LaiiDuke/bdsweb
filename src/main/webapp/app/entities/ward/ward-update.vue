<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="bdswebApp.ward.home.createOrEditLabel"
          data-cy="WardCreateUpdateHeading"
          v-text="$t('bdswebApp.ward.home.createOrEditLabel')"
        >
          Create or edit a Ward
        </h2>
        <div>
          <div class="form-group" v-if="ward.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ward.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bdswebApp.ward.name')" for="ward-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="ward-name"
              data-cy="name"
              :class="{ valid: !$v.ward.name.$invalid, invalid: $v.ward.name.$invalid }"
              v-model="$v.ward.name.$model"
              required
            />
            <div v-if="$v.ward.name.$anyDirty && $v.ward.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.ward.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bdswebApp.ward.district')" for="ward-district">District</label>
            <select class="form-control" id="ward-district" data-cy="district" name="district" v-model="ward.district" required>
              <option v-if="!ward.district" v-bind:value="null" selected></option>
              <option
                v-bind:value="ward.district && districtOption.id === ward.district.id ? ward.district : districtOption"
                v-for="districtOption in districts"
                :key="districtOption.id"
              >
                {{ districtOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.ward.district.$anyDirty && $v.ward.district.$invalid">
            <small class="form-text text-danger" v-if="!$v.ward.district.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.ward.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ward-update.component.ts"></script>
