<template>
  <el-statistic
    ref="statisticRef"
    :value="value"
    :decimal-separator="decimalSeparator"
    :group-separator="groupSeparator"
    :precision="precision"
    :formatter="formatter"
    :title="title"
    :value-style="valueStyle"
    :title-style="titleStyle"
    v-bind="$attrs"
  >
    <template #prefix v-if="$slots.prefix">
      <slot name="prefix"></slot>
    </template>
    
    <template #suffix v-if="$slots.suffix">
      <slot name="suffix"></slot>
    </template>
    
    <template #title v-if="$slots.title || title">
      <slot name="title">{{ title }}</slot>
    </template>
    
    <template #formatter v-if="$slots.formatter">
      <slot name="formatter" :value="value"></slot>
    </template>
  </el-statistic>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { StatisticInstance } from 'element-plus'

// 定义属性
interface Props {
  // 数值内容
  value?: number
  // 小数点分割符号
  decimalSeparator?: string
  // 千分位分隔符
  groupSeparator?: string
  // 小数点精度
  precision?: number
  // 自定义数值格式化函数
  formatter?: ((value: number) => string) | null
  // 标题
  title?: string
  // 数值样式
  valueStyle?: Record<string, any>
  // 标题样式
  titleStyle?: Record<string, any>
}

const props = withDefaults(defineProps<Props>(), {
  value: 0,
  decimalSeparator: '.',
  groupSeparator: ',',
  precision: 0,
  formatter: null,
  title: '',
  valueStyle: () => ({}),
  titleStyle: () => ({})
})

// 定义响应式数据
const statisticRef = ref<StatisticInstance | null>(null)
</script>

<style lang="scss" scoped>
// 统计数值样式可以根据需要自定义
</style>