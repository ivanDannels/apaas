import { defineStore } from 'pinia'

// 路由类型定义
export interface RouteItem {
  path: string
  name: string
  component?: any
  meta?: {
    title: string
    icon?: string
    hidden?: boolean
  }
  children?: RouteItem[]
}

// 菜单类型定义
export interface MenuItem {
  id: number
  name: string
  path: string
  icon?: string
  children?: MenuItem[]
}

export const usePermissionStore = defineStore('permission', {
  state: () => ({
    routes: [] as RouteItem[],
    menus: [] as MenuItem[]
  }),
  
  actions: {
    setRoutes(routes: RouteItem[]) {
      this.routes = routes
    },
    
    setMenus(menus: MenuItem[]) {
      this.menus = menus
    },
    
    reset() {
      this.routes = []
      this.menus = []
    }
  }
})