<template>
  <el-steps
    ref="stepsRef"
    :space="space"
    :direction="direction"
    :active="activeStep"
    :process-status="processStatus"
    :finish-status="finishStatus"
    :align-center="alignCenter"
    :simple="simple"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-steps>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import type { StepsInstance } from 'element-plus'

// 定义属性
interface Props {
  // 每个 step 的间距
  space?: number | string
  // 显示方向
  direction?: 'vertical' | 'horizontal'
  // 设置当前激活步骤
  active?: number
  // 设置当前步骤的状态
  processStatus?: 'wait' | 'process' | 'finish' | 'error' | 'success'
  // 设置结束步骤的状态
  finishStatus?: 'wait' | 'process' | 'finish' | 'error' | 'success'
  // 进行居中对齐
  alignCenter?: boolean
  // 是否应用简洁风格
  simple?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  space: '',
  direction: 'horizontal',
  active: 0,
  processStatus: 'process',
  finishStatus: 'finish',
  alignCenter: false,
  simple: false
})

// 定义事件
interface Emits {
  (e: 'update:active', value: number): void
}

const emit = defineEmits<Emits>()

// 定义响应式数据
const stepsRef = ref<StepsInstance | null>(null)
const activeStep = ref<number>(props.active)

// 监听active变化
watch(
  () => props.active,
  (val) => {
    activeStep.value = val
  }
)

// 监听activeStep变化
watch(
  () => activeStep.value,
  (val) => {
    emit('update:active', val)
  }
)

// 定义暴露给父组件的方法
const setActive = (index: number) => {
  activeStep.value = index
}

defineExpose({
  setActive
})
</script>

<style lang="scss" scoped>
// 步骤条样式可以根据需要自定义
</style>