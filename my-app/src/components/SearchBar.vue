<template>
  <v-container style="width: 100%;" fluid>
    <v-row style="height: 150px;" @click="nav(type,id)">
      <v-col cols="2">
        <v-avatar width="100%" height="100%" tile="true">
          <v-img :src="image"></v-img>
        </v-avatar>
      </v-col>
      <v-col cols="7">
        <v-card width="100%" height="150px">
          <v-card-title>{{title}}</v-card-title>
          <v-card-text>
            <div class="font-weight-regular over1">{{fstl}}</div>
            <div class="font-weight-regular over2">{{scdl}}</div>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="3">
        <ScoreBar :score="score"></ScoreBar>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import ScoreBar from "./ScoreBar"
export default {
  components: {
    ScoreBar
  },
  name: "SearchBar",
  props:['image','title','fstl','scdl','score','type','id'],
  methods:{
    nav(type,id){
      if(type=='book'){
        this.$store.commit("changeBook",id)
        this.$router.push()
      }else if(type=='film'){
        this.$store.commit("changeFilm",id)
        this.$router.push()
      }else if(type=='team'){
        this.$store.commit("changeGroup",id)
        this.$router.push("/group")
      }
    }
  }
}
</script>

<style scoped>
  .over1{
    height: 20px;
    overflow: hidden;
  }
  .over2{
    height:70px;
    overflow: hidden;
  }
</style>
