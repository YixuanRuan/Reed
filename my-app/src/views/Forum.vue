<template>
<div>
  <Post :name="postContent.posting.account" :avatar_img="avatar_prefix + postContent.posting.account"
        :title="postContent.posting.title" :team_name="postContent.teamName" :team_img="avatar_prefix + postContent.teamId"
        :input="postContent.posting.content" :postId="postContent.posting.id"
  />
  <v-row justify="start">

    <v-col v-for="(data, index) in selfComments"
           :key="index"
           md="4"
    >
      <SelfComments style="width: 400px;" :reply="reply_flag" :toptag="toptag_flag"
                    :bm_comment="bm_comment" :name="data.reply.replyerId" :comment="data.reply.content"
                    :avatar_img="avatar_prefix+data.reply.replyerId" :num_like="data.likes"
                    :type="comment_type" :id="data.reply.id"
      />
    </v-col>
  </v-row>

  <PostReply v-if="$store.state.logined" :type="post_type" :user_id="this.$store.state.account" :id="postId" :onsubmit="getReply"/>
  <v-row v-else>
    <v-row cols="12"><p class="pls">请登录再评论</p></v-row>
  </v-row>
</div>
</template>

<script>
import SelfComments from '../components/SelfComments'
import Post from '../components/Post'
import PostReply from '../components/PostReply'

export default {
  name: 'Forum',
  data () {
    return {
      comment_type:3,
      toptag_flag: 0,
      reply_flag: 0,
      bm_comment:true,
      post_type: 4,
      postContent: '',
      avatar_prefix: 'http://114.115.151.96:8666/ProfilePicture/UserAccount/',
      selfComments: [],
      postId: '',
    }
  },
  components: {
    Post,
    SelfComments,
    PostReply
  },
  mounted () {
    this.getRouterData()
    this.initPost()
    this.getReply()
    this.addHistory()
  },
  methods: {
    addHistory () {
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/ViewHistory/AddPosting',
        data: {
          id: this.postId,
          account: this.$store.state.account
        },
        crossDomain: true
      })
    },
    getRouterData() {
      this.postId = this.$route.params.postId
      console.log('postId', this.postId)
    },
    initPost(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Posting/FindOne',
        data: {
          id: this.postId
        },
        crossDomain: true
      }).then(body => {
        this.postContent = body.data
        console.log('post', this.postContent)
      })
    },
    getReply(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/reply/list',
        data: {
          id: this.postId,
          type: 4
        },
        crossDomain: true
      }).then(body => {
        this.selfComments = body.data
        // console.log(this.postId)
        console.log('reply', body.data)
        this.$forceUpdate
      })
    }
  }
}
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
</style>
