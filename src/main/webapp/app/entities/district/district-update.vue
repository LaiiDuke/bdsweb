<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="bdswebApp.district.home.createOrEditLabel"
          data-cy="DistrictCreateUpdateHeading"
          v-text="$t('bdswebApp.district.home.createOrEditLabel')"
        >
          Create or edit a District
        </h2>
        <div>
          <div class="form-group" v-if="district.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="district.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bdswebApp.district.name')" for="district-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="district-name"
              data-cy="name"
              :class="{ valid: !$v.district.name.$invalid, invalid: $v.district.name.$invalid }"
              v-model="$v.district.name.$model"
              required
            />
            <div v-if="$v.district.name.$anyDirty && $v.district.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.district.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bdswebApp.district.province')" for="district-province">Province</label>
            <select class="form-control" id="district-province" data-cy="province" name="province" v-model="district.province" required>
              <option v-if="!district.province" v-bind:value="null" selected></option>
              <option
                v-bind:value="district.province && provinceOption.id === district.province.id ? district.province : provinceOption"
                v-for="provinceOption in provinces"
                :key="provinceOption.id"
              >
                {{ provinceOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.district.province.$anyDirty && $v.district.province.$invalid">
            <small class="form-text text-danger" v-if="!$v.district.province.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.district.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./district-update.component.ts"></script>
