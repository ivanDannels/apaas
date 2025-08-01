<template>
  <div class="sidebar-container">
    <div class="logo-container">
      <h1 class="logo">APaaS平台</h1>
    </div>
    
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :unique-opened="false"
        :active-text-color="variables.menuActiveText"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item 
          v-for="route in permissionStore.routes" 
          :key="route.path" 
          :item="route" 
          :base-path="route.path" 
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { usePermissionStore } from '@/stores/permission'
import type { RouteItem } from '@/types/store'
import SidebarItem from './SidebarItem.vue'
import variables from '@/styles/variables.module.scss'

const route = useRoute()
const permissionStore = usePermissionStore()

const activeMenu = computed(() => {
  const { meta, path } = route
  // if set path, the sidebar will highlight the path you set
  if (meta.activeMenu) {
    return meta.activeMenu
  }
  return path
})

const isCollapse = computed(() => {
  // 这里可以根据需要添加侧边栏折叠逻辑
  return false
})
</script>

<style scoped>
.sidebar-container {
  background-color: #304156;
  height: 100%;
  transition: width 0.28s;
  width: 210px;
  overflow: hidden;
}

.logo-container {
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #2b2f3a;
}

.logo {
  color: #fff;
  font-size: 18px;
  margin: 0;
}

.scrollbar-wrapper {
  overflow-x: hidden !important;
}
</style>