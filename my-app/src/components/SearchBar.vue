<template>
  <v-container style="width: 100%;background: #eee;margin-bottom: 5px" fluid>
    <v-row style="height: 160px;" @click="nav(type,id)">
      <v-col cols="2">
        <v-avatar width="100%" height="120px" tile="true">
          <v-img :src='image'></v-img>
        </v-avatar>
      </v-col>
      <v-col cols="7">
        <v-card width="100%" height="120px" style="background: transparent;" tile elevation="0">
          <v-card-title class="til">{{title}}</v-card-title>
          <v-card-text>
            <div class="font-weight-regular over1">{{fstl}}</div>
            <div class="font-weight-regular over2">{{scdl}}</div>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="3" style="margin-top: 50px">
        <ScoreBar v-if="type==='book'" :score="score"></ScoreBar>
        <ScoreBar v-if="type==='film'" :score="score"></ScoreBar>
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
        this.$store.dispatch('changeCurrentBookId',id)
        this.$router.push('/bkshow')
      }else if(type=='film'){
        this.$store.dispatch('changeCurrentBMId',id)
        this.$router.push('/bmshow')
      }else if(type=='team'){
        this.$store.commit('changeGroup',id)
        this.$router.push('/group')
      }
    }
  }
}
</script>

<style scoped>
  .til{
    word-break: break-all;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .over1{
    height: 20px;
    overflow: hidden;
  }
  .over2{
    height:70px;
    overflow: hidden;
  }
</style>
