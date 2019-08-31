<template>
  <v-card
    class="card"
    color="#CCC"
    height="700px"
    elevation="0"
    tile
    align="center"
    justify="center"
  >
    <v-row class="dimback">
      <v-img class="pic" :src="poster_img"/>
    </v-row>
    <v-row no-gutters style="width: 1000px;position:relative;">
      <v-col cols="3" style="margin-left: 200px">
        <div class="left-back">
          <img :src="poster_img" class="content-left">
        </div>
        <ScoreBar class="scorebar" :score="finalScore"/>
      </v-col>

      <v-col cols="5" class="content-right">
        <v-col>
          <div class="book-name">{{bookName}}</div>
          <div class="author-name">作者：{{author}}</div>
          <div class="author-name" style="max-height: 30px;overflow: hidden">出版商：{{publisher}}</div>
          <div class="author-name">ISBN：{{isbn}}</div>
          <v-row class="vote-button" justify="start" align="end">
            <v-btn class="ma-2" color="white" dark small elevation="0" @click="thumbDown" :disabled="voted">
              <v-icon size="28px" color="red">mdi-thumb-down</v-icon>
            </v-btn>
            <v-btn class="ma-2" color="white" dark small elevation="0" @click="thumbUp" :disabled="voted">
              <v-icon size="28px" color="green">mdi-thumb-up</v-icon>
            </v-btn>
          </v-row>
        </v-col>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import ScoreBar from '../components/ScoreBar'
export default {
  name: 'Showcard',
  components: { ScoreBar },
    mounted () {
      this.getVoted()
      this.getScore()
    },
   data () {
    return {

    }
  },
  props: ['poster_img', 'bookName', 'author',
    'publisher', 'isbn', 'finalScore', 'intro', 'id', 'voted'],
  methods: {
    thumbUp: function () {
      this.axios.post('http://114.115.151.96:8666/book/grade', {
        account: this.$store.state.account,
        id: this.$store.state.currentBookId,
        grade: 1
      }
      )
        .then(function (response) {
          console.log(response)
        })
    },
    thumbDown: function () {
      this.axios.post('http://114.115.151.96:8666/book/grade', {
        account: this.$store.state.account,
        id: this.$store.state.currentBookId,
        grade: 0
      }
      )
        .then(function (response) {
          console.log(response)
        })
    },
    getVoted: function () {
      this.axios.post('http://114.115.151.96:8666/isgrade', {
        account: this.$store.state.account,
        id: this.$store.state.currentBookId
      }
      )
        .then(function (response) {
          console.log(response)
          this.voted = response.data
        })
    },
    getScore: function() {
      this.axios.post('http://114.115.151.96:8666/book/find', {
          id: this.id
        }
      )
      .then(function (response) {
        console.log(response)
        this.finalScore = response.data.score
      })
    },
  }
}
</script>
<style scoped>
  .card{

  }
  .dimback{
    background: #EEE;
  }
  .pic{
    /*background: url("../imgs/ROMA-web.jpg") no-repeat  -120px -900px;*/
    background-repeat: cover;
    background-position: center center;
    height:400px;
    width: 100%;
    opacity:0.3;
  }
  .scorebar{

  }
  .book-name{
    font-size: 36px;
    float: left;
    width: 100%;
    text-align: left;
  }
  .author-name{
    color: aliceblue;
    font-size: 18px;
    float: left;
    width: 100%;
    text-align: left;
  }
  .content-left{
    width: 190px;
    height: 283px;
  }
  .left-back{
    background-color: #AAA;
    margin-top: -130px;
    width: 200px;
    padding: 5px;
    z-index: auto;

    display: inline-flex;
  }
  .content-right{
    width: 70%;
    font-size: 50px;
    display: flex;
    justify-content: left;
  }

  .vote-button{
    width: 100%;
  }
</style>
