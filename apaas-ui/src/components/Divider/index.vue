<template>
  <el-divider
    ref="dividerRef"
    :direction="direction"
    :content-position="contentPosition"
    :border-style="borderStyle"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-divider>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 定义属性
const props = defineProps({
  // 分割线方向
  direction: {
    type: String,
    default: 'horizontal', // horizontal / vertical
    validator: (value) => ['horizontal', 'vertical'].includes(value)
  },
  // 分割线内容的位置
  contentPosition: {
    type: String,
    default: 'center', // left / center / right
    validator: (value) => ['left', 'center', 'right'].includes(value)
  },
  // 分割线样式
  borderStyle: {
    type: String,
    default: 'solid', // solid / dashed / dotted
    validator: (value) => ['solid', 'dashed', 'dotted'].includes(value)
  }
})

// 定义响应式数据
const dividerRef = ref(null)
</script>

<style lang="scss" scoped>
// 分割线样式可以根据需要自定义
</style>