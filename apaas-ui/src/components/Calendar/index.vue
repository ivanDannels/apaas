<template>
  <el-calendar
    ref="calendarRef"
    :model-value="modelValue"
    :range="range"
    v-bind="$attrs"
    @update:modelValue="handleUpdateModelValue"
  >
    <template #date-cell="{data}" v-if="$slots['date-cell']">
      <slot name="date-cell" :data="data"></slot>
    </template>
  </el-calendar>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 定义属性
const props = defineProps({
  // 绑定值
  modelValue: {
    type: Date,
    default: () => new Date()
  },
  // 时间范围
  range: {
    type: Array,
    default: () => []
  }
})

// 定义响应式数据
const calendarRef = ref(null)

// 定义事件
const emit = defineEmits(['update:modelValue'])

// 处理更新 modelValue 事件
const handleUpdateModelValue = (val) => {
  emit('update:modelValue', val)
}
</script>

<style lang="scss" scoped>
// 日历样式可以根据需要自定义
</style>