<template>
  <div class="scope_show">
    <Showcard :poster_img="$store.state.movie_img_prefix+info.id" :filmName="info.filmName"
              :director="info.director" :starring="info.starring"
              :filmTime="info.filmTime" :finalScore="info.finalScore"/>
    <v-row class="intro_outer">
      <v-col cols="4" class="intro-left">
        <div class="intro">简介</div>
      </v-col>
      <v-col cols="7" class="intro-right">
        <div class="intro-content">{{info.intro}}</div>
      </v-col>
    </v-row>
    <CommentsStar :star_reply_name="star_reply_name" :reply_content="reply_content"
                  :like_num="like_num" :comment_num="comment_num" :avatar_img="avatar_img"/>
    <v-row>
      <v-col v-for="(data, index) in comments" :key="index">
        <SelfComments :avatar_img="avatar_prefix + data.reply.replyerId" :name="data.reply.replyerId" :comment="data.reply.content"
                      :title="data.reply.tilte" :id="data.reply.id"/>
      </v-col>
    </v-row>
    <PostReply v-if="$store.state.logined"
               :user_id="this.$store.state.account"
               :id="bm_id" :type="type" v-on:child-say="listenToMyBoy"
               @replyState="refresh"/>
    <v-row v-else>
      <v-row cols="12"><p class="pls">请登录再评论</p></v-row>
    </v-row>
  </div>
</template>

<script>
import NavBar from '../components/NavBar';
import Showcard from '../components/Showcard';
import CommentsStar from '../components/Comments-star';
import ScoreBar from '../components/ScoreBar';
import HistoryComment from '../components/HistoryComment';
import PostReply from '../components/PostReply';
import SelfComments from '../components/SelfComments';
export default {
  data () {
    return {

      flag:false,
      type:'2',

      // reply
      bm_id:'',

      info:{},

      // CommentsStar
      avatar_img:'',
      star_reply_name:'',
      reply_content: '',
      like_num: '',
      comment_num: '',
      avatar_prefix:'http://114.115.151.96:8666/ProfilePicture/UserAccount/',

      // comments
      comments:[
        {},
        {}
      ]
    }
  },
  components: {
    NavBar,
    Showcard,
    CommentsStar,
    ScoreBar,
    PostReply,
    HistoryComment,
    SelfComments,
  },
  mounted() {
    this.bm_id = this.$store.state.currentId;
    this.initBMInfo();
    this.initBestReply();
    this.initComments();
  },
  methods:{
    refresh(state){
      if(state=='done'){
        this.initComments();
      }
    },
    listenToMyBoy: function (somedata){
      this.flag = somedata;
      if(this.flag){
        this.initComments();
        this.flag = false;
      }
    },
    initComments:function () {
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/reply/list',
        data: {
          id: this.$store.state.currentId
        },
        crossDomain: true
      }).then(body =>{
        this.comments = body.data;
      });
    },
    initBMInfo: function(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/film/find',
        data: {
          id: this.$store.state.currentId
        },
        crossDomain: true
      }).then(body =>{
        console.log('movie!')
        console.log(body)
        this.info = body.data;
      });
    },
    initBestReply: function(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/search/likebestReply',
        data: {
          id: this.$store.state.currentId
        },
        crossDomain: true
      }).then(body =>{
        console.log(body);
        this.star_reply_name = body.data.reply.id;
        this.reply_content = body.data.reply.content;
        this.like_num = body.data.likes;
        this.comment_num = '255';
        this.avatar_img = this.$store.state.avatar_img_prefix + body.data.reply.id;
      });
    }
  }
};
</script>

<style scoped>

  .pls{
    color: white;
    background-color: #CCC;
    height: 50px;
    margin-top:50px;
    width: 100%;
    display:-webkit-box;
    -webkit-box-align:center;/* 垂直居中 */
    -webkit-box-pack:center;/* 水平居中 */
  }
  .intro {
    color:#CCC;
    font-size:40px;
    margin-left: 60%;
    padding-left: auto;
    margin-top: 20px;
  }

  .intro_outer{
    padding-top: 20px;
    padding-bottom: 40px;
  }
  .intro-content{
    color:black;
    font-size:16px;
    margin-top: 20px;
  }
</style>
