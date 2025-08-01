<template>
  <el-alert
    ref="alertRef"
    :title="title"
    :type="type"
    :description="description"
    :closable="closable"
    :center="center"
    :close-text="closeText"
    :show-icon="showIcon"
    :effect="effect"
    v-bind="$attrs"
    @close="handleClose"
  >
    <template #title v-if="$slots.title">
      <slot name="title"></slot>
    </template>
    
    <template #default v-if="$slots.default">
      <slot></slot>
    </template>
  </el-alert>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { AlertInstance } from 'element-plus'

// 定义属性
const props = defineProps({
  // 标题
  title: {
    type: String,
    default: ''
  },
  // 类型
  type: {
    type: String,
    default: 'info', // success / warning / info / error
    validator: (value: string) => ['success', 'warning', 'info', 'error'].includes(value)
  },
  // 辅助性文字
  description: {
    type: String,
    default: ''
  },
  // 是否可关闭
  closable: {
    type: Boolean,
    default: true
  },
  // 文字是否居中
  center: {
    type: Boolean,
    default: false
  },
  // 关闭按钮自定义文本
  closeText: {
    type: String,
    default: ''
  },
  // 是否显示图标
  showIcon: {
    type: Boolean,
    default: false
  },
  // 选择提供的主题
  effect: {
    type: String,
    default: 'light', // light / dark
    validator: (value: string) => ['light', 'dark'].includes(value)
  }
})

type AlertEmits = {
  close: []
}

// 定义事件
const emit = defineEmits<AlertEmits>()

// 定义响应式数据
const alertRef = ref<AlertInstance | null>(null)

// 处理关闭事件
const handleClose = () => {
  emit('close')
}
</script>

<style lang="scss" scoped>
// 警告样式可以根据需要自定义
</style>