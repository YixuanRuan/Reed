<template>
  <v-card
    color="#CCC"
    height="500px"
  >
    <v-img
      :src="$store.state.group.imgSrc+$store.state.group.groupId"
      height="380px"
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
  .btn{
    background-color: #CCC;
  }
</style>

<script>
import GroupCard from "./GroupCard"
export default {
  name: 'GroupHead',
  components: {
    GroupCard
  }
};
</script>
