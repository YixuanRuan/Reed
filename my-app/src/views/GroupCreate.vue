<template>
  <div>
    <BackGroundVideo></BackGroundVideo>
    <div class="main-part">
      <div class="resultAlert" v-if="succeed">
        <v-row>
          <v-col cols="5"></v-col>
          <v-col cols="2">
            <v-card style="margin-top: 300px;">
              <v-card-title style="color: white; background-color: #31b0d5;
         text-align: center;">成功创建小组</v-card-title>
            </v-card>
          </v-col>
          <v-col cols="5"></v-col>
        </v-row>
      </div>
      <v-row no-gutters :class="{'blur':succeed}" style="background: rgba(232,232,232,0.7);padding-bottom: 40px;padding-top: 60px;">
        <v-col cols="3"></v-col>
        <v-col cols="6">
          <v-form
            ref="form"
            v-model="valid"
            :lazy-validation="lazy"
          >
            <p>创建小组</p>
            <v-text-field
              v-model="name"
              :counter="10"
              :rules="nameRules"
              label="小组名称"
              required
            ></v-text-field>

            <v-text-field
              v-model="intro"
              :rules="introRules"
              label="小组介绍"
              :counter="50"
              required
            ></v-text-field>

            <v-row>
              <v-col v-for="i in $store.state.groupFind.tabsNum" :key="i" cols="2">
                <v-checkbox
                  v-model="tagsChecked[i-1]"
                  :label="$store.state.groupFind.tabNames[i-1]"
                  required
                ></v-checkbox>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2"/>
              <v-col cols="4" >
                <v-btn
                  color="error"
                  @click="reset"
                  style="width: 100%"
                >
                  重置
                </v-btn>
              </v-col>
              <v-col cols="4">
                <v-btn
                  color="green"
                  @click="submit(tagsChecked)"
                  style="width: 100%; color: white;"
                >
                  提交
                </v-btn>
              </v-col>
              <v-col cols="2"/>
            </v-row>
          </v-form>
        </v-col>
        <v-col cols="3"></v-col>
      </v-row>
    </div>
  </div>
</template>

<script>
import BackGroundVideo from '../components/BackGroundVideo'
export default {
  components: {
    BackGroundVideo
  },
  name: 'GroupCreate',
  data: () => ({
    succeed: false,
    valid: true,
    nameRules: [
      v => !!v || '小组名称是必须的',
      v => (v && v.length <= 10) || '小组名称必须小于10个字哦~'
    ],
    introRules: [
      v => !!v || '小组介绍是必须的',
      v => (v && v.length < 50) || '小组介绍必须小于50个字哦~'
    ],
    lazy: false,
    tagsChecked: [ false, false, false, false, false, false]
  }),
  computed: {
    intro: {
      get () {
        return this.$store.state.groupCreate.intro
      },
      set (newVal) {
        this.$store.commit('changeGroupCreateIntro', newVal)
      }
    },
    name: {
      get () {
        return this.$store.state.groupCreate.name
      },
      set (newVal) {
        this.$store.commit('changeGroupCreateName', newVal)
      }
    }
  },
  methods: {
    reset () {
        this.$refs.form.reset()
    },
    submit (tagsChecked) {
        var tags = []
      for (var i = 0; i < this.tagsChecked.length; i++) {
        if (this.tagsChecked[i]){
            tags.push(i)
        }
      }
      console.log(tags)
      this.$store.commit('groupCreate', tags)
      this.succeed = true
      var that = this
      setTimeout(function () {
        that.$router.push('/myGroup')
      }, 2000)
    }
  }
}
</script>

<style>
  .resultAlert{
    position: fixed;
    margin-top: -100px;
    width: 100%;
    height: 1000px;
  }
  .blur{
    filter: blur(20px);
  }
  .main-part{
    margin-top: -80vh;
    z-index: 2;
    position: relative;
    background: transparent;
    height: 80vh;
  }

  .card-option{
    margin-top: 3px;
    font-size: 13px;
    color: #646464;
    text-align: center;
    height: 20px;
  }
</style>
