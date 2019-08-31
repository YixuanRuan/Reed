<template>
  <div style="width: 90%; margin-left: 5%; margin-right: 5%; margin-top: 50px;">
    <v-row justify="center" align="center">
      <v-col cols="8">
      <v-tabs
          v-model="tab"
          background-color="white"
          color="grey"
      >
        <p class=" SearchMenuTitle">搜索结果</p>
        <v-tab v-for="i in tabs" :key="i" :href="`#tab-${i}`" @click="cli(i)">
            {{ tabNames[i-1] }}
        </v-tab>
        <v-tabs-slider></v-tabs-slider>
      </v-tabs>
      </v-col>

      <v-col cols="4">
      </v-col>
    </v-row>

    <v-tabs-items v-model="tab">
      <v-tab-item
        :key="1"
        value='tab-1'
        class="padd"
      >
        <SearchBar
          v-for="bar in $store.state.search.dataShow['book'][page-1]"
          :key="bar"
          :image="$store.state.search.bookImgPath+bar['id']"
          :type="'book'"
          :id="bar['id']"
          :title="bar['bookName']"
          :fstl="'作者：'+bar['author']+' ISBN：'+bar['isbn']"
          :scdl="'介绍：'+bar['intro']"
        ></SearchBar>
      </v-tab-item>
      <v-tab-item
        :key='2'
        value='tab-2'
        class="padd"
      >
        <SearchBar
          v-for="bar in $store.state.search.dataShow['film'][page-1]"
          :key="bar"
          :image="$store.state.search.filmImgPath+bar['id']"
          :type="'film'"
          :id="bar['id']"
          :title="bar['filmName']"
          :fstl="'导演：'+bar['director']+' 国家：'+bar['country']+' 语言：'+bar['language']+' 主演：'+bar['starring']"
          :scdl="bar['intro']"
        ></SearchBar>
      </v-tab-item>
      <v-tab-item
        :key='3'
        value='tab-3'
        class="padd"
      >
        <SearchBar
          v-for="bar in $store.state.search.dataShow['team'][page-1]"
          :key="bar"
          :image="$store.state.search.groupImgPath+bar['id']"
          :type="'team'"
          :id="bar['id']"
          :title="bar['teamName']"
          :fstl="bar['tags']"
          :scdl="bar['introduction']"
        ></SearchBar>
      </v-tab-item>
    </v-tabs-items>
    <v-pagination
      v-model='page'
      :disabled='disabled'
      :length='$store.state.search.pageNum[$store.state.search.field[$store.state.search.tab-1]]'
      :next-icon='nextIcon'
      :prev-icon='prevIcon'
      :page= "page"
      :total-visible='totalVisible'
      color='#ccc'
    ></v-pagination>
  </div>
</template>

<script>
import SearchBar from './SearchBar'
export default {
  name: 'SearchMenu',
  components: {
    SearchBar
  },
  data () {
    return {
      tab: null,
      tabs: 3,
      tabNames: ['书籍', '影视', '小组'],
      pageNum: 10,
      page: 1,
      circle: false,
      disabled: false,
      nextIcon: 'mdi-arrow-right',
      prevIcon: 'mdi-arrow-left',
      totalVisible: 10
    }
  },
  mounted () {
    this.$store.commit('handleTab', 0)
  },
  methods: {
    cli: function (i) {
      this.page=1
      this.$store.commit('handleTab', i)
    }
  },

}
</script>

<style scoped>
.SearchMenuTitle{
    width:130px;
    padding-top: 10px;
    padding-left: 28px;
    font-size: 20px;
}
.padd{
  padding-bottom: 20px;
}
</style>
