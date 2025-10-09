import { type RouteRecordRaw } from 'vue-router'
import LoginPage from '@/views/login/index.vue'
import Layout from '@/layout/index.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: LoginPage,
    meta: {
      hidden: true
    }
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: {
          title: '首页',
          icon: 'el-icon-s-home'
        }
      },
      // 流程引擎路由
      {
        path: '/flow',
        name: 'flow',
        meta: {
          title: '流程管理',
          icon: 'el-icon-s-order'
        },
        children: [
          {
            path: 'design',
            name: 'flow-design',
            component: () => import('@/views/flow/definition/index.vue'),
            meta: {
              title: '流程设计'
            }
          },
          {
            path: 'execution',
            name: 'flow-execution',
            component: () => import('@/views/flow/instance/index.vue'),
            meta: {
              title: '流程执行'
            }
          },
          {
            path: 'monitoring',
            name: 'flow-monitoring',
            component: () => import('@/views/flow/task/index.vue'),
            meta: {
              title: '流程监控'
            }
          }
        ]
      },
      // 系统管理路由
      {
        path: '/system',
        name: 'system',
        meta: {
          title: '系统管理',
          icon: 'el-icon-s-tools'
        },
        children: [
          {
            path: 'user',
            name: 'system-user',
            component: () => import('@/views/system/user/index.vue'),
            meta: {
              title: '用户管理'
            }
          },
          {
            path: 'role',
            name: 'system-role',
            component: () => import('@/views/system/role/index.vue'),
            meta: {
              title: '角色管理'
            }
          },
          {
            path: 'menu',
            name: 'system-menu',
            component: () => import('@/views/system/menu/index.vue'),
            meta: {
              title: '菜单管理'
            }
          },
          {
            path: 'post',
            name: 'system-post',
            component: () => import('@/views/system/post/index.vue'),
            meta: {
              title: '岗位管理'
            }
          },
          {
            path: 'notice',
            name: 'system-notice',
            component: () => import('@/views/system/notice/index.vue'),
            meta: {
              title: '通知公告'
            }
          },
          // 系统监控路由整合到系统管理下
          {
            path: 'audit',
            name: 'system-audit',
            component: () => import('@/views/system/log/login/index.vue'),
            meta: {
              title: '登录日志'
            }
          },
          {
            path: 'log',
            name: 'system-log',
            component: () => import('@/views/system/log/operation/index.vue'),
            meta: {
              title: '操作日志'
            }
          }
        ]
      }
    ]
  },
  // 404页面
  {
    path: '/:pathMatch(.*)*',
    name: 'not-found',
    component: () => import('@/views/error/404.vue'),
    meta: {
      hidden: true
    }
  }
]

export default routes