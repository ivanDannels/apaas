<template>
  <el-timeline ref="timelineRef" v-bind="$attrs">
    <slot></slot>
  </el-timeline>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { TimelineInstance } from 'element-plus'

// 定义响应式数据
const timelineRef = ref<TimelineInstance | null>(null)
</script>

<style lang="scss" scoped>
// 时间线样式可以根据需要自定义
</style>