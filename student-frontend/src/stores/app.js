import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAppStore = defineStore('app', () => {
  const isCollapse = ref(false)
  const activeMenu = ref('/dashboard')

  const toggleSidebar = () => {
    isCollapse.value = !isCollapse.value
  }

  const setActiveMenu = (menu) => {
    activeMenu.value = menu
  }

  return {
    isCollapse, activeMenu, toggleSidebar, setActiveMenu
  }
})
