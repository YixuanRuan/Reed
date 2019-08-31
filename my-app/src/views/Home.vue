<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div>
    <BackGroundVideo/>
    <v-row no-gutters class="login-regist-container">
      <v-col cols="7" >
        <img src= "../imgs/full-logo.png" class="logo-left">
      </v-col>
      <v-col cols="5" class="right-content">
        <form @submit.prevent="login">
          <p class="b-text">人，是一根会思考的苇草</p>
          <p class="s-text">挖掘更多思想...</p>
          <div v-if="!this.$store.state.logined">
            <v-text-field
              v-model="username"
              label="Username"
              filled
              background-color="#eee"
              style="opacity: 0.8"
              class="username"></v-text-field>

            <v-text-field
              :value="password"
              v-model="password"
              label="Password"
              type="Password"
              filled
              background-color="#eee"
              style="opacity: 0.8"
            ></v-text-field>

            <v-text-field
              :value="re_password"
              v-if="register"
              v-model="re_password"
              label="Re-enter Password"
              type="Password"
              filled
              background-color="#eee"
              style="opacity: 0.8"
            ></v-text-field>
            <v-text-field
              v-if="register"
              v-model="phoneNumber"
              label="Please enter your phone number"
              filled
              background-color="#eee"
              style="opacity: 0.8"
            ></v-text-field>
            <v-row>
              <v-col cols="6">
              <v-text-field
                v-if="register"
                v-model="verificationCode"
                label="Verification Code"
                filled
                background-color="#eee"
                style="width:160px ;height: 60px;opacity: 0.8"
              ></v-text-field>
              </v-col>
              <v-col col="6">
              <v-btn style="height: 60px" v-if="register" v-show="show" v-on:click="getCode" block color="secondary" dark>Get Code</v-btn>
              <v-btn style="height: 60px" v-show="!show" class="count" block color="secondary" dark>{{count}} s</v-btn>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <v-btn v-on:click="login" block color="secondary" dark>Login / Register</v-btn>
                <v-tooltip v-model="password_wrong_show" bottom color="red lighten-2" style="margin-left: 200px">
                  <template v-slot:activator="{ on }" >
                    <v-icon color="gray lighten-1">mdi-shopping_cart</v-icon>
                  </template>
                  <span>{{error_img}}</span>
                </v-tooltip>
              </v-col>
            </v-row>
          </div>
        </form>

      </v-col>
    </v-row>
    <div>
      <vue-particles
        color="#2EC4B6"
        :particleOpacity="0.7"
        :particlesNumber="80"
        shapeType="circle"
        :particleSize="4"
        linesColor="#2EC4B6"
        :linesWidth="1"
        :lineLinked="true"
        :lineOpacity="0.4"
        :linesDistance="150"
        :moveSpeed="3"
        :hoverEffect="true"
        hoverMode="grab"
        :clickEffect="true"
        clickMode="push"
        style="z-index:-1;margin-bottom: -765px"
      >
      </vue-particles>
    </div>
    <SegText :text="hot" class="segtext-hot"/>
    <div style="width: 100%; display:-webkit-box; -webkit-box-pack:center; background-color: white">
      <div class="hot-content">
        <BookComment :card_content="this.$store.state.today_book_recommend_content"/>
        <HistoryComment :card_content="this.$store.state.today_recommend_content"/>
      </div>
    </div>
    <SegText :text="topic" class="segtext-topic"/>
    <div class="comments">
      <v-row
        no-gutters
        justify="start"
      >
        <v-col v-for="(data, index) in topics"
               :key="index"
               style="margin-right: 50px"
        >
          <Comments :avatar_img="avatar_prefix + data.account" :name="data.username" :team_img="avatar_prefix + data.teamId"
                    :team_name="data.teamName" :title="data.title" :content="data.content" :groupId="data.teamId"
                    :num_comment="data.replyNum" :num_like="data.likeNum" :id="data.id"
          />
        </v-col>

      </v-row>
      <div style="height: 100px"></div>

    </div>
  </div>
</template>

<script>
import SegText from '../components/SegText'
import Comments from '../components/Comments'
import HistoryComment from '../components/HistoryComment'
import BookComment from '../components/BookComment'
import BackGroundVideo from '../components/BackGroundVideo'

export default {
  data () {
    return {
      avatar_prefix: 'http://114.115.151.96:8666/ProfilePicture/UserAccount/',
      user_img: '',
      team_img: '',
      show: true,
      count: '',
      timer: null,
      error_img: '',
      password_wrong_show: false,
      info: '',
      bookInfo: '',
      hot: '今日热门',
      topic: '今日话题',
      height: '560px',
      width: '310px',
      register: false,
      success: false,
      topics: [
        {},
        {},
        {},
        {},
        {}
      ]
    }
  },
  components: {
    SegText,
    Comments,
    BookComment,
    HistoryComment,
    BackGroundVideo
  },
  mounted () {
    this.initTodayBookRecommend();
    this.initTodayRecommend();
    this.initComments();
  },
  methods: {
    getCode () {
      const TIME_COUNT = 60
      if (!this.timer) {
        this.axios({
          method: 'post',
          url: 'http://114.115.151.96:8666/send',
          data: {
            phone: this.$store.state.phoneNumber
          },
          crossDomain: true
        }).then(body => {
          this.info = body
          // 错误信息
          if (this.info.data === 'request fail!') {
            var that = this
            this.password_wrong_show = true
            this.error_img = 'request fail!'
            setTimeout(function () {
              that.password_wrong_show = false
            }, 2000)
          }
          else {
            this.$store.dispatch('changeCode', this.info.data)
          }
        })
        this.count = TIME_COUNT;
        this.show = false;
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= TIME_COUNT) {
            this.count--;
          } else {
            this.show = true;
            clearInterval(this.timer);
            this.timer = null;
          }
        }, 1000)
      }
    },
    unshow () {
      this.password_wrong_show = false
    },
    initTodayRecommend(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/film/findAll',
        data: {
        },
        crossDomain: true
      }).then(body => {
        this.info = body
        this.$store.dispatch('getTodayRecommend', this.info.data)
      })
    },
    initTodayBookRecommend(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/search/booktimelist',
        data: {
          pagesCount: 20
        },
        crossDomain: true
      }).then(body => {
        console.log("hello")
        console.log(body)
        this.bookInfo = body
        this.$store.dispatch('getTodayBookRecommend', this.bookInfo.data)
      }).catch(error => {
        console.log(error)
        console.log("book error")
      })
    },
    initComments(){
      this.axios({
        method: 'post',
        url: 'http://114.115.151.96:8666/Posting/GetPostingSortByLike',
        data: {
          num: 6
        },
        crossDomain: true
      }).then(body => {
        this.topics = body.data
        console.log('topics', this.topics)
      })
    },
    login () {
      // 未输入账号
      if (this.$store.state.username === '') {
        this.password_wrong_show = true
        var that = this
        this.error_img = '请输入账号'
        setTimeout(function () {
          that.password_wrong_show = false
          that.$forceUpdate()
        }, 2000)
        this.$store.commit('clear')
      } else {
        // 未处于注册态,进行登录
        if (this.register === false) {
          this.axios({
            method: 'post',
            url: 'http://114.115.151.96:8666/User/Login',
            data: {
              account: this.$store.state.username,
              password: this.$store.state.password
            },
            crossDomain: true
          }).then(body => {
            this.info = body
            // 用户不存在
            if (this.info.data === 0) {
              this.register = true
            }
            // 密码错误
            else if (this.info.data === -1) {
              this.password_wrong_show = true
              var that = this
              this.error_img = '密码错误'
              setTimeout(function () {
                that.password_wrong_show = false
                that.$forceUpdate()
              }, 2000)
              this.$store.commit('clear')
            }
            // 登录成功
            else {
              this.$store.commit('logined')
              this.$store.dispatch('changeAC', this.info.data.user.account)
              this.$store.dispatch('changeInro', this.info.data.user.introduction)
              this.$store.dispatch('changeSelfAvatar', 'http://114.115.151.96:8666/ProfilePicture/UserAccount/' + this.info.data.user.account)
              if ( this.info.data.user.superuser ) {
                this.$store.commit('IsSuper')
                this.$router.push({ path: '/manage' })
              }
              else {
                this.$router.push({ path: '/selfinfo' })
              }
            }
          })
        }
        // 处于注册态
        else {
          // 两次密码输入不相同
          if(this.$store.state.re_password !== this.$store.state.password){
            this.error_img = '两次密码不相同'
            this.password_wrong_show = true
            var that = this
            setTimeout(function () {
              that.password_wrong_show = false
              that.$forceUpdate()
            }, 2000)
            this.$store.commit('clear')
          }
          // 验证码错误
          else if(this.$store.state.verificationCode != this.$store.state.true_verificationCode){
            var that = this
            this.password_wrong_show = true
            this.error_img = 'Wrong Verification Code!'
            setTimeout(function () {
              that.password_wrong_show = false
            }, 2000)
          }
          // 注册
          else{
            this.axios({
              method: 'post',
              url: 'http://114.115.151.96:8666/User/Add',
              data: {
                account: this.$store.state.username,
                password: this.$store.state.password
              },
              crossDomain: true
            }).then(body => {
              this.info = body
              if (this.info.data == 1) {
                console.log('register', this.$store.state.account)
                this.$store.commit('logined')
                this.$store.dispatch('changeAC', this.$store.state.username)
                this.$router.push({ path: '/selfinfo' })
              }
              else {
                this.error_img = '用户已存在'
                this.password_wrong_show = true
                var that = this
                setTimeout(function () {
                  that.password_wrong_show = false
                  that.$forceUpdate()
                }, 2000)
                this.$store.commit('clearall')
              }
            })
          }
        }
      }
    }
  },
  computed: {
    today_hot_content: {
      get () {
        return this.$store.state.today_hot_content
      },
      set (newVal) {
        this.$store.commit('handleTodayHotContent', newVal)
      }
    },
    username: {
      get () {
        return this.$store.state.username
      },
      set (newVal) {
        this.$store.commit('handleUsername', newVal)
      }
    },
    password: {
      get () {
        return this.$store.state.password
      },
      set (newVal) {
        this.$store.commit('handlePassword', newVal)
      }
    },
    re_password: {
      get () {
        return this.$store.state.re_password
      },
      set (newVal) {
        this.$store.commit('handleRePassword', newVal)
      }
    },
    phoneNumber: {
      get () {
        return this.$store.state.phoneNumber
      },
      set (newVal) {
        this.$store.commit('handlePhoneNumber', newVal)
      }
    },
    verificationCode: {
      get () {
        return this.$store.state.verificationCode
      },
      set (newVal) {
        this.$store.commit('handleVerificationCode', newVal)
      }
    }
  }
}
</script>

<style scoped>
  .hot-content{
    width: 1060px;
  }

  .left-content{
  }

  .right-content{
    display:-webkit-box;
    -webkit-box-align:center;/* 垂直居中 */
    -webkit-box-pack:center;/* 水平居中 */
  }

  .logo-left{
    margin-left: -100px;
    width: 125%;
  }

  .b-text{
    width: 396px;
    height: 101px;
    color: rgba(0, 0, 0, 1);
    font-size: 36px;
    font-family: "Microsoft YaHei" ! important;
  }
  .s-text{
    width: 396px;
    height: 101px;
    color: rgba(0, 0, 0, 1);
    font-size: 28px;
    font-family: "Microsoft YaHei" ! important;
    margin-top: -50px;
  }
  .username{
    margin-top: -50px;
  }

  .btn-large{
    width: 200px;
  }

  .segtext-hot{
    position: relative;
    z-index: 2;
  }

  .comments{
    float: left;
    padding: 50px;
  }

  .login-regist-container{
    margin-top: -100vh;
    z-index: 2;
    position: relative;
    background: transparent;
  }
</style>
