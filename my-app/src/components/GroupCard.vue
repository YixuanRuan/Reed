<template>
  <v-card align="center" justify="center" class="group-card">
    <v-img :src="img" height="200px" width="200px" style="margin-top:10px;"></v-img>
    <v-card-title class="heading" style="color: white">{{groupName}}</v-card-title>
    <v-card-text style="color: white">{{groupMotto}}</v-card-text>
    <v-btn v-if="inGroup" class="btn" @click="quit(groupId)">退出小组</v-btn>
    <v-btn v-if="inGroup" class="btn" @click="groupReply(groupId)">创建帖子</v-btn>
    <v-btn v-else class="btn" @click="join(groupId)">加入小组</v-btn>
  </v-card>
</template>

<script>
export default {
  name: 'GroupCard',
  data: () => ({
    inGroup: false
  }),
  props: ['img', 'groupName', 'groupMotto', 'groupId'],
  methods: {
    join (groupId) {
      var that = this
      this.axios.post('http://114.115.151.96:8666/Team/Join', {
        account: this.$store.state.account,
        id: groupId,
        power: 3
      })
        .then(function (response) {
          if (response.data == '0') {
            console.log('join failed')
          } else if (response.data == '1') {
            console.log('join succeed')
            that.inGroup = true
          } else {
            console.log('your code is wrong')
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    quit (groupId) {
      var that = this
      this.axios.post('http://114.115.151.96:8666/Team/out', {
        userid: this.$store.state.account,
        teamid: groupId
      })
        .then(function (response) {
          console.log("quit succeed")
          console.log(response.data)
          that.inGroup = false
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    groupReply(groupId){
      this.$router.push({
        name: 'reply',
        params: {
          teamId: groupId
        }})
    }
  },
  mounted () {
    console.log(this.groupId)
    var that = this
    this.axios.post('http://114.115.151.96:8666/Team/IsJoin', {
      account: this.$store.state.account,
      id: this.groupId
    })
      .then(function (response) {
        console.log(response.data)
        if (response.data) {
          that.inGroup = true
        } else if (response.data==false) {
          that.inGroup = false
        } else {
          console.log('your code is wrong')
        }
      })
      .catch(function (error) {
        console.log(error)
      })
  }
}
</script>

<style scoped>
  .group-card{
    min-width: 220px;
    max-width: 100%;
    height: 370px;
    background-color: black;
  }
  .btn{
    background-color: #CCC;
  }
</style>
