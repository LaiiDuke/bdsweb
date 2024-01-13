<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="image">
        <h2 class="jh-entity-heading" data-cy="imageDetailsHeading"><span>Image</span> {{ image.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Data</span>
          </dt>
          <dd>
            <div v-if="image.data">
              <a v-on:click="openFile(image.dataContentType, image.data)">
                <img v-bind:src="'data:' + image.dataContentType + ';base64,' + image.data" style="max-width: 100%" alt="image image" />
              </a>
              {{ image.dataContentType }}, {{ byteSize(image.data) }}
            </div>
          </dd>
          <dt>
            <span>Url</span>
          </dt>
          <dd>
            <span>{{ image.url }}</span>
          </dd>
          <dt>
            <span>Post</span>
          </dt>
          <dd>
            <div v-if="image.post">
              <router-link :to="{ name: 'PostView', params: { postId: image.post.id } }">{{ image.post.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="image.id" :to="{ name: 'ImageEdit', params: { imageId: image.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./image-details.component.ts"></script>
