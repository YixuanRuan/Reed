<template>
<div>
  <v-container fluid>
    <v-row>
      <v-col cols="1"></v-col>
      <v-col cols="8">
        <v-row>
          <v-col cols="10">
            <div class="heading group_head">我的小组消息</div>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="4" v-for="i in $store.state.myGroup.comments[page-1].length" :key="i">
            <Comments
              class="comments"
              :groupId="$store.state.myGroup.comments[page-1][i-1]['teamId']"
              :name="$store.state.myGroup.comments[page-1][i-1]['username']"
              :avatar_img="$store.state.avatar_img_prefix+$store.state.myGroup.comments[page-1][i-1]['account']"
              :team_name="$store.state.myGroup.comments[page-1][i-1]['teamName']"
              :team_img="$store.state.avatar_img_prefix+$store.state.myGroup.comments[page-1][i-1]['teamId']"
              :title="$store.state.myGroup.comments[page-1][i-1]['title']"
              :content="$store.state.myGroup.comments[page-1][i-1]['content']"
            ></Comments>
          </v-col>
        </v-row>
        <v-row justify='center' align='center'>
          <v-pagination
            v-model='page'
            :disabled='disabled'
            :length='$store.state.myGroup.pageNum'
            :next-icon='nextIcon'
            :prev-icon='prevIcon'
            :page= "page"
            :total-visible='totalVisible'
            color='#ccc'
          ></v-pagination>
        </v-row>
      </v-col>
      <v-col cols="2">
        <div class="fixed">
          <v-row >
            <v-col cols="10">
              <div class="heading group_head" style="min-width: 150px;">我加入的小组</div>
            </v-col>
          </v-row>
          <v-row style="width: 100%;">
            <v-col cols="4" v-for="i in myGroups.length" :key="i">
              <GroupCardShort :groupId="myGroups[i-1]['team']['id']"
                              :img="$store.state.avatar_img_prefix+myGroups[i-1]['team']['id']"
                              :groupName="myGroups[i-1]['team']['teamName']"></GroupCardShort>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-btn color="#ccc" style="color: white; margin-top: 30px;" @click="goToGroupCreate()">创建小组</v-btn>
            </v-col>
          </v-row>
        </div>
      </v-col>
      <v-col cols="1"></v-col>
    </v-row>
  </v-container>
</div>
</template>

<script>
import Comments from '../components/Comments.vue'
import GroupCardShort from '../components/GroupCardShort.vue'

export default {
  name: 'MyGroup',
  data () {
    return {
      page: 1,
      circle: false,
      disabled: false,
      nextIcon: 'mdi-arrow-right',
      prevIcon: 'mdi-arrow-left',
      totalVisible: 10,
      myGroups:[]
    }
  },
  components: {
    Comments,
    GroupCardShort
  },
  methods: {
    goToGroupCreate () {
      this.$router.push({ path: '/groupCreate' })
    }
  },
  created () {
    this.$store.commit('handleMyGroup')
    console.log(1)
  },
  mounted() {
    this.axios({
      method: 'post',
      url: 'http://114.115.151.96:8666/Team/findteambyuser',
      data: {
        id: this.$store.state.account
      },
      crossDomain: true
    }).then(body => {
      this.myGroups = body.data
      console.log(this.myGroups)
    })
  }
}
</script>

<style scoped>
.comments{
  margin-left: 120px;
}
.heading{
  color: black;
  font-size: larger;
}
.group_head{
  align-content: center;
  justify-content: center;
  text-align: left;
  height: 50px;
  padding: 10px;
  color: white;
  font-size: larger;
  background-color: #ccc;
  box-shadow: 5px 5px 10px #888888;
  border-radius: 5px;
}
.fixed{
  position: fixed;
  margin-right: 80px;
  margin-left: 25px;
}
</style>
