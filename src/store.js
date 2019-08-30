import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios)

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userId:"abcd",
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
      pageSize: 5,
      pageNum: 20,
      dataShow: [
        {
          image:"https://cdn.vuetifyjs.com/images/profiles/marcus.jpg",
          title:"I'm the 1 title",
          fstl:"I'm the first line",
          scdl:"I'm the second line",
          sore:-45.6,
        },
        {
          image:"https://cdn.vuetifyjs.com/images/profiles/marcus.jpg",
          title:"I'm the 2 title",
          fstl:"I'm the first line",
          scdl:"I'm the second line",
          sore:20.6,
        },
        {
          image:"https://cdn.vuetifyjs.com/images/profiles/marcus.jpg",
          title:"I'm the 3 title",
          fstl:"I'm the first line",
          scdl:"I'm the second line",
          sore:20.6,
        }],
      currentPage: 6,
      currentSearchTab: 0
    }
  },
  getters: {

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
        account: state.userId
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
      console.log(keyword)
    },
    handleSearch(state){
      console.log(state.search.keyword)
      axios.post('http://114.115.151.96:8666/search/find', {
        name: state.search.keyword
      })
        .then(function (response) {
          state.search.dataShow = response.data
          console.log(response)
          state.datas = response.data
        })
        .catch(function (error) {
          state.datas = { tit: error }
        })
    },,
    changeGroupCreatSucceed(state){
      state.groupCreate.succeed=false
    }
  },
  actions: {
  groupCreate(context,tags){
    var ttags=[]
    for (var i=0;i<tags.length;i++){
      ttags.push(context.state.groupCreate.tags[ttags[i]])
    }
    return new Promise((resolve, reject) =>{
      axios.post('http://114.115.151.96:8666/Team/Add', {
        introduction: context.state.groupCreate.intro,
        teamName: context.state.groupCreate.name,
        account: context.state.userId,
        tags: ttags
      })
        .then(function (response) {
          console.log(response)
          commit(context.state.groupCreate.succeed=true
          resolve("succeed")
        })
        .catch(function (error) {
          context.state.datas = { tit: error }
          state.groupCreate.succeed=false
          reject("error")
        })
    })
  }
  }
})
