<template>
  <div class="navbar">
    <div class="left-menu">
      <el-icon class="menu-icon" @click="toggleSidebar">
        <Expand v-if="isCollapse" />
        <Fold v-else />
      </el-icon>
    </div>
    
    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <el-avatar :size="40" :src="avatar" />
          <span class="user-name">{{ username }}</span>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="handleProfile">
              {{ $t('common.profile') }}
            </el-dropdown-item>
            <el-dropdown-item divided @click="logout">
              {{ $t('login.logout') }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import type { UserInfo } from '@/types/store'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

// 这里应该从用户信息中获取头像和用户名
const avatar = computed(() => 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif')
const username = computed(() => authStore.userInfo?.name || 'Admin')

// 侧边栏折叠状态
const isCollapse = computed(() => {
  // 这里应该连接到侧边栏状态
  return false
})

const toggleSidebar = () => {
  // 这里应该触发侧边栏折叠/展开
  console.log('toggle sidebar')
}

const handleProfile = () => {
  // 处理个人资料
  console.log('handle profile')
}

const logout = async () => {
  await authStore.logout()
  router.push(`/login?redirect=${route.fullPath}`)
}
</script>

<style scoped>
.navbar {
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
}

.left-menu {
  display: flex;
  align-items: center;
}

.menu-icon {
  font-size: 20px;
  cursor: pointer;
}

.right-menu {
  display: flex;
  align-items: center;
}

.avatar-container {
  cursor: pointer;
}

.avatar-wrapper {
  display: flex;
  align-items: center;
}

.user-name {
  margin-left: 10px;
  font-size: 14px;
  color: #606266;
}
</style>