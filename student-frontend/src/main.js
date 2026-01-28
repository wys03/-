/**
 * 入口文件
 */
import { createApp } from 'vue'        // Vue 3 应用工厂
import { createPinia } from 'pinia'    // 状态管理库
import router from './router'          // 路由配置
import App from './App.vue'            // 根组件
import ElementPlus from 'element-plus' // UI 组件库
import 'element-plus/dist/index.css'   // UI 样式
import * as ElementPlusIconsVue from '@element-plus/icons-vue' // 图标库

const app = createApp(App)  // 基于 App.vue 创建 Vue 应用实例
const pinia = createPinia()  // 创建 Pinia 状态存储实例

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)  // 将所有 Element Plus 图标注册为全局组件
}

app.use(pinia)      // 启用 Pinia 状态管理
app.use(router)     // 启用 Vue Router 路由
app.use(ElementPlus) // 启用 Element Plus UI 库

app.mount('#app')  // 将应用挂载到 index.html 中 id="app" 的根元素