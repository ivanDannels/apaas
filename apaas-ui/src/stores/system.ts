import { defineStore } from 'pinia'

// 租户类型定义
export interface Tenant {
  id: number
  name: string
  code: string
  status: number
  createTime: string
  updateTime: string
}

// 用户类型定义
export interface User {
  id: number
  username: string
  name: string
  avatar: string
  email: string
  phone: string
  status: number
  tenantId: number
  roleId: number
  createTime: string
  updateTime: string
}

// 角色类型定义
export interface Role {
  id: number
  name: string
  code: string
  status: number
  createTime: string
  updateTime: string
}

// 数据字典类型定义
export interface Dictionary {
  id: number
  name: string
  code: string
  status: number
  createTime: string
  updateTime: string
}

export const useSystemStore = defineStore('system', {
  state: () => ({
    // 租户相关
    tenants: [] as Tenant[],
    currentTenant: null as Tenant | null,
    
    // 用户相关
    users: [] as User[],
    currentUser: null as User | null,
    
    // 角色相关
    roles: [] as Role[],
    currentRole: null as Role | null,
    
    // 数据字典相关
    dictionaries: [] as Dictionary[],
    currentDictionary: null as Dictionary | null
  }),
  
  actions: {
    // 租户相关操作
    setTenants(tenants: Tenant[]) {
      this.tenants = tenants
    },
    
    setCurrentTenant(tenant: Tenant) {
      this.currentTenant = tenant
    },
    
    // 用户相关操作
    setUsers(users: User[]) {
      this.users = users
    },
    
    setCurrentUser(user: User) {
      this.currentUser = user
    },
    
    // 角色相关操作
    setRoles(roles: Role[]) {
      this.roles = roles
    },
    
    setCurrentRole(role: Role) {
      this.currentRole = role
    },
    
    // 数据字典相关操作
    setDictionaries(dictionaries: Dictionary[]) {
      this.dictionaries = dictionaries
    },
    
    setCurrentDictionary(dictionary: Dictionary) {
      this.currentDictionary = dictionary
    },
    
    // 重置状态
    reset() {
      this.tenants = []
      this.currentTenant = null
      this.users = []
      this.currentUser = null
      this.roles = []
      this.currentRole = null
      this.dictionaries = []
      this.currentDictionary = null
    }
  }
})