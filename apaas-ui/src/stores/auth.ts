import { defineStore } from 'pinia'

// 用户信息类型定义
export interface UserInfo {
  id: number
  username: string
  name: string
  avatar: string
  roles: string[]
  permissions: string[]
}

// 权限类型定义
export type Permission = string

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: null as UserInfo | null,
    permissions: [] as Permission[]
  }),
  
  getters: {
    isAuthenticated: (state) => !!state.token,
    hasPermission: (state) => (permission: Permission) => {
      return state.permissions.includes(permission)
    }
  },
  
  actions: {
    setToken(token: string) {
      this.token = token
      localStorage.setItem('token', token)
    },
    
    setUserInfo(userInfo: UserInfo) {
      this.userInfo = userInfo
    },
    
    setPermissions(permissions: Permission[]) {
      this.permissions = permissions
    },
    
    logout() {
      this.token = ''
      this.userInfo = null
      this.permissions = []
      localStorage.removeItem('token')
    }
  }
})