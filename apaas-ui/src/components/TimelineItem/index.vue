<template>
  <el-timeline-item
    ref="timelineItemRef"
    :timestamp="timestamp"
    :hide-timestamp="hideTimestamp"
    :placement="placement"
    :type="type"
    :color="color"
    :size="size"
    :icon="icon"
    :hollow="hollow"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-timeline-item>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { TimelineItemInstance } from 'element-plus'

// 定义属性
interface Props {
  // 时间戳
  timestamp?: string
  // 是否隐藏时间戳
  hideTimestamp?: boolean
  // 时间戳位置
  placement?: 'top' | 'bottom'
  // 节点类型
  type?: '' | 'primary' | 'success' | 'warning' | 'danger' | 'info'
  // 节点颜色
  color?: string
  // 节点尺寸
  size?: 'normal' | 'large'
  // 节点图标
  icon?: string | Record<string, any>
  // 是否空心点
  hollow?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  timestamp: '',
  hideTimestamp: false,
  placement: 'bottom',
  type: '',
  color: '',
  size: 'normal',
  icon: '',
  hollow: false
})

// 定义响应式数据
const timelineItemRef = ref<TimelineItemInstance | null>(null)
</script>

<style lang="scss" scoped>
// 时间线项样式可以根据需要自定义
</style>