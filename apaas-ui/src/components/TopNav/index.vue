<template>
  <div class="top-nav-container">
    <el-menu
      :default-active="activeIndex"
      mode="horizontal"
      :ellipsis="false"
      @select="handleSelect"
      background-color="#304156"
      text-color="#fff"
      active-text-color="#409eff"
    >
      <el-menu-item 
        v-for="item in topMenus" 
        :key="item.id" 
        :index="item.id.toString()"
      >
        <svg-icon 
          v-if="item.meta && item.meta.icon" 
          :icon-class="item.meta.icon" 
          class="menu-icon"
        />
        {{ item.meta?.title || item.name }}
      </el-menu-item>
      
      <div class="flex-grow" />
      
      <el-menu-item index="user-center">
        <el-dropdown @command="handleUserCommand">
          <div class="user-info">
            <el-avatar 
              :src="userStore.avatar" 
              size="small" 
              class="user-avatar"
            />
            <span class="user-name">{{ userStore.name }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="profile">个人中心</el-dropdown-item>
              <el-dropdown-item command="resetPwd">修改密码</el-dropdown-item>
              <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store/modules/user'
import { usePermissionStore } from '@/store/modules/permission'

// 定义路由和存储
const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const permissionStore = usePermissionStore()

// 定义响应式数据
const activeIndex = ref('')

// 计算属性
const topMenus = computed(() => {
  return permissionStore.routes.filter(item => 
    item.meta && item.meta.topMenu
  )
})

// 设置激活菜单
const setActiveMenu = () => {
  const path = route.path
  const matching = route.matched
  
  for (let i = matching.length - 1; i >= 0; i--) {
    const item = matching[i]
    if (item.meta && item.meta.topMenu) {
      activeIndex.value = item.meta.topMenuId?.toString() || ''
      return
    }
  }
  
  // 默认激活第一个顶级菜单
  if (topMenus.value.length > 0) {
    activeIndex.value = topMenus.value[0].id?.toString() || ''
  }
}

// 菜单选择事件
const handleSelect = (index) => {
  activeIndex.value = index
  
  // 查找选中的菜单项
  const menuItem = topMenus.value.find(item => 
    item.id?.toString() === index || 
    (item.meta?.topMenuId?.toString() === index)
  )
  
  if (menuItem) {
    // 跳转到菜单的第一个子路由
    if (menuItem.children && menuItem.children.length > 0) {
      const firstChild = menuItem.children[0]
      const path = firstChild.redirect || firstChild.path
      if (path) {
        router.push(path)
      }
    }
  }
}

// 用户操作命令
const handleUserCommand = (command) => {
  switch (command) {
    case 'profile':
      router.push('/system/user/profile')
      break
    case 'resetPwd':
      router.push('/system/user/profile/resetPwd')
      break
    case 'logout':
      ElMessageBox.confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        userStore.logout().then(() => {
          location.href = '/index'
        })
      }).catch(() => {})
      break
    default:
      break
  }
}

// 监听路由变化
watch(
  () => route.path,
  () => {
    setActiveMenu()
  },
  { immediate: true }
)
</script>

<style scoped>
.top-nav-container {
  background-color: #304156;
}

:deep(.el-menu) {
  border: none;
}

:deep(.el-menu--horizontal) {
  height: 50px;
  display: flex;
  align-items: center;
}

:deep(.el-menu-item) {
  height: 50px;
  line-height: 50px;
}

.menu-icon {
  margin-right: 5px;
  width: 16px;
  height: 16px;
  vertical-align: middle;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.user-avatar {
  margin-right: 8px;
}

.user-name {
  color: #fff;
}

.flex-grow {
  flex-grow: 1;
}
</style>