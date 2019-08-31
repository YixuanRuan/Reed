import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import Progress from 'vue-multiple-progress'
import '@mdi/font/css/materialdesignicons.css'
import Vuetify from 'vuetify/lib'
import axios from 'axios'
import VueAxios from 'vue-axios'
import VueDraggable from 'vue-draggable'
import echarts from 'echarts'
import captcha from 'vue-social-captcha'
import scroll from 'vue-seamless-scroll'
import VueParticles from 'vue-particles'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// 导入极验
require('@/assets/gt.js');
// 绑定到原型
Vue.prototype.$initGeet=initGeetest;

Vue.use(mavonEditor)
Vue.use(VueParticles)
Vue.prototype.$echarts = echarts
require('echarts-wordcloud')
Vue.use(VueAxios, axios)
Vue.config.productionTip = false
Vue.use(Progress)
Vue.use(VueDraggable)

Vue.use(scroll)
Vue.use(captcha)
Vue.use(VueAxios, axios)
Vue.config.productionTip = false
Vue.use(Progress)
Vue.use(VueDraggable)

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')

export default new Vuetify({
  icons: {
    iconfont: 'mdiSvg' // default - only for display purposes
  }
})
