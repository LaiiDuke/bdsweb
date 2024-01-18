<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="bdswebApp.userInfo.home.createOrEditLabel"
          data-cy="UserInfoCreateUpdateHeading"
          v-text="$t('bdswebApp.userInfo.home.createOrEditLabel')"
        >
          Create or edit a UserInfo
        </h2>
        <div>
          <div class="form-group" v-if="userInfo.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="userInfo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bdswebApp.userInfo.name')" for="user-info-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="user-info-name"
              data-cy="name"
              :class="{ valid: !$v.userInfo.name.$invalid, invalid: $v.userInfo.name.$invalid }"
              v-model="$v.userInfo.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bdswebApp.userInfo.phone')" for="user-info-phone">Phone</label>
            <input
              type="text"
              class="form-control"
              name="phone"
              id="user-info-phone"
              data-cy="phone"
              :class="{ valid: !$v.userInfo.phone.$invalid, invalid: $v.userInfo.phone.$invalid }"
              v-model="$v.userInfo.phone.$model"
              required
            />
            <div v-if="$v.userInfo.phone.$anyDirty && $v.userInfo.phone.$invalid">
              <small class="form-text text-danger" v-if="!$v.userInfo.phone.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('bdswebApp.userInfo.user')" for="user-info-user">User</label>
            <select class="form-control" id="user-info-user" data-cy="user" name="user" v-model="userInfo.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="userInfo.user && userOption.id === userInfo.user.id ? userInfo.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
              </option>
            </select>
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
            :disabled="$v.userInfo.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./user-info-update.component.ts"></script>
