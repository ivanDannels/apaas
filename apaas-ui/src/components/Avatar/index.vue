<template>
  <el-avatar
    ref="avatarRef"
    :icon="icon"
    :size="avatarSize"
    :shape="shape"
    :src="src"
    :fit="fit"
    :alt="alt"
    :src-set="srcSet"
    :error="error"
    v-bind="$attrs"
    @error="handleError"
  >
    <slot></slot>
  </el-avatar>
</template>

<script setup lang="ts">
import { ref, computed, inject } from 'vue'
import type { AvatarInstance } from 'element-plus'

// 定义属性
const props = defineProps({
  // 设置头像的图标类型
  icon: {
    type: String,
    default: ''
  },
  // 设置头像的大小
  size: {
    type: [Number, String],
    default: 'default', // number / large / default / small
    validator: (value: number | string) => {
      if (typeof value === 'number') return true
      return ['large', 'default', 'small'].includes(value as string)
    }
  },
  // 设置头像的形状
  shape: {
    type: String,
    default: 'circle', // circle / square
    validator: (value: string) => ['circle', 'square'].includes(value)
  },
  // 图片头像的资源地址
  src: {
    type: String,
    default: ''
  },
  // 当展示类型为图片的时候，设置图片如何适应容器框
  fit: {
    type: String,
    default: 'cover', // fill / contain / cover / none / scale-down
    validator: (value: string) => ['fill', 'contain', 'cover', 'none', 'scale-down'].includes(value)
  },
  // 原生 alt 属性
  alt: {
    type: String,
    default: ''
  },
  // 原生 srcset 属性
  srcSet: {
    type: String,
    default: ''
  },
  // 图片加载失败时的回调函数
  error: {
    type: Function,
    default: null
  }
})

type AvatarEmits = {
  error: [event: Event]
}

// 定义事件
const emit = defineEmits<AvatarEmits>()

// 注入父级表单属性
const formProps: any = inject('formProps', {})

// 定义响应式数据
const avatarRef = ref<AvatarInstance | null>(null)

// 计算属性：头像尺寸
const avatarSize = computed(() => {
  return props.size || formProps.size || 'default'
})

// 处理图片加载失败
const handleError = (event: Event) => {
  emit('error', event)
}
</script>

<style lang="scss" scoped>
// 头像样式可以根据需要自定义
</style>