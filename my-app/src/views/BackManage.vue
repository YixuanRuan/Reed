<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div class="outer">
    <div class="add-swiper">
      <div class="title">添加轮播图</div>
      <v-btn tile elevation="0" style="background-color: #aaa;color:#fff;margin-bottom: 20px;margin-right: 30px">上传图片</v-btn>
      <v-btn tile elevation="0" style="background-color: #aaa;color:#fff;margin-bottom: 20px">确认</v-btn>
      <v-textarea
        background-color="white"
        color="black"
        label="帖子链接"
        filled
        auto-grow
        style="width: 80%"
      ></v-textarea>
    </div>
    <div class="delete-swiper">
      <div class="title">删除轮播图</div>
      <v-menu open-on-hover top offset-y>
        <template v-slot:activator="{ on }">
          <v-btn
            v-on="on"
            style="background-color: #aaa;color: #fff"
            tile
            elevation="0"
          >
            选择轮播图
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            v-for="(data, index) in swipers"
            :key="index"
            @click=""
          >
            <v-list-item-title>{{ data.content }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-btn tile elevation="0" style="background-color: #aaa;color:#fff;margin-bottom: 20px;margin-left: 30px">确认删除</v-btn>
    </div>

    <div class="add-content">
      <div class="title">处理举报</div>
      <v-row>
        <v-col v-for="(data,index) in reported" :key="index">
          <div class="report-manage">
            <Comments :name="data.Posting.account" :team_name="data.Team.teamName" :title="data.Posting.title"
                      :content="data.Posting.content" :avatar_img="img_prefix + data.Posting.account"
                      :team_img="img_prefix + data.Team.id" :id="data.Posting.id"></Comments>
          </div>
        </v-col>
      </v-row>
    </div>
  </div>
</template>

<script>
import Comments from "../components/Comments";
export default {
  components:{
    Comments,
  },
  data (){
      return {
        img_prefix: 'http://114.115.151.96:8666/ProfilePicture/UserAccount/',
        avatar_img:'',
        team_img:'',
        swipers:[
            {
                index:1,
                content:'某个活动1',
                picUrl:''
            },{
                index:2,
                content:'某个推送2',
                picUrl:''
            },{
                index:3,
                content:'某个东西3',
                picUrl:''
            },{
                index:4,
                content:'某个东西4',
                picUrl:''
            },
        ],
        reported:[
              {
                  index:1,
                  content:'zzyy1',
              },{
                  index:2,
                  content:'yhsq2',
              },{
                  index:3,
                  content:'xxbl3',
              },{
                  index:4,
                  content:'fd4',
              },
        ]
      }
  },
  mounted() {
    this.getReport()
  },
  methods:{
    getReport(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/report/list',
        data: {
        },
        crossDomain: true
      }).then(body => {
        this.reported = body.data
        console.log('reported', this.reported)
      })
    }
  }
}
</script>

<style scoped>
  .title{
    color: #aaa;
    font-size: 24px;
    margin-bottom: 20px;
  }

  .outer{
    padding-left: 30px;
    padding-right: 30px;
    padding-top: 30px;
  }
</style>
