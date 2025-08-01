<template>
  <el-scrollbar
    ref="scrollbarRef"
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
    <div
      ref="listRef"
      :style="listStyle"
    >
      <div
        v-for="item in visibleData"
        :key="item[keyProp]"
        :style="itemStyle"
      >
        <slot :item="item"></slot>
      </div>
    </div>
  </el-scrollbar>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted, watch } from 'vue'

// 定义属性
const props = defineProps({
  // 数据源
  data: {
    type: Array,
    default: () => []
  },
  // 每项的高度
  itemHeight: {
    type: Number,
    default: 50
  },
  // 可视区域高度
  height: {
    type: Number,
    default: 300
  },
  // 每项的唯一标识属性名
  keyProp: {
    type: String,
    default: 'id'
  },
  // Scrollbar 属性
  maxHeight: {
    type: [String, Number],
    default: ''
  },
  native: {
    type: Boolean,
    default: false
  },
  wrapStyle: {
    type: [String, Object],
    default: ''
  },
  wrapClass: {
    type: [String, Object],
    default: ''
  },
  viewStyle: {
    type: [String, Object],
    default: ''
  },
  viewClass: {
    type: [String, Object],
    default: ''
  },
  noresize: {
    type: Boolean,
    default: false
  },
  tag: {
    type: String,
    default: 'div'
  },
  always: {
    type: Boolean,
    default: false
  },
  minSize: {
    type: Number,
    default: 20
  }
})

// 定义响应式数据
const scrollbarRef = ref(null)
const listRef = ref(null)
const scrollTop = ref(0)

// 计算可见数据
const visibleData = computed(() => {
  const start = Math.floor(scrollTop.value / props.itemHeight)
  const visibleCount = Math.ceil(props.height / props.itemHeight)
  return props.data.slice(start, start + visibleCount)
})

// 计算列表样式
const listStyle = computed(() => ({
  height: `${props.data.length * props.itemHeight}px`,
  position: 'relative'
}))

// 计算项样式
const itemStyle = computed(() => ({
  position: 'absolute',
  top: 0,
  height: `${props.itemHeight}px`,
  width: '100%'
}))

// 监听滚动事件
const handleScroll = () => {
  if (scrollbarRef.value) {
    scrollTop.value = scrollbarRef.value.wrapRef.scrollTop
  }
}

// 监听数据变化
watch(() => props.data, () => {
  // 数据变化时重置滚动位置
  if (scrollbarRef.value) {
    scrollbarRef.value.wrapRef.scrollTop = 0
    scrollTop.value = 0
  }
})

// 组件挂载时添加滚动事件监听
onMounted(() => {
  if (scrollbarRef.value) {
    scrollbarRef.value.wrapRef.addEventListener('scroll', handleScroll)
  }
})

// 组件卸载时移除滚动事件监听
onUnmounted(() => {
  if (scrollbarRef.value) {
    scrollbarRef.value.wrapRef.removeEventListener('scroll', handleScroll)
  }
})
</script>

<style lang="scss" scoped>
// 虚拟列表样式可以根据需要自定义
</style>