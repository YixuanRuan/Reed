import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios)
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    joined: false,
    selfAvatar: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2579313585,1854004294&fm=26&gp=0.jpg',
    currentId: '',
    currentBookId: '',
    username: '',
    password: '',
    re_password: '',
    phoneNumber: '',
    verificationCode: '',
    true_verificationCode: 'root',
    logined: '',
    error_img: '',
    isSuper: false,
    account: '',
    introduction: '',
    selfComments: [],
    searchBookId: '5d61fe1359f0004904de51b6',
    groupStatus: {
      joinedNum: 1,
      myGroups:[{
        id:"5d65f9b259f000353446afbb",
        teamName:"我是打小天才babababbcwe"
      },{
        id:"5d65f9b259f000353446afbb",
        teamName:"我是小天才babababbcwe"
      },{
        id:"5d65f9b259f000353446afbb",
        teamName:"我是小天才babababbcwe"
      },{
        id:"5d65f9b259f000353446afbb",
        teamName:"我是小天才babababbcwe"
      }]
    },
    datas: {
      hello: 'hiiiit',
    },
    group: {
      groupId: {
        dafalut: "1"
      },
      isMygroup: {
        default: false
      },
      groupName: {
        default: "诗与远方"
      },
      groupMotto: {
        default: "交流着诗意的人生"
      },
      imgSrc: "http://114.115.151.96:8666/ProfilePicture/UserAccount/"
    },
    groupFind: {
      tabsNum: 6,
      tabNames: ['兴趣', '情感', '电影', '文学', '学术', '生活'],
      tabEngNames: ['Hobby', 'Emotion', 'Movie', 'Literature', 'Academic', 'Life'],
      num: 0,
      data: []
    },
    groupCreate: {
      name: "",
      intro: "",
      tags: ['Hobby', 'Emotion', 'Movie', 'Literature', 'Academic', 'Life'],
      tagsChecked: [ false, true, false, false, false, false],
      succeed: false
    },
    myGroup: {
      pageSize: 10,
      pageNum: 1,
      comments:[]
    },
    search: {
      keyword: '11',
      tab: 1,
      totalLen: 100,
      pageSize: 3,
      pageNum: {'book':1,'film':1,'team':1},
      bookImgPath: "http://114.115.151.96:8666/ProfilePicture/UserAccount/",
      filmImgPath: "http://114.115.151.96:8666/ProfilePicture/UserAccount/",
      groupImgPath: "http://114.115.151.96:8666/ProfilePicture/UserAccount/",
      field:['book','film','team'],
      dataShow: {'book':[],'film':[],'team':[]},
      currentPage: 6,
      currentSearchTab: 0
    },
    massage_content: [
      {
        massage_title: '消息通知',
        massage_content: '您在诗与远方发表的评论得到回复，点击查看 >'
      },
      {
        massage_title: '消息通知',
        massage_content: '您在诗与远方发表的评论得到回复，点击查看 >'
      },
      {
        massage_title: '消息通知',
        massage_content: '您在诗与远方发表的评论得到回复，点击查看 >'
      },
      {
        massage_title: '消息通知',
        massage_content: '您在诗与远方发表的评论得到回复，点击查看 >'
      }

    ],

    // 今日推荐
    today_recommend_content: [],

    today_book_recommend_content: [],
    // 今日最新
    today_new_content: [
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: 'n1',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      },
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: 'n2',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      },
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: 'n3',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      },
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: 'n4',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      },
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: 'n5',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      },
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: 'n6',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      },
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: 'n7',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      }
    ],

    // 今日热点
    today_hot_content: [
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: '1',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      },
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: '2',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      },
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: '3',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      },
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: '4',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      },
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: '1',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      },
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: '5',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      },
      {
        poster_img: 'https://m.media-amazon.com/images/M/MV5BMTU0OTc3ODk4Ml5BMl5BanBnXkFtZTgwMzM4NzI5NjM@._V1_UX182_CR0,0,182,268_AL_.jpg',
        name: '6',
        score: 37.6,
        director: '阿方索·卡隆',
        Starring: '雅利扎·阿巴里西奥',
        duration: 135,
        brief: '故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？故事发生在墨西哥城的一个中产阶级社区“罗马”，讲述年轻女佣克里奥突如其来的两个意外，同时砸中了女佣克里奥和雇主索菲亚，两人究竟该如何面对苦涩茫然的生活？',
        looked: '1024'
      }
    ],
    collection: [
      {
        id: "5d65eeccd4a370186cdac7d4",
        filmName: "你好，吸血鬼小姐1",
        score: "37.6",
        account: "abcd"
      },
      // {
      //   id: "5d65eeccd4a370186cdac7d3",
      //   filmName: "九五二班",
      //   score: "37.6",
      //   account: "abcd"
      // },
      // {
      //   id: "5d65eeccd4a370186cdac7d2",
      //   filmName: "拜见女仆大人",
      //   score: "37.6",
      //   account: "abcd"
      // },
      // {
      //   id: "5d65eeccd4a370186cdac7d1",
      //   filmName: "心跳 Hart Beat",
      //   score: "37.6",
      //   account: "abcd"
      // }
    ]
  },
  methods: {
    getSearchResult () {
      axios.post('http://114.115.151.96:8666/book/find', {
        keyword: this.state.search.keyword,
        tab: this.state.search.tab,
        page: this.state.search.currentPage
      })
        .then(function (response) {
          this.state.search.dataShow = response
        })
        .catch(function (error) {
          this.state.search.dataShow = [ { tit: error } ]
        })
    }
  },
  mutations: {
    handleIsSuper (state, newVal) {
      state.isSuper = newVal
    },
    handleCurrentPage (state, newVal) {
      this.state.search.currentPage = newVal
      this.methods.getSearchResult()
    },
    handleTab (state, newVal) {
      this.state.search.tab = newVal
      this.methods.getSearchResult()
    },
    getBook (state) {
      axios.post('http://114.115.151.96:8666/book/find', {
        id: state.searchBookId
      })
        .then(function (response) {
          state.datas = response
        })
        .catch(function (error) {
          state.datas = { tit: error }
        })
    },
    handleGroupFind (state, i) {
      var url ='http://114.115.151.96:8666/Team/Find'+state.groupFind.tabEngNames[i]
      axios.post(url, { })
        .then(function (response) {
          state.groupFind.num = response.data.length
          console.log(state.groupFind.num)
          state.groupFind.data = response.data
        })
        .catch(function (error) {
          state.datas = { tit: error }
        })
    },
    handleMyGroup(state){
      axios.post('http://114.115.151.96:8666/MyGroupNotification', {
        account: state.account
      })
        .then(function (response) {
          state.myGroup.pageNum = Math.ceil(response.data.length/state.myGroup.pageSize)
          var lenn = 0
          var i = 0
          for (i = 0;i < state.myGroup.pageNum-1; i++){
            lenn = i * state.myGroup.pageSize
            state.myGroup.comments.push(response.data.slice(lenn,lenn+state.myGroup.pageSize))
          }
          lenn = i*state.myGroup.pageSize
          state.myGroup.comments.push(response.data.slice(lenn,response.data.length))
        })
        .catch(function (error) {
          state.datas = { tit: error }
        })
      console.log(2)
    },
    changeGroupCreateName(state,newVal){
      state.groupCreate.name = newVal
    },
    changeGroupCreateIntro(state,newVal){
      state.groupCreate.intro = newVal
    },
    changeGroup(state, groupId){
      state.group.groupId = groupId
      console.log(groupId)
      axios.post('http://114.115.151.96:8666/Team/FindOne', {
        id: groupId
      })
        .then(function (response) {
          console.log(response.data)
          state.group.groupName = response.data.teamName
          state.group.groupMotto = response.data.introduction
        })
        .catch(function (error) {
          state.datas = { tit: error }
        })
    },
    changeKeyword(state, keyword){
      state.search.keyword=keyword
    },
    handleSearch(state){
      axios.post('http://114.115.151.96:8666/search/find', {
        name: state.search.keyword
      })
        .then(function (response) {
          console.log("entered")
          state.search.dataShow= {'book':[],'film':[],'team':[]}
          for (let x of state.search.field){
            var lenn = 0
            var i = 0
            console.log(response.data[x])
            state.search.pageNum[x] = Math.ceil(response.data[x].length/state.search.pageSize)
            for (i = 0;i < state.search.pageNum[x]-1; i++){
              lenn = i * state.search.pageSize
              state.search.dataShow[x].push(response.data[x].slice(lenn,lenn+state.search.pageSize))
            }
            lenn = i*state.search.pageSize
            state.search.dataShow[x].push(response.data[x].slice(lenn,response.data[x].length))
          }
          console.log(state.search.dataShow['book'][0])
          state.datas = response.data
        })
        .catch(function (error) {
          state.datas = { tit: error }
        })
    },
    groupCreate(state,tags){
      var ttags=[]
      for (var i=0;i<tags.length;i++){
        ttags.push(state.groupCreate.tags[ttags[i]])
      }
      axios.post('http://114.115.151.96:8666/Team/Add', {
        introduction: state.groupCreate.intro,
        teamName: state.groupCreate.name,
        account: state.account,
        tags: ttags
      })
        .then(function (response) {
          console.log(response)
          state.groupCreate.succeed=true
        })
        .catch(function (error) {
          state.datas = { tit: error }
          state.groupCreate.succeed=false
        })
    },
    changeGroupCreatSucceed(state){
      state.groupCreate.succeed=false
    },
    handleCurrentId (state, newVal) {
      state.currentId = newVal
    },
    handleCurrentBookId (state, newVal) {
      state.currentBookId = newVal
    },
    handleSelfComments (state, newVal) {
      state.selfComments = newVal
    },
    handleTodayNewContent (state, newVal) {
      state.today_new_content = newVal
    },
    handleTodayRecommendContent (state, newVal) {
      state.today_recommend_content = newVal
    },
    handleTodayHotContent (state, newVal) {
      state.today_hot_content = newVal
    },
    handleCollection (state, newVal) {
      state.collection = newVal
      for (i = 0; i < state.collection.length; i++) {
        state.collection[i].account = state.account
      }
    },
    handleMassageContent (state, newVal) {
      state.massage_content = newVal
    },
    handleUsername (state, newVal) {
      state.username = newVal
    },
    handlePassword (state, newVal) {
      state.password = newVal
    },
    handleRePassword (state, newVal) {
      state.re_password = newVal
    },
    handlePhoneNumber (state, newVal) {
      state.phoneNumber = newVal
    },
    handleVerificationCode (state, newVal) {
      state.verificationCode = newVal
    },
    handleError (state, newVal) {
      state.error_img = newVal
    },
    clearall () {
      this.state.password = ''
      this.state.re_password = ''
      this.state.username = ''
    },
    clear () {
      this.state.password = ''
      this.state.re_password = ''
    },
    logined () {
      this.state.logined = true
    },
    changeAccount (state, newAccount) {
      state.account = newAccount
    },
    changeIntroduction (state, newIntroduction) {
      state.introduction = newIntroduction
    },
    deleteItem (state, index) {
      state.collection.splice(index, 1)
    },
    deleteMassage (state, index) {
      state.massage_content.splice(index, 1)
    },
    changeAvatar (state, newAvatar) {
      state.selfAvatar = newAvatar
    },
    changeTodayRecommendContent (state, newList) {
      state.today_recommend_content = newList
    },
    changeTodayBookRecommendContent (state, newList) {
      state.today_book_recommend_content = newList
    },
    changeSelfComments (state, newList) {
      state.selfComments = newList
    },
    changeCurrentId (state, newVal) {
      state.currentId = newVal
    },
    changeJoinState (state) {
      state.joined = !state.joined;
    },
    changetrueCode (state, newVal) {
      state.true_verificationCode = newVal
    },
    changeMassage(state, newList) {
      state.massage_content = newList
    },
    changeIsSuper(state) {
      state.isSuper = true
    }
  },
  actions: {
    changeSuper(context){
      context.commit('changeIsSuper')
    },
    changeMassageData(context, newList) {
      context.commit('changeMassage', newList)
    },
    changeCurrentBMId (context, newVal) {
      context.commit('changeCurrentId', newVal)
    },
    getTodayRecommend (context, newList) {
      context.commit('changeTodayRecommendContent', newList)
    },
    getTodayBookRecommend (context, newList) {
      context.commit('changeTodayBookRecommendContent', newList)
    },
    changeSelfAvatar (context, newAvatar) {
      context.commit('changeAvatar', newAvatar)
    },
    changeSComments (context, newSelfComments) {
      context.commit('changeSelfComments', newSelfComments)
    },
    changeAC (context, newAccount) {
      context.commit('changeAccount', newAccount)
    },
    changeInro (context, newIntroduction) {
      context.commit('changeIntroduction', newIntroduction)
    },
    deleteLikeItem (context, index) {
      context.commit('deleteItem', index)
    },
    deleteMassageItem (context, index) {
      context.commit('deleteMassage', index)
    },
    changeCode (context, trueCode) {
      context.commit('changetrueCode', trueCode)
    }
  }
})
