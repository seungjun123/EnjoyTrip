import { createRouter, createWebHistory } from 'vue-router'
import MainPage from '@/components/MainPage.vue'

import CardDetail from '@/components/attraction/CardDetail.vue'

import Plan from '@/components/plan/Plan.vue'
import PlanDetail from '@/components/plan/PlanDetail.vue'

import FindCardDetail from '@/components/findDH/findDHCardDetail.vue'
import ChatRoom from '@/components/findDH/ChatRoom.vue'

import BoardDetailPage from '@/components/board/modal/BoardDetailModal.vue'
import BoardUpdatePage from '@/components/board/modal/BoardUpdateModal.vue'

import AttractionView from '@/views/AttractionView.vue'
import BoardViewPage from '@/views/BoardView.vue'
import MyPageView from '@/views/MyPageView.vue'
import FavoriteView from '@/views/FavoriteView.vue'
import FinddhView from '@/views/FinddhView.vue'
import MyPageChat from '@/views/MyPageChat.vue'
import MyPostView from '@/views/MyPostView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: MainPage
    },
    {
      path: '/board',
      name: 'board',
      component: BoardViewPage
    },
    {
      path: '/attraction',
      component: AttractionView
    },
    {
      path: '/cardDetail',
      name: 'cardDetail',
      component: CardDetail
    },
    {
      path: '/myPage',
      name: 'myPage',
      component: MyPageView
    },
    {
      path: '/plan',
      name: 'plan',
      component: Plan
    },
    {
      path: '/planDetail',
      name: 'planDetail',
      component: PlanDetail
    },
    {
      path: '/boardDetail',
      name: 'boardDetail',
      component: BoardDetailPage
    },
    {
      path: '/boardUpdate',
      name: 'boardUpdate',
      component: BoardUpdatePage
    },
    {
      path: '/favorite',
      name: 'favorite',
      component: FavoriteView
    },
    {
      path: '/finddh',
      name: 'finddh',
      component: FinddhView
    },
    {
      path: '/findDetail',
      name: 'findDetail',
      component: FindCardDetail
    },
    {
      path: '/chatting',
      name: 'chatting',
      component: ChatRoom
    },
    {
      path: '/mypagechat',
      name: 'mypagechat',
      component: MyPageChat
    },
    {
      path: '/mypost',
      name: 'mypost',
      component: MyPostView
    }
  ]
})

export default router
