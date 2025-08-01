// 通用API响应类型
export interface ApiResponse<T = any> {
  code: number
  msg: string
  data: T
}

// 分页数据类型
export interface PageData<T> {
  items: T[]
  total: number
  pageNum: number
  pageSize: number
}

// 用户相关类型
export interface User {
  userId: number
  userName: string
  nickName: string
  deptId: number
  deptName: string
  phoneNumber: string
  email: string
  sex: string
  status: string
  createTime: string
  posts: Post[]
  roles: Role[]
}

export interface Post {
  postId: number
  postName: string
}

export interface Role {
  roleId: number
  roleName: string
}

// 流程定义相关类型
export interface ProcessDefinition {
  id: number
  name: string
  key: string
  version: number
  deploymentTime: string
  status: number
  description: string
}