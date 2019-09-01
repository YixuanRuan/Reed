<template>
  <div class="outer">
    <div>
      <div class="wordcloud-title">
        标签热度词云
      </div>
      <TagWordcloud2 />
      <div class="wordcloud-title">
        标签热度排行
      </div>
      <div class="tag-rank-outer">
        <v-col>
          <v-row v-for="(data,rank) in Tag" :key="rank" style="margin-bottom: 5px">
            <div class="tag-card">
              <div class="tag-rank">{{data.rank}}. {{data.name}}</div>
              <div class="tag-view">{{data.view}}</div>
            </div>
            <v-icon v-if="data.trending===0" size="16px" style="color:green">mdi-trending-up</v-icon>
            <v-icon v-if="data.trending===1" size="16px" style="color:#aaa">mdi-minus</v-icon>
            <v-icon v-if="data.trending===2" size="16px" style="color:red">mdi-trending-down</v-icon>
          </v-row>
        </v-col>
      </div>
    </div>
  </div>
</template>

<script>
  import TagWordcloud2 from "../components/TagWordcloud2";
  export default {
    name: 'SideBar2',
    components:{
      TagWordcloud2,
    },
    data() {
      return{
        Tag:[
          {
            "rank":1,
            "name":"科幻",
            "view":4890,
            "trending":0,
          },{
            "rank":2,
            "name":"爱情",
            "view":2300,
            "trending":0,
          },{
            "rank":3,
            "name":"动作",
            "view":1890,
            "trending":2,
          },{
            "rank":4,
            "name":"古装",
            "view":1200,
            "trending":1,
          },{
            "rank":5,
            "name":"都市",
            "view":890,
            "trending":2,
          },{
            "rank":6,
            "name":"励志",
            "view":300,
            "trending":1,
          }
        ],
      }
    }
  }
</script>

<style scoped>
  .wordcloud-title{
    background: #eee;
    color: #aaa;
    font-size: 18px;
    height: 40px;
    width: 300px;
    display: flex;
    padding-left: 20px;
    flex-direction: row;
    align-items: center;
  }

  .outer{
    width: 300px;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  .tag-card{
    float: left;
    color: rgb(100,100,100);
    font-size: 16px;
    width: 250px;
  }

  .tag-rank{
    float: left;
    display: flex;
    flex-direction: row;
    justify-content: left;
    align-items: center;
    padding-left: 30px;
  }

  .tag-view{
    float: right;
    display: flex;
    flex-direction: row;
    justify-content: right;
    align-items: center;
    padding-right: 30px
  }
</style>
