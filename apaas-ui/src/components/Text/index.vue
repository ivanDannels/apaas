<template>
  <el-text
    ref="textRef"
    :type="type"
    :size="size"
    :truncated="truncated"
    :line-clamp="lineClamp"
    :tag="tag"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-text>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { TextInstance } from 'element-plus'

// 定义属性
interface Props {
  // 类型
  type?: 'primary' | 'success' | 'warning' | 'danger' | 'info'
  // 大小
  size?: 'large' | 'default' | 'small'
  // 是否截断文本
  truncated?: boolean
  // 文本行数
  lineClamp?: string | number
  // 渲染的标签
  tag?: string
}

const props = withDefaults(defineProps<Props>(), {
  type: 'primary',
  size: 'default',
  truncated: false,
  lineClamp: '',
  tag: 'span'
})

// 定义响应式数据
const textRef = ref<TextInstance | null>(null)
</script>

<style lang="scss" scoped>
// 文字链接样式可以根据需要自定义
</style>