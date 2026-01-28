import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

/**
 * Vite 配置文件
 */
export default defineConfig({
  /**
   * plugins: 插件配置，让 Element Plus 组件可以自动导入
   */
  plugins: [
    /**
     * @vitejs/plugin-vue: Vue 插件
     */
    vue(),
    /**
     * unplugin-auto-import: 自动导入插件
     */
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    /**
     * unplugin-vue-components: Vue 组件插件
     */
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  server: {
    port: 5173,
    /**
     * proxy: 代理配置，解决跨域问题（前端5173端口 → 后端8080端口）
     */
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
