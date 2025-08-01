<template>
  <el-collapse
    ref="collapseRef"
    :model-value="modelValue"
    :accordion="accordion"
    v-bind="$attrs"
    @update:modelValue="handleUpdateModelValue"
  >
    <slot></slot>
  </el-collapse>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 定义属性
const props = defineProps({
  // 当前激活的面板
  modelValue: {
    type: [Array, String, Number],
    default: () => []
  },
  // 是否手风琴模式
  accordion: {
    type: Boolean,
    default: false
  }
})

// 定义响应式数据
const collapseRef = ref(null)

// 定义事件
const emit = defineEmits(['update:modelValue'])

// 处理更新 modelValue 事件
const handleUpdateModelValue = (val) => {
  emit('update:modelValue', val)
}
</script>

<style lang="scss" scoped>
// 折叠面板样式可以根据需要自定义
</style>