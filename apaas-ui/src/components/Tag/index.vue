<template>
  <el-tag
    ref="tagRef"
    :type="type"
    :closable="closable"
    :disable-transitions="disableTransitions"
    :hit="hit"
    :color="color"
    :size="tagSize"
    :effect="effect"
    :round="round"
    v-bind="$attrs"
    @close="handleClose"
    @click="handleClick"
  >
    <slot></slot>
  </el-tag>
</template>

<script setup lang="ts">
import { ref, computed, inject } from 'vue'
import type { TagInstance } from 'element-plus'

// 定义属性
interface Props {
  // 类型
  type?: '' | 'success' | 'info' | 'warning' | 'danger'
  // 是否可关闭
  closable?: boolean
  // 是否禁用渐变动画
  disableTransitions?: boolean
  // 是否有边框描边
  hit?: boolean
  // 背景色
  color?: string
  // 尺寸
  size?: string
  // 主题
  effect?: 'dark' | 'light' | 'plain'
  // 是否为圆形标签
  round?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  type: '',
  closable: false,
  disableTransitions: false,
  hit: false,
  color: '',
  size: '',
  effect: 'light',
  round: false
})

// 定义事件
interface Emits {
  (e: 'close', event: Event): void
  (e: 'click', event: Event): void
}

const emit = defineEmits<Emits>()

// 注入父级表单属性
const formProps = inject('formProps', {} as any)

// 定义响应式数据
const tagRef = ref<TagInstance | null>(null)

// 计算属性：标签尺寸
const tagSize = computed(() => {
  return props.size || formProps.size || 'default'
})

// 处理关闭事件
const handleClose = (event: Event) => {
  emit('close', event)
}

// 处理点击事件
const handleClick = (event: Event) => {
  emit('click', event)
}
</script>

<style lang="scss" scoped>
// 标签样式可以根据需要自定义
</style>