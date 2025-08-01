<template>
  <el-dialog
    ref="dialogRef"
    v-model="visible"
    :title="title"
    :width="width"
    :fullscreen="fullscreen"
    :modal="modal"
    :lock-scroll="lockScroll"
    :open-delay="openDelay"
    :close-delay="closeDelay"
    :top="top"
    :modal-append-to-body="modalAppendToBody"
    :append-to-body="appendToBody"
    :destroy-on-close="destroyOnClose"
    v-bind="$attrs"
    @open="handleOpen"
    @opened="handleOpened"
    @close="handleClose"
    @closed="handleClosed"
  >
    <!-- 头部插槽 -->
    <template #header="scope">
      <div 
        class="draggable-dialog__header" 
        @mousedown="handleMouseDown"
      >
        <slot name="header" v-bind="scope"></slot>
        <div class="draggable-dialog__title">{{ title }}</div>
      </div>
    </template>
    
    <!-- 默认内容插槽 -->
    <slot></slot>
    
    <!-- 底部插槽 -->
    <template #footer v-if="$slots.footer">
      <slot name="footer"></slot>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'

// 定义属性
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  title: {
    type: String,
    default: ''
  },
  width: {
  	type: [String, Number],
    default: '50%'
  },
  fullscreen: {
    type: Boolean,
    default: false
  },
  modal: {
    type: Boolean,
    default: true
  },
  lockScroll: {
    type: Boolean,
    default: true
  },
  openDelay: {
    type: Number,
    default: 0
  },
  closeDelay: {
    type: Number,
    default: 0
  },
  top: {
    type: String,
    default: '15vh'
  },
  modalAppendToBody: {
    type: Boolean,
    default: true
  },
  appendToBody: {
    type: Boolean,
    default: true
  },
  destroyOnClose: {
    type: Boolean,
    default: false
  }
})

// 定义事件
const emit = defineEmits(['update:modelValue', 'open', 'opened', 'close', 'closed'])

// 定义响应式数据
const dialogRef = ref(null)
const visible = ref(props.modelValue)
let isDragging = false
let startX = 0
let startY = 0
let startLeft = 0
let startTop = 0
let dialogElement = null

// 计算属性
const dialogStyle = computed(() => {
  return {
    top: props.top
  }
})

// 处理鼠标按下事件
const handleMouseDown = (e) => {
  // 如果是全屏状态，则不允许拖拽
  if (props.fullscreen) return
  
  // 获取对话框元素
  dialogElement = dialogRef.value?.dialogRef
  if (!dialogElement) return
  
  // 设置拖拽状态
  isDragging = true
  startX = e.clientX
  startY = e.clientY
  
  // 获取对话框当前位置
  const rect = dialogElement.getBoundingClientRect()
  startLeft = rect.left
  startTop = rect.top
  
  // 添加事件监听
  document.addEventListener('mousemove', handleMouseMove)
  document.addEventListener('mouseup', handleMouseUp)
  
  // 防止文本选中
  e.preventDefault()
}

// 处理鼠标移动事件
const handleMouseMove = (e) => {
  if (!isDragging || !dialogElement) return
  
  // 计算偏移量
  const offsetX = e.clientX - startX
  const offsetY = e.clientY - startY
  
  // 计算新位置
  const newLeft = startLeft + offsetX
  const newTop = startTop + offsetY
  
  // 设置对话框位置
  dialogElement.style.left = newLeft + 'px'
  dialogElement.style.top = newTop + 'px'
}

// 处理鼠标松开事件
const handleMouseUp = () => {
  isDragging = false
  
  // 移除事件监听
  document.removeEventListener('mousemove', handleMouseMove)
  document.removeEventListener('mouseup', handleMouseUp)
}

// 处理打开事件
const handleOpen = () => {
  emit('open')
}

// 处理打开完成事件
const handleOpened = () => {
  emit('opened')
}

// 处理关闭事件
const handleClose = () => {
  visible.value = false
  emit('update:modelValue', false)
  emit('close')
}

// 处理关闭完成事件
const handleClosed = () => {
  emit('closed')
}

// 监听值变化
watch(
  () => props.modelValue,
  (val) => {
    visible.value = val
  }
)

// 监听可见性变化
watch(visible, (val) => {
  if (!val) {
    // 关闭时重置对话框位置
    nextTick(() => {
      if (dialogElement) {
        dialogElement.style.left = ''
        dialogElement.style.top = ''
      }
    })
  }
})

// 组件卸载前清理事件监听
onBeforeUnmount(() => {
  document.removeEventListener('mousemove', handleMouseMove)
  document.removeEventListener('mouseup', handleMouseUp)
})
</script>

<style lang="scss" scoped>
.draggable-dialog__header {
  display: flex;
  align-items: center;
  cursor: move;
  user-select: none;
  
  .draggable-dialog__title {
    flex: 1;
    padding-left: 10px;
  }
}
</style>