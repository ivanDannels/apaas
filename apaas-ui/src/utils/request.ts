import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse, InternalAxiosRequestConfig } from 'axios'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/auth'

// 定义响应数据结构
interface ResponseData<T = any> {
  code: number
  data: T
  message: string
}

// 创建axios实例
const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL, // api的base_url
  timeout: 15000 // 请求超时时间
})

// 请求拦截器配置类型
interface CustomAxiosRequestConfig extends InternalAxiosRequestConfig {
  // 可以添加自定义属性
}

// request拦截器
service.interceptors.request.use(
  (config: CustomAxiosRequestConfig): CustomAxiosRequestConfig => {
    // 在发送请求之前做些什么
    const authStore = useAuthStore()
    if (authStore.token) {
      config.headers['Authorization'] = `Bearer ${authStore.token}`
    }
    return config
  },
  (error: any) => {
    // 对请求错误做些什么
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response拦截器
service.interceptors.response.use(
  (response: AxiosResponse<ResponseData>): ResponseData => {
    const res = response.data
    
    // 如果返回的状态码为200，说明接口请求成功，直接返回数据
    if (res.code === 200) {
      return res
    } 
    
    // 如果返回的状态码不是200，说明接口请求失败
    // 如果服务器返回了自定义错误代码和错误消息，则将其显示给用户
    ElMessage({
      message: res.message || 'Error',
      type: 'error',
      duration: 5 * 1000
    })
    
    return Promise.reject(new Error(res.message || 'Error'))
  },
  (error: any) => {
    console.log('err' + error) // for debug
    ElMessage({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
export type { ResponseData }