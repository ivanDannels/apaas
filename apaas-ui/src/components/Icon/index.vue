<template>
  <el-icon
    ref="iconRef"
    :size="size"
    :color="color"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-icon>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { ComponentInstance } from 'vue'

// 定义属性
interface Props {
  // 图标大小
  size?: number | string
  // 图标颜色
  color?: string
}

const props = withDefaults(defineProps<Props>(), {
  size: '',
  color: ''
})

// 定义响应式数据
const iconRef = ref<ComponentInstance | null>(null)
</script>

<style lang="scss" scoped>
// 图标样式可以根据需要自定义
</style>