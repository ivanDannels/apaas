<template>
  <div ref="chartRef" class="chart" :style="chartStyle"></div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount, watch, nextTick } from 'vue'
import * as echarts from 'echarts'

// 定义属性
const props = defineProps({
  // 图表选项
  option: {
    type: Object,
    required: true
  },
  // 图表宽度
  width: {
    type: [String, Number],
    default: '100%'
  },
  // 图表高度
  height: {
    type: [String, Number],
    default: '400px'
  },
  // 是否自动调整大小
  autoResize: {
    type: Boolean,
    default: true
  },
  // 图表主题
  theme: {
    type: [String, Object],
    default: ''
  }
})

// 定义事件
const emit = defineEmits(['click', 'dblclick', 'mouseover', 'mouseout'])

// 定义响应式数据
const chartRef = ref(null)
let chart = null

// 图表样式
const chartStyle = computed(() => {
  return {
    width: typeof props.width === 'number' ? props.width + 'px' : props.width,
    height: typeof props.height === 'number' ? props.height + 'px' : props.height
  }
})

// 初始化图表
const initChart = () => {
  if (!chartRef.value) return
  
  // 销毁已存在的图表
  if (chart) {
    chart.dispose()
  }
  
  // 创建图表实例
  chart = echarts.init(chartRef.value, props.theme)
  
  // 设置图表选项
  chart.setOption(props.option)
  
  // 注册事件监听
  registerEvents()
  
  // 添加窗口大小调整监听
  if (props.autoResize) {
    window.addEventListener('resize', resizeChart)
  }
}

// 注册事件监听
const registerEvents = () => {
  if (!chart) return
  
  // 点击事件
  chart.on('click', (params) => {
    emit('click', params)
  })
  
  // 双击事件
  chart.on('dblclick', (params) => {
    emit('dblclick', params)
  })
  
  // 鼠标移入事件
  chart.on('mouseover', (params) => {
    emit('mouseover', params)
  })
  
  // 鼠标移出事件
  chart.on('mouseout', (params) => {
    emit('mouseout', params)
  })
}

// 调整图表大小
const resizeChart = () => {
  if (chart) {
    chart.resize()
  }
}

// 更新图表选项
const setOption = (option, notMerge = false, replaceMerge = null) => {
  if (chart) {
    chart.setOption(option, notMerge, replaceMerge)
  }
}

// 获取图表实例
const getChart = () => {
  return chart
}

// 监听选项变化
watch(
  () => props.option,
  (val) => {
    if (val) {
      setOption(val)
    }
  },
  { deep: true }
)

// 监听主题变化
watch(
  () => props.theme,
  () => {
    if (chart) {
      initChart()
    }
  }
)

// 组件挂载后初始化图表
onMounted(() => {
  nextTick(() => {
    initChart()
  })
})

// 组件卸载前销毁图表
onBeforeUnmount(() => {
  if (chart) {
    chart.dispose()
  }
  
  // 移除窗口大小调整监听
  if (props.autoResize) {
    window.removeEventListener('resize', resizeChart)
  }
})

// 定义暴露给父组件的方法
defineExpose({
  getChart,
  setOption,
  resize: resizeChart
})
</script>

<style lang="scss" scoped>
.chart {
  width: v-bind('props.width');
  height: v-bind('props.height');
}
</style>