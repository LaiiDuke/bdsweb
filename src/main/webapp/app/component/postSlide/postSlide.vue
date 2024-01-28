<template>
  <div class="item">
    <div class="row">
      <post-component v-for="(item, ind) in postInSlide" :key="ind" :post-obj="item"></post-component>
    </div>
  </div>
</template>

<!--<script lang="ts" src="./post.component.ts"></script>-->
<!-- Scripts -->
<script>
import PostComponent from '@/component/post/post.vue';

export default {
  name: 'PostSlideComponent',
  components: {
    PostComponent,
  },
  props: {
    lstPost: { type: Array, default: [] },
    index: { type: Number, default: 1 },
  },
  data() {
    return {
      postInSlide: [],
    };
  },
  created() {
    this.postInSlide = this.lstPost.slice(this.index * 2 - 2, this.index * 2);
    console.log(this.postInSlide);
  },
};
</script>
