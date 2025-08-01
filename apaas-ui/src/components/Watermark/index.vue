<template>
  <div
    ref="watermarkRef"
    :style="watermarkStyle"
    v-bind="$attrs"
  >
    <slot></slot>
    
    <div
      v-if="watermarkVisible"
      ref="watermarkCanvasRef"
      :style="canvasStyle"
      class="watermark-canvas"
    ></div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted, watch } from 'vue'

// 定义属性
const props = defineProps({
  // 水印文本
  content: {
    type: String,
    default: ''
  },
  // 水印宽度
  width: {
    type: Number,
    default: 120
  },
  // 水印高度
  height: {
    type: Number,
    default: 60
  },
  // 水印旋转角度
  rotate: {
    type: Number,
    default: -22
  },
  // 水印字体大小
  fontSize: {
    type: Number,
    default: 14
  },
  // 水印字体颜色
  fontColor: {
    type: String,
    default: 'rgba(0, 0, 0, 0.15)'
  },
  // 水印字体
  fontFamily: {
    type: String,
    default: 'sans-serif'
  },
  // 水印透明度
  opacity: {
    type: Number,
    default: 1
  },
  // 水印z-index
  zIndex: {
    type: Number,
    default: 9
  },
  // 水印背景色
  backgroundColor: {
    type: String,
    default: 'transparent'
  },
  // 水印是否可见
  visible: {
    type: Boolean,
    default: true
  }
})

// 定义响应式数据
const watermarkRef = ref(null)
const watermarkCanvasRef = ref(null)
const watermarkVisible = ref(props.visible)

// 计算水印容器样式
const watermarkStyle = computed(() => ({
  position: 'relative',
  overflow: 'hidden'
}))

// 计算画布样式
const canvasStyle = computed(() => ({
  position: 'absolute',
  top: 0,
  left: 0,
  width: '100%',
  height: '100%',
  background: `url(${createWatermark()}) repeat`,
  zIndex: props.zIndex,
  opacity: props.opacity,
  pointerEvents: 'none'
}))

// 创建水印
const createWatermark = () => {
  // 创建canvas元素
  const canvas = document.createElement('canvas')
  const ctx = canvas.getContext('2d')
  
  // 设置canvas尺寸
  canvas.width = props.width
  canvas.height = props.height
  
  // 设置旋转中心点
  const centerX = canvas.width / 2
  const centerY = canvas.height / 2
  
  // 清除画布
  ctx.clearRect(0, 0, canvas.width, canvas.height)
  
  // 保存当前状态
  ctx.save()
  
  // 移动到中心点并旋转
  ctx.translate(centerX, centerY)
  ctx.rotate((props.rotate * Math.PI) / 180)
  
  // 设置字体样式
  ctx.font = `${props.fontSize}px ${props.fontFamily}`
  ctx.fillStyle = props.fontColor
  ctx.textAlign = 'center'
  ctx.textBaseline = 'middle'
  
  // 绘制文本
  ctx.fillText(props.content, 0, 0)
  
  // 恢复状态
  ctx.restore()
  
  // 返回base64图片
  return canvas.toDataURL()
}

// 监听属性变化并重新创建水印
watch([() => props.content, () => props.width, () => props.height, () => props.rotate, () => props.fontSize, () => props.fontColor, () => props.fontFamily], () => {
  if (watermarkCanvasRef.value) {
    watermarkCanvasRef.value.style.background = `url(${createWatermark()}) repeat`
  }
})

// 监听可见性变化
watch(() => props.visible, (newVal) => {
  watermarkVisible.value = newVal
})

// 组件挂载时创建水印
onMounted(() => {
  if (watermarkCanvasRef.value) {
    watermarkCanvasRef.value.style.background = `url(${createWatermark()}) repeat`
  }
})
</script>

<style lang="scss" scoped>
// 水印样式可以根据需要自定义
.watermark-canvas {
  // 确保水印不会影响布局
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}
</style>