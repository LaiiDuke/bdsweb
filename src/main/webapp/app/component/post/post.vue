<template>
  <div class="col-lg-12">
    <div class="listing-item">
      <div class="left-image">
        <a href="#"><img src="../../assets/images/listing-01.jpg" alt="" /></a>
      </div>
      <div class="right-content align-self-center">
        <div class="col-lg-8">
          <a href="#"
            ><h4>{{ postObj.title }}</h4></a
          >
          <h6>by: Sale Agent</h6>
          <span class="price">
            <div class="icon"><img src="../../assets/images/listing-icon-01.png" alt="" /></div>
            {{ postObj.price }} tỷ
          </span>
          <span class="details"
            >Details: <em>{{ postObj.square }} m2</em></span
          >
          <ul class="info">
            <li><img src="../../assets/images/listing-icon-02.png" alt="" /> {{ postObj.numberOfBedroom }} Bedrooms</li>
            <li><img src="../../assets/images/listing-icon-03.png" alt="" /> {{ postObj.numberOfFloors }} Floors</li>
          </ul>
          <span class="details">Địa chỉ: {{ postObj.address }}</span>
        </div>

        <ul class="rate">
          <li v-for="index in Math.min(postObj.star, 5)" :key="index"><i class="fa fa-star-o"></i></li>
          <li>(18) Reviews</li>
        </ul>
        <ul class="size">
          <li>KT: {{ postObj.length }}m x {{ postObj.width }}m</li>
        </ul>

        <div class="main-white-button">
          <a href="/detail"><i class="fa fa-eye"></i> Contact Now</a>
        </div>
      </div>
    </div>
  </div>
</template>

<!--<script lang="ts" src="./post.component.ts"></script>-->
<!-- Scripts -->
<script>
import { Post } from '@/shared/model/post.model';
import 'owl.carousel';
export default {
  name: 'PostComponent',
  props: {
    postObj: { type: Post, default: { title: 'zzz' } },
  },
};
</script>
