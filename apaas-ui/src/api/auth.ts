import request from '@/utils/request'
import { ResponseData } from '@/utils/request'

// 登录请求数据类型
export interface LoginData {
  username: string
  password: string
}

// 用户信息类型
export interface UserInfo {
  id: number
  username: string
  name: string
  avatar: string
  roles: string[]
  permissions: string[]
}

// 登录响应数据类型
export interface LoginResponseData {
  token: string
  user: UserInfo
}

// 登录
export function login(data: LoginData): Promise<ResponseData<LoginResponseData>> {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

// 获取用户信息
export function getUserInfo(): Promise<ResponseData<UserInfo>> {
  return request({
    url: '/auth/user/info',
    method: 'get'
  })
}

// 登出
export function logout(): Promise<ResponseData<null>> {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

// 获取权限菜单
export function getPermissions(): Promise<ResponseData<string[]>> {
  return request({
    url: '/auth/permissions',
    method: 'get'
  })
}