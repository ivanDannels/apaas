<template>
  <div
    ref="infiniteScrollRef"
    v-infinite-scroll="loadMore"
    :infinite-scroll-disabled="disabled"
    :infinite-scroll-delay="delay"
    :infinite-scroll-distance="distance"
    :infinite-scroll-immediate="immediate"
    v-bind="$attrs"
  >
    <slot></slot>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 定义属性
const props = defineProps({
  // 是否禁用
  disabled: {
    type: Boolean,
    default: false
  },
  // 节流时延，单位为ms
  delay: {
    type: Number,
    default: 200
  },
  // 触发加载的距离阈值，单位为px
  distance: {
    type: Number,
    default: 0
  },
  // 是否立即执行加载方法，以防初始状态下内容无法撑满容器
  immediate: {
    type: Boolean,
    default: true
  }
})

// 定义响应式数据
const infiniteScrollRef = ref(null)

// 定义事件
const emit = defineEmits(['load'])

// 加载更多数据
const loadMore = () => {
  // 如果禁用，则不触发加载
  if (props.disabled) return
  
  // 触发加载事件
  emit('load')
}
</script>

<style lang="scss" scoped>
// 无限滚动样式可以根据需要自定义
</style>