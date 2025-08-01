<template>
  <el-badge
    ref="badgeRef"
    :value="value"
    :max="max"
    :is-dot="isDot"
    :hidden="hidden"
    :type="type"
    :show-zero="showZero"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-badge>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { BadgeInstance } from 'element-plus'

// 定义属性
const props = defineProps({
  // 显示值
  value: {
    type: [String, Number],
    default: ''
  },
  // 最大值，超过最大值会显示 '{max}+'
  max: {
    type: Number,
    default: 99
  },
  // 是否显示小圆点
  isDot: {
    type: Boolean,
    default: false
  },
  // 是否隐藏 badge
  hidden: {
    type: Boolean,
    default: false
  },
  // 类型
  type: {
    type: String,
    default: 'danger', // primary / success / warning / danger / info
    validator: (value: string) => ['primary', 'success', 'warning', 'danger', 'info'].includes(value)
  },
  // 当数值为 0 时，是否展示 badge
  showZero: {
    type: Boolean,
    default: true
  }
})

// 定义响应式数据
const badgeRef = ref<BadgeInstance | null>(null)
</script>

<style lang="scss" scoped>
// 徽章样式可以根据需要自定义
</style>