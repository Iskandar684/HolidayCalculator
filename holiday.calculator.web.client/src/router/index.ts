import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/my-statements',
    name: 'my-statements',
    component: () => import('../views/CurrentUserStatements.vue')
  },
  {
    path: '/statement/:uuid',
    name: 'statement',
    component: () => import('../components/StatementDocument.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
