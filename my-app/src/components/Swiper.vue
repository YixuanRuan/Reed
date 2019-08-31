<template>
  <div class="outer">
    <swiper style="width: 100%" :options="swiperOption" ref="mySwiper" @someSwiperEvent="callback">
      <!-- slides -->
      <swiper-slide v-for="(data,index) in content" :key="index">
        <div class="item">
          <v-img
            :src="data.imgUrl"
            :aspect-ratio="1"
            min-width="500"
            height="100%"
          ></v-img>
        </div>
      </swiper-slide>
      <!-- Optional controls -->
      <div class="swiper-pagination"  slot="pagination"></div>
      <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
      <div class="swiper-button-next swiper-button-white" slot="button-next"></div>
      <div class="swiper-scrollbar"   slot="scrollbar"></div>
    </swiper>
  </div>
</template>

<script>
import 'swiper/dist/css/swiper.css'
import { swiper, swiperSlide } from 'vue-awesome-swiper'
export default {
  components: {
    swiperSlide,
    swiper
  },
  data () {
    return {
    // swiper轮播的参数
      swiperOption: {
        // 自动播放
        autoplay: {
          delay: 3000,
          topOnLastSlide: false,
          disableOnInteraction: true,
        },
        // 滚动条
        scrollbar: {
          el: '.swiper-scrollbar',
        },
        // 上一张，下一张
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev',
        }
        // 其他参数…………
      },
      content: [
        {
          index: 1,
          imgUrl: require('../imgs/swiper-ontheroad.jpg'),
          text: '1'
        },
        {
          index: 2,
          imgUrl: require('../imgs/swiper-roma.jpg'),
          text: '1'
        },
        {
          index: 3,
          imgUrl: require('../imgs/swiper-string.jpg'),
          text: '1'
        },
    ]
    }
  }
}
</script>

<style scoped>
  .item{
    background: #ccc;
    width: 100%;
    height: 100%;
  }

  .outer{
    width: 100%;
    height: 70vh;
  }
</style>
