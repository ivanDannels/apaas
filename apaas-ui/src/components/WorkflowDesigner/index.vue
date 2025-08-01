<template>
  <div class="workflow-designer">
    <div ref="containerRef" class="container"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue'
import LogicFlow from '@logicflow/core'
import '@logicflow/core/dist/style/index.css'
import '@logicflow/extension/lib/style/index.css'

// 引入扩展
import { Snapshot, BpmnElement, Menu, DndPanel, SelectionSelect } from '@logicflow/extension'

// 定义属性
const props = defineProps({
  data: {
    type: Object,
    default: () => ({})
  },
  config: {
    type: Object,
    default: () => ({})
  }
})

// 定义事件
const emit = defineEmits(['change', 'node:click', 'edge:click', 'blank:click'])

// 定义响应式数据
const containerRef = ref(null)
let lf = null

// 默认配置
const defaultConfig = {
  grid: true,
  background: {
    color: '#F0F0F0'
  },
  keyboard: {
    enabled: true
  },
  mouseWheel: {
    enabled: true
  },
  style: {
    edge: {
      stroke: '#5F6368'
    }
  }
}

// 初始化LogicFlow
const initLogicFlow = () => {
  if (!containerRef.value) return
  
  // 合并配置
  const config = Object.assign({}, defaultConfig, props.config, {
    container: containerRef.value
  })
  
  // 创建LogicFlow实例
  lf = new LogicFlow(config)
  
  // 注册扩展
  lf.register(Snapshot)
  lf.register(Menu)
  lf.register(DndPanel)
  lf.register(SelectionSelect)
  
  // 注册BPMN元素
  BpmnElement.registerAll(lf)
  
  // 注册事件监听
  registerEvents()
  
  // 设置数据
  if (props.data) {
    lf.render(props.data)
  }
}

// 注册事件监听
const registerEvents = () => {
  if (!lf) return
  
  // 图数据变化事件
  lf.on('graph:change', ({ data }) => {
    emit('change', data)
  })
  
  // 节点点击事件
  lf.on('node:click', ({ data }) => {
    emit('node:click', data)
  })
  
  // 边点击事件
  lf.on('edge:click', ({ data }) => {
    emit('edge:click', data)
  })
  
  // 空白区域点击事件
  lf.on('blank:click', ({ e, position }) => {
    emit('blank:click', { e, position })
  })
}

// 获取LogicFlow实例
const getLf = () => {
  return lf
}

// 获取图数据
const getGraphData = () => {
  return lf ? lf.getGraphData() : null
}

// 设置图数据
const setGraphData = (data) => {
  if (lf) {
    lf.render(data)
  }
}

// 导出图片
const exportImage = () => {
  if (lf) {
    lf.getSnapshot()
  }
}

// 导出数据
const exportData = () => {
  if (lf) {
    return lf.getGraphData()
  }
  return null
}

// 组件挂载后初始化
onMounted(() => {
  nextTick(() => {
    initLogicFlow()
  })
})

// 组件卸载前销毁
onBeforeUnmount(() => {
  if (lf) {
    lf.dispose()
  }
})

// 定义暴露给父组件的方法
defineExpose({
  getLf,
  getGraphData,
  setGraphData,
  exportImage,
  exportData
})
</script>

<style lang="scss" scoped>
.workflow-designer {
  width: 100%;
  height: 100%;
  
  .container {
    width: 100%;
    height: 100%;
  }
}
</style>