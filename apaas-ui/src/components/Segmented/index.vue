<template>
  <el-segmented
    ref="segmentedRef"
    :model-value="modelValue"
    :options="options"
    :size="size"
    :disabled="disabled"
    :validate-event="validateEvent"
    :block="block"
    v-bind="$attrs"
    @update:modelValue="handleUpdateModelValue"
    @change="handleChange"
  >
    <template #default="{ item }" v-if="$slots.default">
      <slot :item="item"></slot>
    </template>
  </el-segmented>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { SegmentedInstance, SegmentedOption } from 'element-plus'

// 定义属性
interface Props {
  // 绑定值
  modelValue?: string | number | boolean
  // 选项列表
  options?: SegmentedOption[]
  // 尺寸
  size?: 'large' | 'default' | 'small'
  // 是否禁用
  disabled?: boolean
  // 是否触发表单验证
  validateEvent?: boolean
  // 是否为块级元素
  block?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: '',
  options: () => [],
  size: 'default',
  disabled: false,
  validateEvent: true,
  block: false
})

// 定义响应式数据
const segmentedRef = ref<SegmentedInstance | null>(null)

// 定义事件
interface Emits {
  (e: 'update:modelValue', value: string | number | boolean): void
  (e: 'change', value: string | number | boolean): void
}

const emit = defineEmits<Emits>()

// 处理更新 modelValue 事件
const handleUpdateModelValue = (val: string | number | boolean) => {
  emit('update:modelValue', val)
}

// 处理值变化事件
const handleChange = (val: string | number | boolean) => {
  emit('change', val)
}
</script>

<style lang="scss" scoped>
// 分割线组件样式可以根据需要自定义
</style>