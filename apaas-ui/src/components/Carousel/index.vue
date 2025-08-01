<template>
  <el-carousel
    ref="carouselRef"
    :height="height"
    :initial-index="initialIndex"
    :trigger="trigger"
    :autoplay="autoplay"
    :interval="interval"
    :indicator-position="indicatorPosition"
    :arrow="arrow"
    :type="type"
    :loop="loop"
    :direction="direction"
    :pause-on-hover="pauseOnHover"
    :motion-blur="motionBlur"
    v-bind="$attrs"
    @change="handleChange"
  >
    <slot></slot>
  </el-carousel>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 定义属性
const props = defineProps({
  // 走马灯的高度
  height="{
    type: String,
    default: '200px'
  },
  // 初始状态激活的幻灯片的索引，从 0 开始
  initialIndex: {
    type: Number,
    default: 0
  },
  // 指示器的触发方式
  trigger: {
    type: String,
    default: 'hover', // hover / click
    validator: (value) => ['hover', 'click'].includes(value)
  },
  // 是否自动切换
  autoplay: {
    type: Boolean,
    default: true
  },
  // 自动切换的时间间隔，单位为毫秒
  interval: {
    type: Number,
    default: 3000
  },
  // 指示器的位置
  indicatorPosition: {
    type: String,
    default: 'outside', // outside / none
    validator: (value) => ['outside', 'none'].includes(value)
  },
  // 切换箭头的显示时机
  arrow: {
    type: String,
    default: 'hover', // always / hover / never
    validator: (value) => ['always', 'hover', 'never'].includes(value)
  },
  // 走马灯的类型
  type: {
    type: String,
    default: '', // card
    validator: (value) => ['', 'card'].includes(value)
  },
  // 是否循环显示
  loop: {
    type: Boolean,
    default: true
  },
  // 走马灯的展示方向
  direction: {
    type: String,
    default: 'horizontal', // horizontal / vertical
    validator: (value) => ['horizontal', 'vertical'].includes(value)
  },
  // 鼠标悬浮时暂停自动切换
  pauseOnHover: {
    type: Boolean,
    default: true
  },
  // 是否开启过渡动效
  motionBlur: {
    type: Boolean,
    default: false
  }
})

// 定义响应式数据
const carouselRef = ref(null)

// 定义事件
const emit = defineEmits(['change'])

// 处理幻灯片切换事件
const handleChange = (current, prev) => {
  emit('change', current, prev)
}
</script>

<style lang="scss" scoped>
// 走马灯样式可以根据需要自定义
</style>