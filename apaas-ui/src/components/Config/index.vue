<template>
  <el-config-provider
    ref="configProviderRef"
    :locale="locale"
    :size="size"
    :button="button"
    :message="message"
    :experimental-features="experimentalFeatures"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-config-provider>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 定义属性
const props = defineProps({
  // 语言包
  locale: {
    type: Object,
    default: () => ({})
  },
  // 全局组件大小
  size: {
    type: String,
    default: 'default', // large / default / small
    validator: (value) => ['large', 'default', 'small'].includes(value)
  },
  // 全局按钮配置
  button: {
    type: Object,
    default: () => ({})
  },
  // 全局消息配置
  message: {
    type: Object,
    default: () => ({})
  },
  // 实验性功能
  experimentalFeatures: {
    type: Array,
    default: () => []
  }
})

// 定义响应式数据
const configProviderRef = ref(null)
</script>

<style lang="scss" scoped>
// 配置组件样式可以根据需要自定义
</style>