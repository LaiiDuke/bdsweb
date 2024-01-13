<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="post">
        <h2 class="jh-entity-heading" data-cy="postDetailsHeading"><span>Post</span> {{ post.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Title</span>
          </dt>
          <dd>
            <span>{{ post.title }}</span>
          </dd>
          <dt>
            <span>Content</span>
          </dt>
          <dd>
            <span>{{ post.content }}</span>
          </dd>
          <dt>
            <span>Price</span>
          </dt>
          <dd>
            <span>{{ post.price }}</span>
          </dd>
          <dt>
            <span>Square</span>
          </dt>
          <dd>
            <span>{{ post.square }}</span>
          </dd>
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ post.status }}</span>
          </dd>
          <dt>
            <span>Hash</span>
          </dt>
          <dd>
            <span>{{ post.hash }}</span>
          </dd>
          <dt>
            <span>Type</span>
          </dt>
          <dd>
            <div v-if="post.type">
              <router-link :to="{ name: 'PostTypeView', params: { postTypeId: post.type.id } }">{{ post.type.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Category</span>
          </dt>
          <dd>
            <div v-if="post.category">
              <router-link :to="{ name: 'CategoryView', params: { categoryId: post.category.id } }">{{ post.category.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>User</span>
          </dt>
          <dd>
            {{ post.user ? post.user.id : '' }}
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="post.id" :to="{ name: 'PostEdit', params: { postId: post.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./post-details.component.ts"></script>
