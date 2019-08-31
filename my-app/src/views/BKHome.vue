<template>
  <div>
    <div class="swiper">
      <Swiper :content="$store.state.bookSwiperImgs"/>
    </div>
    <div class="outer">
      <div style="position:relative;margin-top: 50px;margin-left: 30px">
        <SideBar />
      </div>
      <div class="bm-part">
        <p class="content-title">推荐</p>
        <div style="width: 100%; display:-webkit-box; -webkit-box-pack:center; background-color: white">
          <div class="hot-content">
            <BookComment :card_content="today_recommend_content"/>
          </div>
        </div>
        <p class="content-title">热门</p>
        <div style="width: 100%; display:-webkit-box; -webkit-box-pack:center; background-color: white">
          <div class="hot-content">
            <BookComment :card_content="today_hot_content"/>
          </div>
        </div>
        <p class="content-title">最新</p>
        <div style="width: 100%; display:-webkit-box; -webkit-box-pack:center; background-color: white">
          <div class="hot-content">
            <BookComment :card_content="today_new_content"/>
          </div>
        </div>
      </div>
    </div>
    <p class="content-title">精彩评论</p>
    <v-row justify="start" style="margin-left: 10%;margin-right: 10%">
      <v-col v-for="(data, index) in wonderfulComments"
             :key="index"
             md="6"
      >
        <WonderfulComment />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import WonderfulComment from '../components/WonderfulComment'
import BookComment from "../components/BookComment";
import draggable from 'vuedraggable'
import SideBar from './SideBar'
import Swiper from '../components/Swiper'

export default {
  name: 'BKHome',
  components: {
    BookComment,
    WonderfulComment,
    draggable,
    SideBar,
    Swiper
  },
  data () {
    return {
      wonderfulComments: [
        {},
        {},
        {}
      ]
    }
  },
  mounted () {
    this.axios({
      method: 'post',
      url: 'http://114.115.151.96:8666/search/booklikelist',
      data: {
        pagesCount: 20
      },
      crossDomain: true
    }).then(body => {
      this.info = body
      this.$store.dispatch('getTodayBookHot', this.info.data)
      console.log(this.info.data)
    });
    this.axios({
      method: 'post',
      url: 'http://114.115.151.96:8666/search/bookreplylist',
      data: {
        pagesCount: 20
      },
      crossDomain: true
    }).then(body => {
      this.info = body
      this.$store.dispatch('getTodayBookRecommend', this.info.data)
      console.log(this.info.data)
    });
    this.axios({
      method: 'post',
      url: 'http://114.115.151.96:8666/search/booktimelist',
      data: {
        pagesCount: 20
      },
      crossDomain: true
    }).then(body => {
      this.info = body
      this.$store.dispatch('getTodayBookNew', this.info.data)
      console.log(this.info.data)
    });
  },
  computed: {
    today_hot_content: {
      get () {
        return this.$store.state.today_book_hot_content
      },
      set (newVal) {
        this.$store.commit('changeTodayBookHot', newVal)
      }
    },
    today_recommend_content: {
      get () {
        return this.$store.state.today_book_recommend_content
      },
      set (newVal) {
        this.$store.commit('changeTodayBookRecommendContent', newVal)
      }
    },
    today_new_content: {
      get () {
        return this.$store.state.today_book_new_content
      },
      set (newVal) {
        this.$store.commit('changeTodayBookNew', newVal)
      }
    }
  },
  methods: {

  }

}
</script>

<style scoped>
  .content-title{
    margin-left: 50px;
    margin-top: 40px;
    font-size: 32px;
    color:#AAAAAA;
  }
  .hot-content{
    width: 80%;
  }
  .bm-part{
    width: 80%;
  }
  .outer{
    display: flex;
    flex-direction: row;
    justify-content: center;
    background: white;
  }
  .swiper{
    width: 90%;
  }
</style>
