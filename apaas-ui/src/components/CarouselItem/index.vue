<template>
  <el-carousel-item
    ref="carouselItemRef"
    :name="name"
    :label="label"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-carousel-item>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 定义属性
const props = defineProps({
  // 幻灯片的名字，可用作 setActiveItem 的参数
  name: {
    type: String,
    default: ''
  },
  // 幻灯片所对应的内容文本
  label: {
    type: [String, Number],
    default: ''
  }
})

// 定义响应式数据
const carouselItemRef = ref(null)
</script>

<style lang="scss" scoped>
// 走马灯项样式可以根据需要自定义
</style>