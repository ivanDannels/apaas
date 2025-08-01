<template>
  <el-popover
    ref="popoverRef"
    :trigger="trigger"
    :title="title"
    :effect="effect"
    :content="content"
    :placement="placement"
    :disabled="disabled"
    :visible="visible"
    :transition="transition"
    :popper-class="popperClass"
    :popper-style="popperStyle"
    :teleported="teleported"
    :offset="offset"
    :show-arrow="showArrow"
    :show-after="showAfter"
    :hide-after="hideAfter"
    :auto-close="autoClose"
    :popper-options="popperOptions"
    :tabindex="tabindex"
    :persistent="persistent"
    :gpu-acceleration="gpuAcceleration"
    v-bind="$attrs"
    @show="handleShow"
    @hide="handleHide"
    @after-enter="handleAfterEnter"
    @after-leave="handleAfterLeave"
  >
    <template #reference v-if="$slots.reference">
      <slot name="reference"></slot>
    </template>
    
    <template #default v-if="$slots.default || content">
      <slot>{{ content }}</slot>
    </template>
  </el-popover>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'

// 定义属性
const props = defineProps({
  // 触发方式
  trigger: {
    type: String,
    default: 'click', // click / hover / focus / manual
    validator: (value) => ['click', 'hover', 'focus', 'manual'].includes(value)
  },
  // 标题
  title: {
    type: String,
    default: ''
  },
  // Tooltip 主题
  effect: {
    type: String,
    default: 'dark', // dark / light
    validator: (value) => ['dark', 'light'].includes(value)
  },
  // 显示的内容
  content: {
    type: String,
    default: ''
  },
  // 出现位置
  placement: {
    type: String,
    default: 'bottom',
    validator: (value) => [
      'top', 'top-start', 'top-end',
      'bottom', 'bottom-start', 'bottom-end',
      'left', 'left-start', 'left-end',
      'right', 'right-start', 'right-end'
    ].includes(value)
  },
  // Popover 是否可用
  disabled: {
    type: Boolean,
    default: false
  },
  // 状态是否可见
  visible: {
    type: Boolean,
    default: null
  },
  // 动画名称
  transition: {
    type: String,
    default: 'el-fade-in-linear'
  },
  // 为 Popover 的 popper 添加类名
  popperClass: {
    type: String,
    default: ''
  },
  // 为 Popover 的 popper 添加样式
  popperStyle: {
    type: Object,
    default: () => ({})
  },
  // 是否使用 teleport
  teleported: {
    type: Boolean,
    default: true
  },
  // 出现位置的偏移量
  offset: {
    type: Number,
    default: 12
  },
  // 是否显示箭头
  showArrow: {
    type: Boolean,
    default: true
  },
  // 延迟出现，单位毫秒
  showAfter: {
    type: Number,
    default: 0
  },
  // 延迟消失，单位毫秒
  hideAfter: {
    type: Number,
    default: 200
  },
  // Popover 出现后自动隐藏延时，单位毫秒
  autoClose: {
    type: Number,
    default: 0
  },
  // popper.js 的参数
  popperOptions: {
    type: Object,
    default: () => ({})
  },
  // Popover 的 tabindex
  tabindex: {
    type: [Number, String],
    default: 0
  },
  // 当 popover 关闭时，是否停留在内存中
  persistent: {
    type: Boolean,
    default: true
  },
  // 是否启用 GPU 加速
  gpuAcceleration: {
    type: Boolean,
    default: true
  }
})

// 定义响应式数据
const popoverRef = ref(null)

// 定义事件
const emit = defineEmits(['show', 'hide', 'after-enter', 'after-leave', 'update:visible'])

// 处理显示事件
const handleShow = () => {
  emit('show')
  emit('update:visible', true)
}

// 处理隐藏事件
const handleHide = () => {
  emit('hide')
  emit('update:visible', false)
}

// 处理动画进入完成事件
const handleAfterEnter = () => {
  emit('after-enter')
}

// 处理动画离开完成事件
const handleAfterLeave = () => {
  emit('after-leave')
}

// 监听 visible 属性变化
watch(() => props.visible, (newVal) => {
  if (popoverRef.value) {
    if (newVal) {
      popoverRef.value.show()
    } else {
      popoverRef.value.hide()
    }
  }
})
</script>

<style lang="scss" scoped>
// 弹出框样式可以根据需要自定义
</style>