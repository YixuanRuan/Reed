<template>
  <v-card
    color="#CCC"
    height="500px"
    tile
    elevation="0"
  >
    <v-img
      :src="$store.state.group.imgSrc+$store.state.group.groupId"
      height="380px"
      style="opacity: 0.5"
    ></v-img>
    <v-row>
      <v-col cols="3" sm="4" md="5"></v-col>
      <v-col cols="6" sm="4" md="2">
        <GroupCard class="card"
           :img="$store.state.group.imgSrc+$store.state.group.groupId"
           :groupName="$store.state.group.groupName"
           :groupMotto="$store.state.group.groupMotto"
           :groupId="$store.state.group.groupId"
        />
      </v-col>
      <v-col cols="3" sm="4" md="5"></v-col>
    </v-row>
  </v-card>
</template>

<style scoped>
.card{
  position: absolute;
  top: 100px;
}
</style>

<script>
import GroupCard from "./GroupCard"

export default {
  name: 'GroupHead',
  components: {
    GroupCard
  },
  data: () => ({
    groupName: "诗与远方",
    groupMotto: "交流着事宜的人生",
    btn_text:"加入小组",
    join_text: "加入小组",
    post_text: "创建帖子"
  }),
  methods:{
    joinAndPost(){
      this.$store.commit('changeJoinState')
      console.log(this.$store.state.joined)
      this.btn_text = (this.$store.state.joined === true) ? this.post_text : this.join_text
    }
  }
};
</script>
