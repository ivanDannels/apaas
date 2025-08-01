<template>
  <el-progress
    ref="progressRef"
    :percentage="percentage"
    :type="type"
    :stroke-width="strokeWidth"
    :text-inside="textInside"
    :status="status"
    :color="color"
    :width="width"
    :show-text="showText"
    :stroke-linecap="strokeLinecap"
    :format="format"
    :indeterminate="indeterminate"
    :duration="duration"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-progress>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'

// 定义属性
const props = defineProps({
  // 百分比（必填）
  percentage: {
    type: Number,
    default: 0,
    required: true,
    validator: (value) => value >= 0 && value <= 100
  },
  // 进度条类型
  type: {
    type: String,
    default: 'line', // line / circle / dashboard
    validator: (value) => ['line', 'circle', 'dashboard'].includes(value)
  },
  // 进度条的宽度
  strokeWidth: {
    type: Number,
    default: 6
  },
  // 进度条显示文字内置在进度条内
  textInside: {
    type: Boolean,
    default: false
  },
  // 进度条当前状态
  status: {
    type: String,
    default: '', // success / exception / warning
    validator: (value) => ['', 'success', 'exception', 'warning'].includes(value)
  },
  // 进度条背景色
  color: {
    type: [String, Array, Function],
    default: ''
  },
  // 环形进度条画布宽度
  width: {
    type: Number,
    default: 126
  },
  // 是否显示进度条文字内容
  showText: {
    type: Boolean,
    default: true
  },
  // 圆形进度条线段形状
  strokeLinecap: {
    type: String,
    default: 'round', // butt / round / square
    validator: (value) => ['butt', 'round', 'square'].includes(value)
  },
  // 指定进度条文字内容
  format: {
    type: Function,
    default: null
  },
  // 是否为不确定进度
  indeterminate: {
    type: Boolean,
    default: false
  },
  // 不确定进度动画时长
  duration: {
    type: Number,
    default: 3
  }
})

// 定义响应式数据
const progressRef = ref(null)

// 定义暴露给父组件的方法
const updatePercentage = (percentage) => {
  // 可以通过修改percentage属性来更新进度
}

defineExpose({
  updatePercentage
})
</script>

<style lang="scss" scoped>
// 进度条样式可以根据需要自定义
</style>