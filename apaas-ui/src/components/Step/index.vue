<template>
  <el-step
    :title="title"
    :description="description"
    :icon="icon"
    :status="status"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-step>
</template>

<script setup lang="ts">
// 定义属性
const props = defineProps({
  // 标题
  title: {
    type: String,
    default: ''
  },
  // 描述性文字
  description: {
    type: String,
    default: ''
  },
  // 图标
  icon: {
    type: String,
    default: ''
  },
  // 当前步骤的状态
  status: {
    type: String,
    default: '', // wait / process / finish / error / success
    validator: (value) => ['', 'wait', 'process', 'finish', 'error', 'success'].includes(value)
  }
})
</script>

<style lang="scss" scoped>
// 步骤条项样式可以根据需要自定义
</style>