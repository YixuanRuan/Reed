<template>
  <div>
    <GroupHead></GroupHead>
    <v-row justify="start">
      <v-col v-for="(data, index) in selfComments"
             :key="index"
      >
        <Comments
          :isbest="data.posting.isbest" type="4" :istop="data.posting.istop"
          :avatar_img="$store.state.avatar_img_prefix+data.posting.account"
          :name="data.posting.account" :comment="data.posting.content"
          :num_comment="data.replyNum" :title="data.posting.title"
          :id="data.posting.id" :num_like="data.likeNum"
          style="width: 400px;" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import GroupHead from "../components/GroupHead";
import Comments from "../components/Comments";
export default {
  name: "Group",
  components: {
    GroupHead,
    Comments
  },
  data () {
    return {
      selfComments: [
        {
          "reply": 0,
          "toptag": 1,
        },
        {
          "reply": 0,
          "toptag": 1,
        },
        {
          "reply": 1,
        },
        {
          "reply": 0,
          "toptag": 2,
        },
        {
          "reply": 2
        }
      ]
    }
  },
  mounted() {
    var that=this
    console.log(this.$store.state.group.groupId)
    this.axios.post('http://114.115.151.96:8666/Posting/GetPostingsByTeamId', {
      teamId: this.$store.state.group.groupId,
    })
      .then(function (response) {
        console.log("returned")
        console.log(response.data)
        that.selfComments = response.data
      })
      .catch(function (error) {
        this.state.search.dataShow = [ { tit: error } ]
      })
  }
}
</script>

<style scoped>

</style>
