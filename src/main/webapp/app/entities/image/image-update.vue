<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="bdswebApp.image.home.createOrEditLabel" data-cy="ImageCreateUpdateHeading">Create or edit a Image</h2>
        <div>
          <div class="form-group" v-if="image.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="image.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="image-data">Data</label>
            <div>
              <img
                v-bind:src="'data:' + image.dataContentType + ';base64,' + image.data"
                style="max-height: 100px"
                v-if="image.data"
                alt="image image"
              />
              <div v-if="image.data" class="form-text text-danger clearfix">
                <span class="pull-left">{{ image.dataContentType }}, {{ byteSize(image.data) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('data', 'dataContentType', 'file_data')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_data"
                id="file_data"
                data-cy="data"
                v-on:change="setFileData($event, image, 'data', true)"
                accept="image/*"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="data"
              id="image-data"
              data-cy="data"
              :class="{ valid: !$v.image.data.$invalid, invalid: $v.image.data.$invalid }"
              v-model="$v.image.data.$model"
            />
            <input type="hidden" class="form-control" name="dataContentType" id="image-dataContentType" v-model="image.dataContentType" />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="image-url">Url</label>
            <input
              type="text"
              class="form-control"
              name="url"
              id="image-url"
              data-cy="url"
              :class="{ valid: !$v.image.url.$invalid, invalid: $v.image.url.$invalid }"
              v-model="$v.image.url.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="image-post">Post</label>
            <select class="form-control" id="image-post" data-cy="post" name="post" v-model="image.post">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="image.post && postOption.id === image.post.id ? image.post : postOption"
                v-for="postOption in posts"
                :key="postOption.id"
              >
                {{ postOption.id }}
              </option>
            </select>
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
            :disabled="$v.image.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./image-update.component.ts"></script>
