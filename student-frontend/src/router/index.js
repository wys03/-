import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../components/Layout.vue'

const routes = [
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('../views/Dashboard.vue'),
        meta: { title: '首页', icon: 'HomeFilled' }
      },
      {
        path: '/students',
        name: 'StudentList',
        component: () => import('../views/StudentList.vue'),
        meta: { title: '学生管理', icon: 'User' }
      },
      {
        path: '/courses',
        name: 'CourseList',
        component: () => import('../views/CourseList.vue'),
        meta: { title: '课程管理', icon: 'Reading' }
      },
      {
        path: '/scores',
        name: 'ScoreList',
        component: () => import('../views/ScoreList.vue'),
        meta: { title: '成绩管理', icon: 'Document' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
