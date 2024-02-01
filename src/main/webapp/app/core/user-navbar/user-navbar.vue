<template>
  <div>
    <!-- ***** Preloader Start ***** -->
    <div id="js-preloader" class="js-preloader">
      <div class="preloader-inner">
        <span class="dot"></span>
        <div class="dots">
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>
    </div>
    <!-- ***** Preloader End ***** -->
    <header class="header-area header-sticky wow slideInDown" data-wow-duration="0.75s" data-wow-delay="0s">
      <div class="container">
        <div class="row">
          <div class="col-12">
            <nav class="main-nav">
              <!-- ***** Logo Start ***** -->
              <a href="/" class="logo"> </a>
              <!-- ***** Logo End ***** -->
              <!-- ***** Menu Start ***** -->
              <ul class="nav">
                <li class="nav-link"><a href="/" class="active">Home</a></li>
                <b-nav-item-dropdown id="categoryBarDropdown" text="Category" v-if="categories && Object.keys(categories).length > 1">
                  <!--                  <span class="no-bold">Category</span>-->
                  <b-dropdown-item v-for="(item, index) in categories" :key="index">
                    <router-link :to="{ name: 'CategoryPost', params: { categoryId: item.id } }" custom v-slot="{ navigate }">
                      <a @click="navigate">{{ item.name }} </a>
                    </router-link>
                  </b-dropdown-item>
                </b-nav-item-dropdown>
                <li class="nav-link"><a href="/list-product">Listing</a></li>
                <li class="nav-link"><a href="/contact">Contact Us</a></li>
                <li>
                  <div class="main-white-button">
                    <a href="#"><i class="fa fa-plus"></i> Add Your Listing</a>
                  </div>
                </li>
              </ul>
              <a class="menu-trigger">
                <span>Menu</span>
              </a>
              <!-- ***** Menu End ***** -->
            </nav>
          </div>
        </div>
      </div>
    </header>
    <div v-if="$route.path.startsWith('/admin')" class="page-heading">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="top-text header-text">
              <h6>Keep in touch with us</h6>
              <h2>Feel free to send us a message about your business needs</h2>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./user-navbar.component.ts"></script>
