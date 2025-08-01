<template>
  <el-skeleton
    ref="skeletonRef"
    :loading="loading"
    :animated="animated"
    :count="count"
    :row="row"
    :row-width="rowWidth"
    :throttle="throttle"
    v-bind="$attrs"
  >
    <template #template v-if="$slots.template">
      <slot name="template"></slot>
    </template>
    
    <template #default v-if="!loading && $slots.default">
      <slot></slot>
    </template>
  </el-skeleton>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { SkeletonInstance } from 'element-plus'

// 定义属性
interface Props {
  // 是否显示骨架屏
  loading?: boolean
  // 是否使用动画
  animated?: boolean
  // 渲染多少个 template
  count?: number
  // 骨架屏段落数量
  row?: number
  // 骨架屏段落宽度
  rowWidth?: number | string | (number | string)[]
  // 延迟显示加载效果的时间(ms)
  throttle?: number
}

const props = withDefaults(defineProps<Props>(), {
  loading: true,
  animated: false,
  count: 1,
  row: 3,
  rowWidth: '100%',
  throttle: 0
})

// 定义响应式数据
const skeletonRef = ref<SkeletonInstance | null>(null)
</script>

<style lang="scss" scoped>
// 骨架屏样式可以根据需要自定义
</style>