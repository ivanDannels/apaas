<template>
  <el-button
    ref="buttonRef"
    :type="type"
    :size="buttonSize"
    :plain="plain"
    :round="round"
    :circle="circle"
    :loading="loading"
    :disabled="isDisabled"
    :icon="icon"
    :autofocus="autofocus"
    :native-type="nativeType"
    :block="block"
    v-bind="$attrs"
    @click="handleClick"
    @focus="handleFocus"
    @blur="handleBlur"
  >
    <slot></slot>
  </el-button>
</template>

<script setup lang="ts">
import { ref, computed, inject } from 'vue'
import type { ButtonInstance } from 'element-plus'

// 定义属性
const props = defineProps({
  // 按钮类型
  type: {
    type: String,
    default: 'default', // primary / success / warning / danger / info / text
    validator: (value: string) => ['default', 'primary', 'success', 'warning', 'danger', 'info', 'text'].includes(value)
  },
  // 按钮尺寸
  size: {
    type: String,
    default: ''
  },
  // 是否朴素按钮
  plain: {
    type: Boolean,
    default: false
  },
  // 是否圆角按钮
  round: {
    type: Boolean,
    default: false
  },
  // 是否圆形按钮
  circle: {
    type: Boolean,
    default: false
  },
  // 是否加载中状态
  loading: {
    type: Boolean,
    default: false
  },
  // 是否禁用状态
  disabled: {
    type: Boolean,
    default: undefined
  },
  // 图标类名
  icon: {
    type: String,
    default: ''
  },
  // 是否默认聚焦
  autofocus: {
    type: Boolean,
    default: false
  },
  // 原生 type 属性
  nativeType: {
    type: String,
    default: 'button', // button / submit / reset
    validator: (value: string) => ['button', 'submit', 'reset'].includes(value)
  },
  // 是否为块级元素
  block: {
    type: Boolean,
    default: false
  }
})

type ButtonEmits = {
  click: [event: MouseEvent]
  focus: [event: FocusEvent]
  blur: [event: FocusEvent]
}

// 定义事件
const emit = defineEmits<ButtonEmits>()

// 注入父级表单属性
const formProps: any = inject('formProps', {})

// 定义响应式数据
const buttonRef = ref<ButtonInstance | null>(null)

// 计算属性：是否禁用
const isDisabled = computed(() => {
  // 如果props中明确设置了disabled，则使用props的值
  if (props.disabled !== undefined) {
    return props.disabled
  }
  // 否则使用父级表单的disabled值
  return formProps.disabled || false
})

// 计算属性：按钮尺寸
const buttonSize = computed(() => {
  return props.size || formProps.size || 'default'
})

// 处理点击事件
const handleClick = (event: MouseEvent) => {
  // 如果禁用或加载中，则不触发点击事件
  if (isDisabled.value || props.loading) {
    event.preventDefault()
    return
  }
  
  emit('click', event)
}

// 处理聚焦事件
const handleFocus = (event: FocusEvent) => {
  emit('focus', event)
}

// 处理失焦事件
const handleBlur = (event: FocusEvent) => {
  emit('blur', event)
}

// 定义暴露给父组件的方法
const focus = () => {
  buttonRef.value?.focus()
}

const blur = () => {
  buttonRef.value?.blur()
}

defineExpose({
  focus,
  blur
})
</script>

<style lang="scss" scoped>
// 按钮样式可以根据需要自定义
</style>