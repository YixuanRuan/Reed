<template>
  <div>
    <h1><textarea v-model="title_value" placeholder="请输入标题"
                  style="height: 40px;width: 100%; margin: 20px; outline: none;">标题</textarea></h1>
    <mavon-editor v-model="value" style="height:650px"/>
    <v-btn depressed large color="teal"  class="post_btn" @click="post">发帖</v-btn>
    <v-btn depressed large color="teal" class="post_btn">清空</v-btn>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        title_value:'',
        value: '',
        defaultData: "preview"
      };
    },
    methods:{
      post (){
        this.axios({
          method: 'post',
          url: 'http://114.115.151.96:8666/Posting/Add',
          data: {
            account:this.$store.state.account,
            teamId:this.$route.params.teamId,
            title: this.title_value,
            content: this.value
          },
          crossDomain: true
        }).then(body => {
          console.log(body)
          this.clean()
        })
      },
      clean (){
        this.title_value = ''
        this.value = ''
      }
    }
  };
</script>

<style scoped>
  .post_btn{
    width: 22%;
    margin: 12px;
    color: white;
  }
</style>
