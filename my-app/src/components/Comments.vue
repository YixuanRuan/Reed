<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-card
    class="mx-auto comments-card"
    color="#ccc"
    max-width="400"
    elevation="5"
    style="border-radius: 0; width: 400px"
  >
    <div class="topper">
      <div class="toptag">
        <div v-if="toptag==1" class="topped">置顶</div>
        <div v-if="toptag==2" class="essence">精华</div>
      </div>
    </div>

    <v-card-actions>
      <v-list-item class="grow">
        <v-row>

          <v-col cols="3">
        <v-list-item-avatar tile color="grey darken-3" style="width: 60px;height: 60px">
          <v-img
            :src="avatar_img"
          ></v-img>
        </v-list-item-avatar>
          </v-col>

          <v-col cols="3">
          <v-list-item-content class="user-name">
          <v-list-item-title class="user-name-text">{{name}}</v-list-item-title>
        </v-list-item-content>
          </v-col>

          <v-col cols="3">
          <v-list-item-content class="team-name">

          <v-list-item-title @click="goToTeam(groupId)">
            <div class="from">
              <p>来自</p>
            </div>
            <div class="team-name-text">
              <p>{{team_name}}</p>
            </div>

          </v-list-item-title>
        </v-list-item-content>
          </v-col>
          <v-col cols="3">

        <v-list-item-avatar tile color="grey darken-3" @click="goToTeam(groupId)" style="width: 50px;height: 50px">
          <v-img
            class=" team-avatar"
            :src="team_img"
          ></v-img>
        </v-list-item-avatar>
          </v-col>
        </v-row>

      </v-list-item>
    </v-card-actions>
    <v-card-text class="text-title font-weight-bold" @click="goToComments">
       {{title}}
    </v-card-text>

    <v-card-text class="text-content" @click="goToComments">
      {{content}}
    </v-card-text>
    <div class="option-menu">
      <v-menu v-if="userType !== 0" bottom offset-y nudge-bottom="10" :close-on-content-click="closeOnContentClick" max-height="300" style="float:left !important" absolute open-on-click>
        <template v-slot:activator="{ on }">
          <v-btn
            dark
            icon
            v-on="on"
          >
            <v-icon>mdi-menu</v-icon>
          </v-btn>
        </template>

        <v-list style="background: #EEEEEE;padding: 2px;width: 130px">
          <v-list-item v-if="userType === 1" @click="delReport" class="card-option justify-center">忽略</v-list-item>
          <v-list-item v-if="userType === 1" @click="delPost" class="card-option justify-center">删除</v-list-item>
          <v-list-item v-if="userType === 2" @click="setTop" class="card-option justify-center">置顶</v-list-item>
          <v-list-item v-if="userType === 2" @click="setFine" class="card-option justify-center">设为精华</v-list-item>
        </v-list>
      </v-menu>
      <v-card-actions class="justify-end" style="padding-bottom: 0px; width: 100%">
        <v-list-item class="grow" style="width:100px">
          <v-row
            align="center"
            justify="end"
            class="comment-bottom"
          >
            <v-icon class="mr-1" color="white" @click="routerTo()">mdi-comment</v-icon>
            <span class="subheading mr-2">{{num_comment}}</span>
            <span class="mr-1"></span>
            <v-icon class="mr-1" :color="like_color" @click="like" v-if="userType != 1">mdi-thumb-up</v-icon>
            <span class="subheading" v-if="userType != 1">{{num_like}}</span>
          </v-row>
        </v-list-item>
      </v-card-actions>
    </div>
  </v-card>
</template>

<script>
export default {
  name: 'Comments',
  data: () => ({
    like_color: 'white'
  }),
  mounted () {
    this.isLiked()
    this.clickLike()
  },
  props: {
    toptag: {
      default: 0
    },
    reportId: {
      default: ''
    },
    userType: {
      default: 2
    },
    groupId: {
      default: 0
    },
    avatar_img: {
      default: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2579313585,1854004294&fm=26&gp=0.jpg'
    },
    name: {
      default: 'Evan You'
    },
    team_img: {
      default: 'https://upload-images.jianshu.io/upload_images/2707438-61bec868c535b5d2.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/640/format/webp'
    },
    team_name: {
      default: '诗和远方'
    },
    title: {
      default: '大家更喜欢什么风格的诗呢？'
    },
    content: {
      default: '"Turns out semicolon-less style is easier and safer in TS because most gotcha edge cases are type invalid as well."'
    },
    num_comment: {
      default: 256
    },
    num_like: {
      default: 45
    },
    id: {
      default: ''
    },
    onsubmit: {
      type: Function,
      default: null
    }
  },
  methods: {
    setTop () {
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Posting/settop',
        data: {
          id: this.id,
          status: 1
        },
        crossDomain: true
      }).then(body => {
        console.log(11111111111)
        this.toptag = 1
      })
    },
    setFine () {
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Posting/setbest',
        data: {
          id: this.id,
          status: 1
        },
        crossDomain: true
      }).then(body => {
        this.toptag = 2
      })
    },
    clickLike: function () {
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Like/CountNum',
        data: {
          postingId: this.id
        },
        crossDomain: true
      }).then(body => {
        this.num_like = body.data
      })
    },
    like: function () {
      this.like_color = (this.like_color == 'white') ? 'red' : 'white'
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Like/ChangeStatus',
        data: {
          account: this.$store.state.account,
          postingId: this.id,
          type: 4
        },
        crossDomain: true
      }).then(body => {
        this.clickLike()
      })
    },
    isLiked: function () {
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Like/IsLike',
        data: {
          account: this.$store.state.account,
          postingId: this.id
        },
        crossDomain: true
      }).then(body => {
        console.log(body)
        this.like_color = body.data ? 'red' : 'white'
      })
    },
    goToComments () {
      this.$router.push({
        name: `forum`,
        params: {
          postId: this.id
        }
      })
    },
    goToTeam (groupId) {
      this.$store.commit('changeGroup', groupId)
      this.$router.push('group')
    },
    routerTo () {
      this.$router.push({
        name: `forum`,
        params: {
          postId: this.id
        }
      })
    },
    delReport () {
      console.log('reported', this.reported)
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/report/delete',
        data: {
          id: this.reportId
        },
        crossDomain: true
      }).then(body => {
        this.onsubmit()
      })
    },
    delPost () {
      console.log(111111111111111)
      console.log(this.id)
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Posting/Del',
        data: {
          postingId: this.id
        },
        crossDomain: true
      }).then(body => {
        this.delReport()
      })
    }
  }
}
</script>

<style scoped>

  .topped,
  .essence{
    height: 30px;
    font-size: 20px;
    border-bottom-left-radius: 10px;
    width: 100px;
    text-align: center;
    float: right;
  }

  .topped{
    background: #aaa;
    color: #fff;
  }
  .essence{
    background: #fff;
    color: #aaa;
  }

  .topper{
    display: flex;
    flex-direction: row;
  }

  .toptag{
    width: 100%;
  }

  .comments-card{
    margin: 20px;

  }

  .team-avatar{

  }
  .team-name{
    margin-bottom: -20px;
    color: white;
  }
  .team-name-text{
    text-align: right;
    font-size: 18px;
  }

  .from{
    text-align: right;
    font-size: 14px;
    margin-bottom: -10px;

  }

  .user-name{
    margin-left: -5px;
    color: white;
  }

  .user-name-text{
    font-size: 24px;
  }

  .text-title{
    font-size: 20px;
    margin-bottom: -15px;
    margin-top: -15px;
  }

  .text-content{
    font-size: 14px;
    padding-top: 5px;
    padding-bottom: 5px;
    margin-top: -15px;
    margin-bottom: -15px;
  }

  .comment-bottom{
    color: white;
    margin-right: 0px;
  }

  .card-option{
    margin-top: 3px;
    font-size: 13px;
    color: #646464;
    text-align: center;
    height: 20px;
  }

  .option-menu{
    display: flex;
    flex-direction: row;
    align-items: center;
    width: 100%;
    padding-left: 10px;
  }
</style>
