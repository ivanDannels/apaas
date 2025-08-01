// 登录表单类型
export interface LoginForm {
  username: string
  password: string
}

// 用户信息类型
export interface UserInfo {
  id: number
  username: string
  name: string
}

// 状态类型
export interface AppState {
  sidebar: {
    opened: boolean
    withoutAnimation: boolean
  }
  device: string
  size: string
}

export interface UserState {
  token: string
  userInfo: UserInfo | null
}

export interface TagsViewState {
  visitedViews: any[]
  cachedViews: any[]
}

export interface PermissionState {
  routes: any[]
  addRoutes: any[]
}