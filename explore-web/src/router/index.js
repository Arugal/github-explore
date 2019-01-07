import Vue from 'vue'
import Router from 'vue-router'
import Trending from '@/page/trending/Trending'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Trending',
      component: Trending
    }
  ]
})
