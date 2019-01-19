import Vue from 'vue'
import Router from 'vue-router'
import Trending from '@/page/trending/Trending'
import TabTrending from '@/page/trending/TabTrending'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'TabTrending',
      component: TabTrending
    },
    // {
    //   path: '/tab',
    //   name: 'TabTrending',
    //   component: TabTrending
    // }
  ]
})
