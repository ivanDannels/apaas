<template>
  <el-collapse-item
    ref="collapseItemRef"
    :name="name"
    :title="title"
    :disabled="disabled"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-collapse-item>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 定义属性
const props = defineProps({
  // 唯一标志符
  name: {
    type: [String, Number],
    default: ''
  },
  // 面板标题
  title: {
    type: String,
    default: ''
  },
  // 是否禁用
  disabled: {
    type: Boolean,
    default: false
  }
})

// 定义响应式数据
const collapseItemRef = ref(null)
</script>

<style lang="scss" scoped>
// 折叠面板项样式可以根据需要自定义
</style>