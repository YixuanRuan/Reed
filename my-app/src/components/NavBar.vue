<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-card style="border-radius: 0px;">

    <v-tabs
      fixed-tabs
      background-color="secondary"
      dark
      color="white"
      class="nav-container"
      style="border-radius: 0px"
    >
      <div style="width: 1px"></div>
      <router-link class="link-text" to="/">
        <img src= "../imgs/text-only -white.png" class="logo-header">
      </router-link>
      <div class="link-text" ><v-tab to="/" :aria-selected="grow" style="height: 100%">主页</v-tab></div>
      <div class="link-text" ><v-tab to="/bkhome" style="height: 100%">书籍</v-tab></div>
      <div class="link-text" ><v-tab to="/bmhome" style="height: 100%">影视</v-tab></div>
      <div class="link-text" v-if="$store.state.logined"><v-tab :to="group" style="height: 100%">小组</v-tab></div>
      <div class="link-text" v-if="$store.state.logined"><v-tab to="/selfinfo" style="height: 100%">我的</v-tab></div>
      <div style="width: 35%"></div>
<!-------------------------------------------------------------浏览历史------------------------------------------------------------->
      <v-menu v-if="$store.state.logined" open-on-hover bottom offset-y :close-on-content-click="closeOnContentClick">
        <template v-slot:activator="{ on }">
          <v-btn
            dark
            icon
            v-on="on"
            style="margin-top:6px; margin-right: 20px"
          >
            <v-icon>mdi-history</v-icon>
          </v-btn>
        </template>

        <v-tabs v-model="tab"
                class="justify-center"
                style="width: 300px;"
        >
          <v-tab>帖子</v-tab>
          <v-tab>作品</v-tab>
          <v-tabs-slider></v-tabs-slider>
        </v-tabs>

        <v-tabs-items v-model="tab">
          <v-tab-item>
            <div class="history-forum">
              <v-icon color="#aaa" style="margin-right: 20px">mdi-comment</v-icon>
              <div style="font-size: 14px;margin: 10px;width:200px;overflow: hidden">帖子标题标题标题标题</div>
              <v-icon color="#aaa" style="float: right">mdi-delete</v-icon>
            </div>
          </v-tab-item>
          <v-tab-item>
            <div class="history-bm">
              <v-img
                :src="poster_img"
                :aspect-ratio="1"
                max-width="60px"
                max-height="80px"
              />
              <div style="font-size: 14px;margin: 10px;width:200px;overflow: hidden">标题标题标题</div>
              <v-icon color="#aaa" style="float: right">mdi-delete</v-icon>
            </div>
          </v-tab-item>
        </v-tabs-items>
      </v-menu>

<!-------------------------------------------------------------消息列表------------------------------------------------------------->
      <v-menu v-if="$store.state.logined" bottom offset-y nudge-bottom="10" :close-on-content-click="closeOnContentClick" max-height="340">
        <template v-slot:activator="{ on }">
          <v-btn
            dark
            icon
            v-on="on"
            style="margin-top:6px; margin-right: 20px"
            @click="getInformation"
          >
            <v-icon>mdi-message-processing</v-icon>
          </v-btn>
        </template>

        <v-list style="background: #EEEEEE;padding: 5px;">
            <v-list-item
              v-for="(item, index) in $store.state.massage_content"
              :key="index"
              style="background-color:#CACACA; margin-top: 5px"
            >
              <v-row style="width: 300px; height: 105px;">
                <v-col cols="9" @click="routerTo(index)">
                  <p class="massage-title">消息通知</p>
                  <p class="massage-content">您在{{item.teamName}}发表的评论得到回复，点击查看</p>
                </v-col>
                <v-col cols="3">
                  <v-btn dark icon class="icon-delete" v-on:click="deleteMassage(index)">
                    <v-icon large dark >mdi-delete</v-icon>
                  </v-btn>
                </v-col>
              </v-row>
            </v-list-item>
        </v-list>
      </v-menu>
<!------------------------------------------------------------------------------------------------------------------------------>

<!-------------------------------------------------------------收藏夹------------------------------------------------------------->
      <v-menu v-if="$store.state.logined" bottom offset-y nudge-bottom="10" :close-on-content-click="closeOnContentClick" max-height="325">
          <template v-slot:activator="{ on }">
            <v-btn
              dark
              icon
              v-on="on"
              style="margin-top:6px; margin-right: 20px"
              @click="submitCollection"
            >
              <v-icon>mdi-heart</v-icon>
            </v-btn>
          </template>
          <v-list style="background: #EEEEEE;padding: 5px;">
            <draggable class="list-group" :list="$store.state.collection" group="people">
              <v-list-item
                v-for="(item, index) in $store.state.collection"
                :key="index"
                style="background-color:#CACACA; margin-top: 5px"
              >
                <v-row style="width: 330px; height: 100px;">
                  <v-col cols="4">
                    <v-img
                      class="poster"
                      :src="film_prefix+item.id"
                    ></v-img>
                  </v-col>
                  <v-col cols="6">
                    <p class="poster-name">{{item.filmName == null ? item.bookName : item.filmName}}</p>
                    <ScoreBar class="scoreBar" :score="item.score"/>
                  </v-col>
                  <v-col cols="2">
                    <v-btn dark icon class="icon-delete" v-on:click="deleteItem(index)">
                      <v-icon large dark >mdi-delete</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
              </v-list-item>
            </draggable>
          </v-list>
      </v-menu>
<!------------------------------------------------------------------------------------------------------------------------------>

      <v-text-field
        @keyup.enter="submit(keyword)"
        class="mx-xl-n7"
        flat
        hide-details
        label="Search"
        solo-inverted
        style="margin-right: 10px;border-radius: 3px;height: 30px"
        v-model="keyword"
        :value="keyword"
      ></v-text-field>
    </v-tabs>
  </v-card>
</template>

<script>

import ScoreBar from '../components/ScoreBar'
import draggable from 'vuedraggable'

export default {

  components: {
    ScoreBar,
    draggable
  },
  data () {
    return {
      flag: true,
      film_prefix: 'http://114.115.151.96:8666/PosterPicture/MovieAccount/',
      test: '1',
      massage_color: '',
      like_color: '',
      closeOnContentClick: false,
      poster_name: '寄生兽',
      poster_img: 'http://p1.ifengimg.com/cmpp/2016/07/11/17/88967cf4-a794-4a28-9675-c3ee65eebd30_size59_w600_h838.jpeg-contentimage',
      imgUrl: '../imgs/full-logo-white.png',
      tab: null,
      text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
      icons: false,
      centered: false,
      keyword: '',
      grow: false,
      vertical: false,
      prevIcon: false,
      nextIcon: false,
      right: false,
      tabs: 3
    }
  },
  methods: {
    submit: function (keyword) {
      if (keyword.length === 0) {
        keyword = 'everything'
      }
      this.$store.commit('changeKeyword', keyword)
      this.$router.push('/search/')
    },
    routerTo (index) {
      this.$router.push({
        name: `forum`,
        params: {
          postId: this.$store.state.massage_content[index].information.postingId
        }
      })
    },
    like: function () {
      this.like_color = (this.like_color === '') ? 'red' : ''
    },
    massage: function () {
      this.massage_color = this.massage_color === '' ? 'green' : ''
    },
    deleteItem: function (index) {
      this.$store.dispatch('deleteLikeItem', index)
    },
    deleteMassage: function (index) {
      this.$store.dispatch('deleteMassageItem', index)

      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Information/Del',
        data: {
          id: this.$store.state.massage_content[index].information.id
        },
        crossDomain: true
      })
    },
    getInformation () {
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Information/Get',
        data: {
          account: this.$store.state.account
        },
        crossDomain: true
      }).then(body => {
        console.log('massage', body.data)
        this.$store.dispatch('changeMassageData', body.data)
        console.log('massage', massage_content)
      })
    },
    submitCollection () {
      if (this.flag) {
        this.getCollection()
        this.flag = false
      } else {
        this.delCollection()
        this.flag = true
      }
      // this.addCollection()
    },
    delCollection () {
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Collection/Del',
        data: {
          account: this.$store.state.account
        },
        crossDomain: true
      }).then(body => {
        this.addCollection()
      })
    },
    getCollection () {
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Collection/Get',
        data: {
          account: this.$store.state.account
        },
        crossDomain: true
      }).then(body => {
        this.$store.dispatch('changeCollection', body.data)
      })
    },
    addCollection () {
      var i = 0
      for (i = 0; i < this.$store.state.collection.length; i++) {
        console.log(this.$store.state.collection[i].filmName)
        this.axios({
          method: 'post',
          url: 'http://114.115.151.96:8666/Collection/Add',
          data: {
            id: this.$store.state.collection[i].id,
            filmName: this.$store.state.collection[i].filmName,
            score: this.$store.state.collection[i].score,
            account: this.$store.state.account
          },
          crossDomain: true
        })
      }
    },
    computed: {
      group: {
        get () {
          if (this.$store.state.groupStatus.joinedNum == 0) {
            return '/groupFind'
          } else {
            return '/myGroup'
          }
        }
      },
      collection: {
        get () {
          return this.$store.state.collection
        },
        set (newVal) {
          this.$store.commit('handleCollection', newVal)
        }
      },
      massage_content: {
        get () {
          return this.$store.state.massage_content
        },
        set (newVal) {
          this.$store.commit('handleMassageContent', newVal)
        }
      },
      optionLeft () {
        return {
          direction: 2,
          limitMoveNum: 2
        }
      }
    }
  }
}
</script>

<style scoped>

.massage-content{
  font-size: 16px;
  margin-top: -15px;
}

.massage-title{
  font-size: 24px;
  color: white;
}

.scoreBar{
  margin-top: -20px;
  margin-left: -10px;
}

.icon-delete{
  margin-top: 20px;
  margin-left: 0px;
}

.poster-name{
  color:white;
  font-size: 18px;
  margin-left: -25px;
  margin-bottom: 30px;
}
.logo-header{
  width: 70px;
  max-height: 50%;
  margin-top: 11px;
  margin-left: 18px;
  margin-right: 20px;
}
.search{
  float: right;
}
.link-text{
  color: white;
}

a {
  text-decoration: none;
}
.router-link-active {
  text-decoration: none;
}

.poster{
  width: 61px;
  height: 86px;
  margin:-5px 5px 5px -10px;
}

  .history-bm,.history-forum{
    width: 300px;
    height: 80px;
    display: flex;
    flex-direction: row;
    align-items: center;

    padding: 5px;
    margin: 2px;
    background: #eee;
  }
</style>
