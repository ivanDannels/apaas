<template>
  <el-image
    ref="imageRef"
    :src="src"
    :fit="fit"
    :lazy="lazy"
    :scroll-container="scrollContainer"
    :preview-src-list="previewSrcList"
    :preview-teleported="previewTeleported"
    :zoom-rate="zoomRate"
    :max-scale="maxScale"
    :min-scale="minScale"
    :preview-teleported="previewTeleported"
    :z-index="zIndex"
    :initial-index="initialIndex"
    :infinite="infinite"
    :hide-on-click-modal="hideOnClickModal"
    :modal-append-to-body="modalAppendToBody"
    :close-on-press-escape="closeOnPressEscape"
    v-bind="$attrs"
    @load="handleLoad"
    @error="handleError"
    @switch="handleSwitch"
    @close="handleClose"
  >
    <template #placeholder v-if="$slots.placeholder">
      <slot name="placeholder"></slot>
    </template>
    
    <template #error v-if="$slots.error">
      <slot name="error"></slot>
    </template>
  </el-image>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { ImageInstance } from 'element-plus'

// 定义属性
interface Props {
  // 图片源
  src?: string
  // 确定图片如何适应容器
  fit?: 'fill' | 'contain' | 'cover' | 'none' | 'scale-down'
  // 是否开启懒加载
  lazy?: boolean
  // 开启懒加载后，监听 scroll 事件的容器
  scrollContainer?: string | HTMLElement
  // 预览数组
  previewSrcList?: string[]
  // 图片预览是否被挂载到 body 元素
  previewTeleported?: boolean
  // 缩放速率
  zoomRate?: number
  // 最大缩放比例
  maxScale?: number
  // 最小缩放比例
  minScale?: number
  // 预览时遮罩层的 z-index
  zIndex?: number
  // 初始预览图像索引
  initialIndex?: number
  // 是否可以无限循环预览
  infinite?: boolean
  // 是否可以通过点击遮罩层关闭预览
  hideOnClickModal?: boolean
  // 是否将预览弹出层插入至 body 元素
  modalAppendToBody?: boolean
  // 是否可以通过按下 ESC 关闭预览
  closeOnPressEscape?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  src: '',
  fit: 'fill',
  lazy: false,
  scrollContainer: '',
  previewSrcList: () => [],
  previewTeleported: false,
  zoomRate: 1.2,
  maxScale: 7,
  minScale: 0.1,
  zIndex: 2000,
  initialIndex: 0,
  infinite: true,
  hideOnClickModal: false,
  modalAppendToBody: true,
  closeOnPressEscape: true
})

// 定义响应式数据
const imageRef = ref<ImageInstance | null>(null)

// 定义事件
interface Emits {
  (e: 'load', event: Event): void
  (e: 'error', event: Event): void
  (e: 'switch', index: number): void
  (e: 'close'): void
}

const emit = defineEmits<Emits>()

// 处理图片加载成功事件
const handleLoad = (e: Event) => {
  emit('load', e)
}

// 处理图片加载失败事件
const handleError = (e: Event) => {
  emit('error', e)
}

// 处理预览切换事件
const handleSwitch = (val: number) => {
  emit('switch', val)
}

// 处理预览关闭事件
const handleClose = () => {
  emit('close')
}
</script>

<style lang="scss" scoped>
// 图片样式可以根据需要自定义
</style>