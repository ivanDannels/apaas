<template>
  <el-scrollbar
    ref="scrollbarRef"
    :height="height"
    :max-height="maxHeight"
    :native="native"
    :wrap-style="wrapStyle"
    :wrap-class="wrapClass"
    :view-style="viewStyle"
    :view-class="viewClass"
    :noresize="noresize"
    :tag="tag"
    :always="always"
    :min-size="minSize"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-scrollbar>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { ScrollbarInstance } from 'element-plus'

// 定义属性
interface Props {
  // 滚动条高度
  height?: string | number
  // 滚动条最大高度
  maxHeight?: string | number
  // 是否使用原生滚动条样式
  native?: boolean
  // 包装容器的自定义样式
  wrapStyle?: string | Record<string, any>
  // 包装容器的自定义类名
  wrapClass?: string | Record<string, any>
  // 视图的自定义样式
  viewStyle?: string | Record<string, any>
  // 视图的自定义类名
  viewClass?: string | Record<string, any>
  // 是否不响应容器尺寸变化
  noresize?: boolean
  // 视图的元素标签
  tag?: string
  // 滚动条是否总是显示
  always?: boolean
  // 滚动条最小尺寸
  minSize?: number
}

const props = withDefaults(defineProps<Props>(), {
  height: '',
  maxHeight: '',
  native: false,
  wrapStyle: '',
  wrapClass: '',
  viewStyle: '',
  viewClass: '',
  noresize: false,
  tag: 'div',
  always: false,
  minSize: 20
})

// 定义响应式数据
const scrollbarRef = ref<ScrollbarInstance | null>(null)

// 暴露方法
interface ScrollbarExpose {
  setScrollTop: (scrollTop: number) => void
  setScrollLeft: (scrollLeft: number) => void
  update: () => void
  wrapRef: HTMLElement | null
}

defineExpose<ScrollbarExpose>({
  // 设置滚动条位置
  setScrollTop: (scrollTop: number) => {
    if (scrollbarRef.value) {
      scrollbarRef.value.setScrollTop(scrollTop)
    }
  },
  // 设置滚动条位置
  setScrollLeft: (scrollLeft: number) => {
    if (scrollbarRef.value) {
      scrollbarRef.value.setScrollLeft(scrollLeft)
    }
  },
  // 更新滚动条
  update: () => {
    if (scrollbarRef.value) {
      scrollbarRef.value.update()
    }
  },
  // 获取滚动包装器引用
  get wrapRef() {
    return scrollbarRef.value?.wrapRef || null
  }
})
</script>

<style lang="scss" scoped>
// 滚动条样式可以根据需要自定义
</style>