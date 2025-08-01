import { RouteRecordRaw } from 'vue-router'
import LoginPage from '@/views/login/LoginPage.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: LoginPage
  },
  {
    path: '/',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: () => import('@/views/dashboard/index.vue')
  },
  // 流程引擎路由
  {
    path: '/flow',
    name: 'flow',
    component: () => import('@/views/flow/index.vue'),
    children: [
      {
        path: 'design',
        name: 'flow-design',
        component: () => import('@/views/flow/design/index.vue')
      },
      {
        path: 'execution',
        name: 'flow-execution',
        component: () => import('@/views/flow/execution/index.vue')
      },
      {
        path: 'monitoring',
        name: 'flow-monitoring',
        component: () => import('@/views/flow/monitoring/index.vue')
      }
    ]
  },
  // 系统管理路由
  {
    path: '/system',
    name: 'system',
    component: () => import('@/views/system/index.vue'),
    children: [
      {
        path: 'tenant',
        name: 'system-tenant',
        component: () => import('@/views/system/tenant/index.vue')
      },
      {
        path: 'user',
        name: 'system-user',
        component: () => import('@/views/system/user/index.vue')
      },
      {
        path: 'role',
        name: 'system-role',
        component: () => import('@/views/system/role/index.vue')
      },
      {
        path: 'dict',
        name: 'system-dict',
        component: () => import('@/views/system/dict/index.vue')
      }
    ]
  },
  // 系统监控路由
  {
    path: '/monitor',
    name: 'monitor',
    component: () => import('@/views/monitor/index.vue'),
    children: [
      {
        path: 'audit',
        name: 'monitor-audit',
        component: () => import('@/views/monitor/audit/index.vue')
      },
      {
        path: 'log',
        name: 'monitor-log',
        component: () => import('@/views/monitor/log/index.vue')
      },
      {
        path: 'dashboard',
        name: 'monitor-dashboard',
        component: () => import('@/views/monitor/dashboard/index.vue')
      }
    ]
  },
  // 404页面
  {
    path: '/:pathMatch(.*)*',
    name: 'not-found',
    component: () => import('@/views/error/404.vue')
  }
]

export default routes