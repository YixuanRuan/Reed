<template>
  <div class="reply-container">
    <div class="reply-text-container">
      <p class="reply-text">发表回复</p>
    </div>
    <div class="input-container">
      <v-container class="textarea-container">
        <v-textarea
          background-color="white"
          color="black"
          label="你的回复"
          hint="25字符及以上"
          filled
          auto-grow
          :value="reply"
          v-model="reply"
        ></v-textarea>
        <v-btn depressed large color="#AEAEAE" dark width="160" class="reply-btn" v-on:click="replySubmit">
          发表
        </v-btn>
      </v-container>

    </div>

  </div>

</template>

<script>
export default {

  name: 'PostReply',
  data () {
    return {
      reply: '',
      somedata: true
    }
  },
  methods: {
    addInformation () {
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Information/Add',
        data: {
          postingId: this.id
        },
        crossDomain: true
      }).then(body => {
        this.info = this.id
      })
    },
    replySubmit: function () {
      console.log(this.reply)
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/reply/add',
        data: {
          replyerId: this.user_id,
          content: this.reply,
          placeId: this.id,
          type: this.type,
          istop: false,
          isbest: false
        },
        crossDomain: true
      }).then(body => {
        console.log('-------------------reply-------------------')
        console.log('user_id', this.user_id)
        console.log('id', this.id)
        console.log('reply', this.reply)
        console.log('type', this.type)
        console.log('id', this.id)
        console.log('-----------------reply end-----------------')
        this.reply = ''
        this.addInformation()
        this.onsubmit()
      })
    }
  },
  props: {
    type: {
      default: 1
    },
    user_id: {
      default: ''
    },
    id: {
      default: ''
    },
    onsubmit: {
      type: Function,
      default: null
    }
  }
}
</script>

<style scoped>
  .reply-container{
  }
  .reply-text-container{
    display:-webkit-box;
    -webkit-box-pack:center;/* 水平居中 */

  }
  .reply-text{
    color: #AAAAAA;
    font-size: 48px;
    margin: 20px ;
  }
  .input-container{
    display:-webkit-box;
    -webkit-box-pack:center;/* 水平居中 */
    min-height: 150px;
    width: 100%;
    background-color: #CCCCCC;
  }
  .textarea-container{
    width: 100%
  }
  .reply-btn{
    float:right;
    font-size: 20px;
    margin-right: 10px;
  }

</style>
