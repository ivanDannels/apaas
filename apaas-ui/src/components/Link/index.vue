<template>
  <el-link
    ref="linkRef"
    :type="type"
    :underline="underline"
    :disabled="disabled"
    :href="href"
    :target="target"
    :to="to"
    :replace="replace"
    :icon="icon"
    v-bind="$attrs"
    @click="handleClick"
  >
    <slot></slot>
  </el-link>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 定义属性
const props = defineProps({
  // 类型
  type: {
    type: String,
    default: 'primary', // primary / success / warning / danger / info
    validator: (value) => ['primary', 'success', 'warning', 'danger', 'info'].includes(value)
  },
  // 是否下划线
  underline: {
    type: Boolean,
    default: true
  },
  // 是否禁用
  disabled: {
    type: Boolean,
    default: false
  },
  // 原生 href 属性
  href: {
    type: String,
    default: ''
  },
  // 原生 target 属性
  target: {
    type: String,
    default: '_self', // _blank / _self / _parent / _top
    validator: (value) => ['_blank', '_self', '_parent', '_top'].includes(value)
  },
  // 路由跳转对象
  to: {
    type: [String, Object],
    default: ''
  },
  // 是否替换当前历史记录
  replace: {
    type: Boolean,
    default: false
  },
  // 图标类名
  icon: {
    type: [String, Object],
    default: ''
  }
})

// 定义响应式数据
const linkRef = ref(null)

// 定义事件
const emit = defineEmits(['click'])

// 处理点击事件
const handleClick = (event) => {
  // 如果禁用，则阻止默认行为和事件冒泡
  if (props.disabled) {
    event.preventDefault()
    event.stopPropagation()
    return
  }
  
  // 触发点击事件
  emit('click', event)
}
</script>

<style lang="scss" scoped>
// 链接样式可以根据需要自定义
</style>