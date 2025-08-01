<template>
  <el-empty
    ref="emptyRef"
    :image-size="imageSize"
    :description="description"
    v-bind="$attrs"
  >
    <template #image v-if="$slots.image">
      <slot name="image"></slot>
    </template>
    
    <template #description v-if="$slots.description">
      <slot name="description"></slot>
    </template>
    
    <template #default v-if="$slots.default">
      <slot></slot>
    </template>
  </el-empty>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 定义属性
const props = defineProps({
  // 图像大小
  imageSize: {
    type: Number,
    default: 0
  },
  // 描述文字
  description: {
    type: String,
    default: '暂无数据'
  }
})

// 定义响应式数据
const emptyRef = ref(null)
</script>

<style lang="scss" scoped>
// 空状态样式可以根据需要自定义
</style>