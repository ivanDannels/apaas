<template>
  <div class="permission-container">
    <slot v-if="value" />
    <slot v-else name="no-match" />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useUserStore } from '@/store/modules/user'

// 定义属性
const props = defineProps({
  // 权限标识
  value: {
    type: [String, Array],
    default: ''
  },
  // 权限类型: hasPermission(拥有权限) / lacksPermission( lack权限)
  type: {
    type: String,
    default: 'hasPermission'
  }
})

// 定义响应式数据
const userStore = useUserStore()

// 计算属性
const hasPermission = computed(() => {
  // 超级管理员拥有所有权限
  if (userStore.roles.includes('admin')) {
    return true
  }
  
  // 获取用户权限
  const userPermissions = userStore.permissions
  
  // 如果权限标识为空，则默认有权限
  if (!props.value || props.value.length === 0) {
    return true
  }
  
  // 将权限标识转换为数组
  const requiredPermissions = Array.isArray(props.value) ? props.value : [props.value]
  
  // 根据权限类型判断
  if (props.type === 'hasPermission') {
    // 拥有权限：用户权限包含所需权限中的任意一个
    return requiredPermissions.some(permission => 
      userPermissions.includes(permission)
    )
  } else {
    // lack权限：用户权限不包含所需权限中的任意一个
    return !requiredPermissions.some(permission => 
      userPermissions.includes(permission)
    )
  }
})
</script>

<style scoped>
.permission-container {
  display: inline-block;
}
</style>