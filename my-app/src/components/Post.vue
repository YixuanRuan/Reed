<template>
<div class="post-container">
  <v-row no-gutters>
    <v-col cols="2" class="post-avatar">
      <v-img
        class="avatar-img"
        :src="avatar_img"
        height="150px"
        width="150px"
        position="center center"
      ></v-img>
      <v-btn depressed style="margin-left: 60px; margin-top: -20px" @click="report">举报帖子</v-btn>
    </v-col>
    <v-col cols="8" class="post-content">
      <p class="user-name">{{name}}</p>
      <p class="post-name">{{title}}</p>
      <div class="post-content" v-html="compiledMarkdown"></div>
      <!--<p class="post-content">-->
        <!--{{content}}-->
      <!--</p>-->
    </v-col>
    <v-col cols="2" class="post-team">
      <v-img
        class="team-img"
        :src="team_img"
        height="150px"
        width="150px"
        position="center center"
      ></v-img>
      <p class="team-name">{{team_name}}</p>
    </v-col>

  </v-row>
</div>
</template>

<script>

import marked from 'marked'
var rendererMD = new marked.Renderer()
marked.setOptions({
  renderer: rendererMD,
  gfm: true,
  tables: true,
  breaks: false,
  pedantic: false,
  sanitize: false,
  smartLists: true,
  smartypants: false
})

export default {
  name: 'Post',
  data () {
    return {
    }
  },
  mounted () {
  },
  methods: {
    report () {
      console.log('postId',this.postId)
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/report/add',
        data: {
          reporterId: this.$store.state.account,
          reportedId: this.postId
        },
        crossDomain: true
      }).then(body => {
        this.info = body
      })
    }
  },
  computed: {
    compiledMarkdown: function () {
      return marked(this.input, { sanitize: true })
    }
  },
  props: {
    team_img: {
      default: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2579313585,1854004294&fm=26&gp=0.jpg'
    },
    input: {
      default: '# 陈忠智是个大骚货 ## '
    },
    postId: {
      default: ''
    },
    name: {
      default: 'logos'
    },
    avatar_img: {
      default: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2579313585,1854004294&fm=26&gp=0.jpg'
    },
    title: {
      default: '大家更喜欢什么风格的诗呢？'
    },
    team_name: {
      default: '诗和远方'
    },
    content: {
      default: '我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗\n' +
            '        我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗\n' +
          '        我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗\n' +
          '        我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗\n' +
          '        我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗\n' +
          '        我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗\n' +
          '        我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗\n' +
          '        我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗\n' +
          '        我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗\n' +
          '        我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗\n' +
          '        我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗\n' +
          '        我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗我更喜欢现代诗'
    },
    num_like: {
      default: 45
    }
  }
}
</script>

<style scoped>
  .post-container {
    background-color: #CCCCCC;
    padding-left: 50px;
    padding-right: 50px;
  }
  .post-avatar{
  }
  .post-content{
    margin-bottom: 20px;
  }
  .post-team{
  }
  .btn-text-report{
    font-family: "Microsoft YaHei" ! important;
  }
  .team-name{
    display:-webkit-box;
    -webkit-box-align:center;/* 垂直居中 */
    -webkit-box-pack:center;/* 水平居中 */
    margin-top: -20px;
    margin-left: -20px;
    font-size: 30px;
    color:white;
  }
  .avatar-img{
    margin: 40px;
  }
  .team-img{
    margin: 40px;
  }
  .user-name{
    color:white;
    font-size: 32px;
    margin-top: 45px;
    font-family: "Microsoft YaHei" ! important;
  }
  .post-name{
    margin-top: -20px;
    font-weight: bold;
    font-size: 32px;
    font-family: "Microsoft YaHei" ! important;
  }
  .post-content{
    font-size: 16px;
    font-family: "Microsoft YaHei" ! important;
  }

</style>
