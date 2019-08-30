import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'

Vue.use(Router)

const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
      // path: '/',
      // name: 'bmhome',
      // component: BMHome
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
    },
    {
      path: '/forum',
      name: 'forum',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "forum" */ './views/Forum.vue')
    },
    {
      path: '/selfinfo',
      name: 'selfinfo',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "selfinfo" */ './views/SelfInfo.vue')
    },
    {
      path: '/bmhome',
      name: 'bmhome',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "bmhome" */ './views/BMHome.vue')
    },
    {
      path: '/group',
      name: 'group',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "bmhome" */ './views/Group.vue')
    },
    {
      path: '/bmdisplay',
      name: 'bmdisplay',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "bmhome" */ './views/BMDisplay.vue')
    },
    {
      path: '/search',
      name: 'search',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "bmhome" */ './views/Search.vue')
    },
    {
      path: '/groupFind',
      name: 'groupFind',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "bmhome" */ './views/GroupFind.vue')
    },
    {
      path: '/myGroup',
      name: 'myGroup',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "bmhome" */ './views/MyGroup.vue')
    },
    {
      path: '/groupCreate',
      name: 'groupCreate',
      component: () => import('./views/GroupCreate.vue')
    }
  ]
})
