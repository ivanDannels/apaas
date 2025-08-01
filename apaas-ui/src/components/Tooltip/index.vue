<template>
  <el-tooltip
    ref="tooltipRef"
    :content="content"
    :placement="placement"
    :disabled="disabled"
    :visible="visible"
    :effect="effect"
    :popper-class="popperClass"
    :popper-style="popperStyle"
    :teleported="teleported"
    :offset="offset"
    :show-after="showAfter"
    :show-arrow="showArrow"
    :hide-after="hideAfter"
    :auto-close="autoClose"
    :manual="manual"
    :popper-options="popperOptions"
    :enterable="enterable"
    :trigger="trigger"
    :trigger-keys="triggerKeys"
    :trigger-on-focus="triggerOnFocus"
    :transition="transition"
    :stop-popper-mouse-event="stopPopperMouseEvent"
    :virtual-ref="virtualRef"
    :virtual-triggering="virtualTriggering"
    :open-delay="openDelay"
    :close-delay="closeDelay"
    :arrow-offset="arrowOffset"
    v-bind="$attrs"
  >
    <slot></slot>
    
    <template #content v-if="$slots.content || content">
      <slot name="content">{{ content }}</slot>
    </template>
  </el-tooltip>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import type { TooltipInstance } from 'element-plus'

// 定义属性
interface Props {
  // 显示的内容
  content?: string
  // Tooltip 的出现位置
  placement?: 'top' | 'top-start' | 'top-end' | 'bottom' | 'bottom-start' | 'bottom-end' | 'left' | 'left-start' | 'left-end' | 'right' | 'right-start' | 'right-end'
  // Tooltip 是否可用
  disabled?: boolean
  // 状态是否可见
  visible?: boolean | null
  // Tooltip 主题
  effect?: 'dark' | 'light'
  // 为 Tooltip 的 popper 添加类名
  popperClass?: string
  // 为 Tooltip 的 popper 添加样式
  popperStyle?: Record<string, any>
  // 是否使用 teleport
  teleported?: boolean
  // 出现位置的偏移量
  offset?: number
  // 延迟出现，单位毫秒
  showAfter?: number
  // 是否显示箭头
  showArrow?: boolean
  // 延迟消失，单位毫秒
  hideAfter?: number
  // Tooltip 出现后自动隐藏延时，单位毫秒
  autoClose?: number
  // 是否手动控制模式
  manual?: boolean
  // popper.js 的参数
  popperOptions?: Record<string, any>
  // 鼠标是否可以进入 tooltip 中
  enterable?: boolean
  // 触发方式
  trigger?: 'hover' | 'click' | 'focus' | 'manual' | ('hover' | 'click' | 'focus' | 'manual')[]
  // 触发方式为 keyboard 时，触发 tooltip 的按键
  triggerKeys?: string[]
  // 当 trigger 为 'focus' 时，是否在获得焦点时触发
  triggerOnFocus?: boolean
  // 动画名称
  transition?: string
  // 是否阻止 popper 的鼠标事件
  stopPopperMouseEvent?: boolean
  // 虚拟引用元素
  virtualRef?: Record<string, any> | null
  // 是否虚拟触发
  virtualTriggering?: boolean
  // 延迟出现，单位毫秒
  openDelay?: number
  // 延迟消失，单位毫秒
  closeDelay?: number
  // 箭头偏移量
  arrowOffset?: number
}

const props = withDefaults(defineProps<Props>(), {
  content: '',
  placement: 'bottom',
  disabled: false,
  visible: null,
  effect: 'dark',
  popperClass: '',
  popperStyle: () => ({}),
  teleported: true,
  offset: 12,
  showAfter: 0,
  showArrow: true,
  hideAfter: 200,
  autoClose: 0,
  manual: false,
  popperOptions: () => ({}),
  enterable: true,
  trigger: 'hover',
  triggerKeys: () => ['Enter', 'Space'],
  triggerOnFocus: true,
  transition: 'el-fade-in-linear',
  stopPopperMouseEvent: true,
  virtualRef: null,
  virtualTriggering: false,
  openDelay: 0,
  closeDelay: 0,
  arrowOffset: 0
})

// 定义响应式数据
const tooltipRef = ref<TooltipInstance | null>(null)

// 定义事件
interface Emits {
  (e: 'visible-change', value: boolean | null): void
}

const emit = defineEmits<Emits>()

// 监听 visible 属性变化
watch(() => props.visible, (newVal) => {
  emit('visible-change', newVal)
})
</script>

<style lang="scss" scoped>
// 文字提示样式可以根据需要自定义
</style>