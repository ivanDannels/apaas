<template>
  <el-backtop
    ref="backtopRef"
    :target="target"
    :visibility-height="visibilityHeight"
    :right="right"
    :bottom="bottom"
    v-bind="$attrs"
    @click="handleClick"
  >
    <slot></slot>
  </el-backtop>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { BacktopInstance } from 'element-plus'

// 定义属性
const props = defineProps({
  // 触发滚动的对象
  target: {
    type: String,
    default: ''
  },
  // 滚动高度达到此参数值才显示组件
  visibilityHeight: {
    type: Number,
    default: 200
  },
  // 控制组件的右间距
  right: {
    type: Number,
    default: 40
  },
  // 控制组件的下间距
  bottom: {
    type: Number,
    default: 40
  }
})

type BacktopEmits = {
  click: [e: MouseEvent]
}

// 定义响应式数据
const backtopRef = ref<BacktopInstance | null>(null)

// 定义事件
const emit = defineEmits<BacktopEmits>()

// 处理点击事件
const handleClick = (e: MouseEvent) => {
  emit('click', e)
}
</script>

<style lang="scss" scoped>
// 返回顶部样式可以根据需要自定义
</style>