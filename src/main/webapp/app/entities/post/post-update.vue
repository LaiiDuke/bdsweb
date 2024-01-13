<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="bdswebApp.post.home.createOrEditLabel" data-cy="PostCreateUpdateHeading">Create or edit a Post</h2>
        <div>
          <div class="form-group" v-if="post.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="post.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="post-title"
              data-cy="title"
              :class="{ valid: !$v.post.title.$invalid, invalid: $v.post.title.$invalid }"
              v-model="$v.post.title.$model"
              required
            />
            <div v-if="$v.post.title.$anyDirty && $v.post.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.post.title.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.post.title.maxLength">
                This field cannot be longer than 200 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-content">Content</label>
            <textarea
              class="form-control"
              name="content"
              id="post-content"
              data-cy="content"
              :class="{ valid: !$v.post.content.$invalid, invalid: $v.post.content.$invalid }"
              v-model="$v.post.content.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-price">Price</label>
            <input
              type="number"
              class="form-control"
              name="price"
              id="post-price"
              data-cy="price"
              :class="{ valid: !$v.post.price.$invalid, invalid: $v.post.price.$invalid }"
              v-model.number="$v.post.price.$model"
              required
            />
            <div v-if="$v.post.price.$anyDirty && $v.post.price.$invalid">
              <small class="form-text text-danger" v-if="!$v.post.price.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.post.price.numeric"> This field should be a number. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-square">Square</label>
            <input
              type="number"
              class="form-control"
              name="square"
              id="post-square"
              data-cy="square"
              :class="{ valid: !$v.post.square.$invalid, invalid: $v.post.square.$invalid }"
              v-model.number="$v.post.square.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.post.status.$invalid, invalid: $v.post.status.$invalid }"
              v-model="$v.post.status.$model"
              id="post-status"
              data-cy="status"
            >
              <option v-for="postStatus in postStatusValues" :key="postStatus" v-bind:value="postStatus">{{ postStatus }}</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-hash">Hash</label>
            <input
              type="text"
              class="form-control"
              name="hash"
              id="post-hash"
              data-cy="hash"
              :class="{ valid: !$v.post.hash.$invalid, invalid: $v.post.hash.$invalid }"
              v-model="$v.post.hash.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-type">Type</label>
            <select class="form-control" id="post-type" data-cy="type" name="type" v-model="post.type">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="post.type && postTypeOption.id === post.type.id ? post.type : postTypeOption"
                v-for="postTypeOption in postTypes"
                :key="postTypeOption.id"
              >
                {{ postTypeOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-category">Category</label>
            <select class="form-control" id="post-category" data-cy="category" name="category" v-model="post.category">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="post.category && categoryOption.id === post.category.id ? post.category : categoryOption"
                v-for="categoryOption in categories"
                :key="categoryOption.id"
              >
                {{ categoryOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="post-user">User</label>
            <select class="form-control" id="post-user" data-cy="user" name="user" v-model="post.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="post.user && userOption.id === post.user.id ? post.user : userOption"
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
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.post.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./post-update.component.ts"></script>
