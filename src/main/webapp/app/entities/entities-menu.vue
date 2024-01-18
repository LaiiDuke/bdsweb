<template>
  <div>
    <b-dropdown-item to="/user-info">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.userInfo')">User Info</span>
    </b-dropdown-item>
    <b-dropdown-item to="/post-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.postType')">Post Type</span>
    </b-dropdown-item>
    <b-dropdown-item to="/category">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.category')">Category</span>
    </b-dropdown-item>
    <b-dropdown-item to="/post">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.post')">Post</span>
    </b-dropdown-item>
    <b-dropdown-item to="/province">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.province')">Province</span>
    </b-dropdown-item>
    <b-dropdown-item to="/district">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.district')">District</span>
    </b-dropdown-item>
    <b-dropdown-item to="/ward">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.ward')">Ward</span>
    </b-dropdown-item>
    <b-dropdown-item to="/street">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.street')">Street</span>
    </b-dropdown-item>
    <b-dropdown-item to="/image">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.image')">Image</span>
    </b-dropdown-item>
    <b-dropdown-item to="/config">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.config')">Config</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
