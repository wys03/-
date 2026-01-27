import axios from 'axios'
import { ElMessage } from 'element-plus'

/**
 * 创建axios实例
 */
const service = axios.create({
  baseURL: '/api',
  timeout: 30000
})

/**
 * 请求拦截器
 * 在请求发送之前做一些处理
 */
service.interceptors.request.use(
  config => {
    console.log('请求配置:', config)
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

/**
 * 响应拦截器
 * 在响应数据返回之后做一些处理
 */
service.interceptors.response.use(
  response => {
    const res = response.data
    console.log('响应数据:', res)
    if (res.code === 200) {
      return res
    } else {
      // 如果错误信息包含Duplicate entry，则不显示，由组件处理
      if (!res.message || !res.message.includes('Duplicate entry')) {
        ElMessage.error(res.message || '请求失败')
      }
      return Promise.reject(new Error(res.message || '请求失败'))
    }
  },
  error => {
    console.error('响应错误:', error)
    if (error.response) {
      console.error('错误状态码:', error.response.status)
      console.error('错误数据:', error.response.data)
      // 对于重复学号错误，不显示默认错误信息
      const errorData = error.response.data
      if (errorData && errorData.message && errorData.message.includes('Duplicate entry')) {
        // 跳过显示，由组件处理
      } else {
        ElMessage.error(`请求失败: ${error.response.status}`)
      }
    } else if (error.request) {
      ElMessage.error('网络错误，请检查后端服务是否启动')
    } else {
      ElMessage.error(error.message || '网络错误')
    }
    return Promise.reject(error)
  }
)

/**
 * 导出axios实例
 */
export default service
