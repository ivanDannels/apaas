<template>
  <el-space
    ref="spaceRef"
    :size="size"
    :direction="direction"
    :alignment="alignment"
    :wrap="wrap"
    :fill="fill"
    :fill-ratio="fillRatio"
    :spacer="spacer"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-space>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { SpaceInstance } from 'element-plus'

// 定义属性
interface Props {
  // 间距大小
  size?: 'small' | 'default' | 'large' | number | [number, number]
  // 排列方向
  direction?: 'horizontal' | 'vertical'
  // 对齐方式
  alignment?: 'flex-start' | 'flex-end' | 'center'
  // 是否自动换行
  wrap?: boolean
  // 是否填充整个容器
  fill?: boolean
  // 填充比例
  fillRatio?: number
  // 间隔符
  spacer?: string | Record<string, any>
}

const props = withDefaults(defineProps<Props>(), {
  size: 'small',
  direction: 'horizontal',
  alignment: 'center',
  wrap: false,
  fill: false,
  fillRatio: 100,
  spacer: ''
})

// 定义响应式数据
const spaceRef = ref<SpaceInstance | null>(null)
</script>

<style lang="scss" scoped>
// 空间组件样式可以根据需要自定义
</style>