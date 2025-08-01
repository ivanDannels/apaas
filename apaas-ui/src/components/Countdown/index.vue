<template>
  <el-countdown
    ref="countdownRef"
    :value="value"
    :format="format"
    :prefix="prefix"
    :suffix="suffix"
    :title="title"
    :value-style="valueStyle"
    :title-style="titleStyle"
    v-bind="$attrs"
    @finish="handleFinish"
  >
    <template #prefix v-if="$slots.prefix || prefix">
      <slot name="prefix">{{ prefix }}</slot>
    </template>
    
    <template #suffix v-if="$slots.suffix || suffix">
      <slot name="suffix">{{ suffix }}</slot>
    </template>
    
    <template #title v-if="$slots.title || title">
      <slot name="title">{{ title }}</slot>
    </template>
    
    <template #default="{ days, hours, minutes, seconds, milliseconds }" v-if="$slots.default">
      <slot :days="days" :hours="hours" :minutes="minutes" :seconds="seconds" :milliseconds="milliseconds"></slot>
    </template>
  </el-countdown>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 定义属性
const props = defineProps({
  // 目标时间
  value: {
    type: [Number, String, Date],
    default: () => Date.now() + 3600000 // 默认1小时后
  },
  // 时间格式
  format: {
    type: String,
    default: 'HH:mm:ss'
  },
  // 前缀
  prefix: {
    type: String,
    default: ''
  },
  // 后缀
  suffix: {
    type: String,
    default: ''
  },
  // 标题
  title: {
    type: String,
    default: ''
  },
  // 数值样式
  valueStyle: {
    type: Object,
    default: () => ({})
  },
  // 标题样式
  titleStyle: {
    type: Object,
    default: () => ({})
  }
})

// 定义响应式数据
const countdownRef = ref(null)

// 定义事件
const emit = defineEmits(['finish'])

// 处理倒计时结束事件
const handleFinish = () => {
  emit('finish')
}
</script>

<style lang="scss" scoped>
// 倒计时样式可以根据需要自定义
</style>