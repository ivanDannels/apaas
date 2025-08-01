import request from '@/utils/request'
import { ResponseData } from '@/utils/request'

// 租户相关类型定义
export interface Tenant {
  id: number
  name: string
  code: string
  status: number
  createTime: string
  updateTime: string
}

export interface TenantQuery {
  name?: string
  code?: string
  status?: number
  page: number
  size: number
}

export interface TenantForm {
  id?: number
  name: string
  code: string
  status: number
}

// 用户相关类型定义
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

export interface UserQuery {
  username?: string
  name?: string
  status?: number
  tenantId?: number
  page: number
  size: number
}

export interface UserForm {
  id?: number
  username: string
  name: string
  email: string
  phone: string
  status: number
  tenantId: number
  roleId: number
  password?: string
}

// 角色相关类型定义
export interface Role {
  id: number
  name: string
  code: string
  status: number
  createTime: string
  updateTime: string
}

export interface RoleQuery {
  name?: string
  code?: string
  status?: number
  page: number
  size: number
}

export interface RoleForm {
  id?: number
  name: string
  code: string
  status: number
}

// 数据字典相关类型定义
export interface Dictionary {
  id: number
  name: string
  code: string
  status: number
  createTime: string
  updateTime: string
}

export interface DictionaryQuery {
  name?: string
  code?: string
  status?: number
  page: number
  size: number
}

export interface DictionaryForm {
  id?: number
  name: string
  code: string
  status: number
}

// 租户相关API
export function getTenants(params: TenantQuery): Promise<ResponseData<Tenant[]>> {
  return request({
    url: '/system/tenants',
    method: 'get',
    params
  })
}

export function getTenantById(id: number): Promise<ResponseData<Tenant>> {
  return request({
    url: `/system/tenants/${id}`,
    method: 'get'
  })
}

export function createTenant(data: TenantForm): Promise<ResponseData<null>> {
  return request({
    url: '/system/tenants',
    method: 'post',
    data
  })
}

export function updateTenant(id: number, data: TenantForm): Promise<ResponseData<null>> {
  return request({
    url: `/system/tenants/${id}`,
    method: 'put',
    data
  })
}

export function deleteTenant(id: number): Promise<ResponseData<null>> {
  return request({
    url: `/system/tenants/${id}`,
    method: 'delete'
  })
}

// 用户相关API
export function getUsers(params: UserQuery): Promise<ResponseData<User[]>> {
  return request({
    url: '/system/users',
    method: 'get',
    params
  })
}

export function getUserById(id: number): Promise<ResponseData<User>> {
  return request({
    url: `/system/users/${id}`,
    method: 'get'
  })
}

export function createUser(data: UserForm): Promise<ResponseData<null>> {
  return request({
    url: '/system/users',
    method: 'post',
    data
  })
}

export function updateUser(id: number, data: UserForm): Promise<ResponseData<null>> {
  return request({
    url: `/system/users/${id}`,
    method: 'put',
    data
  })
}

export function deleteUser(id: number): Promise<ResponseData<null>> {
  return request({
    url: `/system/users/${id}`,
    method: 'delete'
  })
}

// 角色相关API
export function getRoles(params: RoleQuery): Promise<ResponseData<Role[]>> {
  return request({
    url: '/system/roles',
    method: 'get',
    params
  })
}

export function getRoleById(id: number): Promise<ResponseData<Role>> {
  return request({
    url: `/system/roles/${id}`,
    method: 'get'
  })
}

export function createRole(data: RoleForm): Promise<ResponseData<null>> {
  return request({
    url: '/system/roles',
    method: 'post',
    data
  })
}

export function updateRole(id: number, data: RoleForm): Promise<ResponseData<null>> {
  return request({
    url: `/system/roles/${id}`,
    method: 'put',
    data
  })
}

export function deleteRole(id: number): Promise<ResponseData<null>> {
  return request({
    url: `/system/roles/${id}`,
    method: 'delete'
  })
}

// 数据字典相关API
export function getDictionaries(params: DictionaryQuery): Promise<ResponseData<Dictionary[]>> {
  return request({
    url: '/system/dictionaries',
    method: 'get',
    params
  })
}

export function getDictionaryById(id: number): Promise<ResponseData<Dictionary>> {
  return request({
    url: `/system/dictionaries/${id}`,
    method: 'get'
  })
}

export function createDictionary(data: DictionaryForm): Promise<ResponseData<null>> {
  return request({
    url: '/system/dictionaries',
    method: 'post',
    data
  })
}

export function updateDictionary(id: number, data: DictionaryForm): Promise<ResponseData<null>> {
  return request({
    url: `/system/dictionaries/${id}`,
    method: 'put',
    data
  })
}

export function deleteDictionary(id: number): Promise<ResponseData<null>> {
  return request({
    url: `/system/dictionaries/${id}`,
    method: 'delete'
  })
}