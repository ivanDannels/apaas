<template>
  <el-tabs
    ref="tabsRef"
    v-model="activeTab"
    :type="type"
    :tab-position="tabPosition"
    :before-leave="beforeLeave"
    :stretch="stretch"
    v-bind="$attrs"
    @tab-click="handleTabClick"
    @tab-remove="handleTabRemove"
    @tab-add="handleTabAdd"
  >
    <slot></slot>
  </el-tabs>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import type { TabsInstance, TabPaneName, TabsPaneContext } from 'element-plus'

// 定义属性
interface Props {
  // 绑定值，选中选项卡的 name
  modelValue?: string | number
  // 风格类型
  type?: '' | 'card' | 'border-card'
  // 选项卡所在位置
  tabPosition?: 'top' | 'right' | 'bottom' | 'left'
  // 切换标签之前的钩子，若返回 false 或者返回 Promise 且被 reject，则阻止切换
  beforeLeave?: ((activeName: TabPaneName, oldActiveName: TabPaneName) => boolean | Promise<boolean>) | null
  // 标签是否拉伸以填充容器
  stretch?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: '',
  type: '',
  tabPosition: 'top',
  beforeLeave: null,
  stretch: false
})

// 定义事件
interface Emits {
  (e: 'update:modelValue', value: string | number): void
  (e: 'tab-click', tab: TabsPaneContext, event: Event): void
  (e: 'tab-remove', name: string | number): void
  (e: 'tab-add'): void
}

const emit = defineEmits<Emits>()

// 定义响应式数据
const tabsRef = ref<TabsInstance | null>(null)
const activeTab = ref<string | number>(props.modelValue)

// 监听modelValue变化
watch(
  () => props.modelValue,
  (val) => {
    activeTab.value = val
  }
)

// 监听activeTab变化
watch(
  () => activeTab.value,
  (val) => {
    emit('update:modelValue', val)
  }
)

// 处理标签页点击
const handleTabClick = (tab: TabsPaneContext, event: Event) => {
  emit('tab-click', tab, event)
}

// 处理标签页移除
const handleTabRemove = (name: string | number) => {
  emit('tab-remove', name)
}

// 处理标签页添加
const handleTabAdd = () => {
  emit('tab-add')
}

// 定义暴露给父组件的方法
const setCurrentName = (name: string | number) => {
  activeTab.value = name
}

defineExpose({
  setCurrentName
})
</script>

<style lang="scss" scoped>
// 选项卡样式可以根据需要自定义
</style>