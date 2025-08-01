<template>
  <div class="card" :class="cardClass" :style="cardStyle">
    <!-- 卡片头部 -->
    <div 
      v-if="showHeader || $slots.header" 
      class="card-header" 
      :class="headerClass"
      :style="headerStyle"
    >
      <slot name="header">
        <div class="card-header-content">
          <div v-if="title" class="card-title">{{ title }}</div>
          <div v-if="subtitle" class="card-subtitle">{{ subtitle }}</div>
        </div>
        <div v-if="showTools || $slots.tools" class="card-tools">
          <slot name="tools">
            <el-button 
              v-if="showRefresh" 
              :icon="Refresh" 
              :size="toolSize" 
              @click="handleRefresh"
              circle
            />
            <el-button 
              v-if="showFullscreen" 
              :icon="isFullscreen ? FullScreenReturn : FullScreen" 
              :size="toolSize" 
              @click="toggleFullscreen"
              circle
            />
            <el-button 
              v-if="showCollapse" 
              :icon="isCollapsed ? ArrowDown : ArrowUp" 
              :size="toolSize" 
              @click="toggleCollapse"
              circle
            />
          </slot>
        </div>
      </slot>
    </div>
    
    <!-- 卡片主体 -->
    <div 
      v-show="!isCollapsed" 
      class="card-body" 
      :class="bodyClass"
      :style="bodyStyle"
    >
      <slot></slot>
    </div>
    
    <!-- 卡片底部 -->
    <div 
      v-if="$slots.footer" 
      class="card-footer" 
      :class="footerClass"
      :style="footerStyle"
    >
      <slot name="footer"></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { Refresh, FullScreen, FullScreenReturn, ArrowUp, ArrowDown } from '@element-plus/icons-vue'

// 定义属性
const props = defineProps({
  // 卡片标题
  title: {
    type: String,
    default: ''
  },
  // 卡片副标题
  subtitle: {
    type: String,
    default: ''
  },
  // 是否显示头部
  showHeader: {
    type: Boolean,
    default: true
  },
  // 是否显示工具栏
  showTools: {
    type: Boolean,
    default: true
  },
  // 是否显示刷新按钮
  showRefresh: {
    type: Boolean,
    default: false
  },
  // 是否显示全屏按钮
  showFullscreen: {
    type: Boolean,
    default: false
  },
  // 是否显示折叠按钮
  showCollapse: {
    type: Boolean,
    default: false
  },
  // 工具按钮大小
  toolSize: {
    type: String,
    default: 'small'
  },
  // 是否可折叠
  collapsible: {
    type: Boolean,
    default: false
  },
  // 是否默认折叠
  collapsed: {
    type: Boolean,
    default: false
  },
  // 是否可全屏
  fullscreenable: {
    type: Boolean,
    default: false
  },
  // 卡片阴影
  shadow: {
    type: String,
    default: 'hover', // always / hover / never
    validator: (value) => ['always', 'hover', 'never'].includes(value)
  },
  // 卡片主题
  theme: {
    type: String,
    default: 'default', // default / primary / success / warning / danger / info
    validator: (value) => ['default', 'primary', 'success', 'warning', 'danger', 'info'].includes(value)
  },
  // 卡片宽度
  width: {
    type: [String, Number],
    default: '100%'
  },
  // 卡片高度
  height: {
    type: [String, Number],
    default: 'auto'
  },
  // 最小高度
  minHeight: {
    type: [String, Number],
    default: undefined
  },
  // 是否有边框
  bordered: {
    type: Boolean,
    default: true
  },
  // 头部类名
  headerClass: {
    type: [String, Array, Object],
    default: ''
  },
  // 头部样式
  headerStyle: {
    type: [String, Array, Object],
    default: ''
  },
  // 主体类名
  bodyClass: {
    type: [String, Array, Object],
    default: ''
  },
  // 主体样式
  bodyStyle: {
    type: [String, Array, Object],
    default: ''
  },
  // 底部类名
  footerClass: {
    type: [String, Array, Object],
    default: ''
  },
  // 底部样式
  footerStyle: {
    type: [String, Array, Object],
    default: ''
  }
})

// 定义事件
const emit = defineEmits(['refresh', 'fullscreen-change', 'collapse-change'])

// 定义响应式数据
const isCollapsed = ref(props.collapsed)
const isFullscreen = ref(false)
const originalParent = ref(null)
const originalStyle = ref({})

// 卡片类名
const cardClass = computed(() => {
  return [
    'el-card',
    `el-card--${props.shadow}`,
    `el-card--${props.theme}`,
    {
      'is-collapsible': props.collapsible,
      'is-fullscreen': isFullscreen.value,
      'is-bordered': props.bordered
    }
  ]
})

// 卡片样式
const cardStyle = computed(() => {
  return {
    width: typeof props.width === 'number' ? props.width + 'px' : props.width,
    height: isFullscreen.value ? '100%' : (typeof props.height === 'number' ? props.height + 'px' : props.height),
    minHeight: props.minHeight ? (typeof props.minHeight === 'number' ? props.minHeight + 'px' : props.minHeight) : undefined
  }
})

// 处理刷新
const handleRefresh = () => {
  emit('refresh')
}

// 切换全屏
const toggleFullscreen = () => {
  if (!props.fullscreenable) return
  
  isFullscreen.value = !isFullscreen.value
  
  if (isFullscreen.value) {
    enterFullscreen()
  } else {
    exitFullscreen()
  }
  
  emit('fullscreen-change', isFullscreen.value)
}

// 进入全屏
const enterFullscreen = () => {
  const card = document.querySelector('.card')
  if (!card) return
  
  // 保存原始父级和样式
  originalParent.value = card.parentNode
  originalStyle.value = {
    position: card.style.position,
    width: card.style.width,
    height: card.style.height,
    top: card.style.top,
    left: card.style.left,
    zIndex: card.style.zIndex
  }
  
  // 移动到body并设置全屏样式
  document.body.appendChild(card)
  card.style.position = 'fixed'
  card.style.width = '100%'
  card.style.height = '100%'
  card.style.top = '0'
  card.style.left = '0'
  card.style.zIndex = '9999'
}

// 退出全屏
const exitFullscreen = () => {
  const card = document.querySelector('.card')
  if (!card || !originalParent.value) return
  
  // 恢复原始位置和样式
  originalParent.value.appendChild(card)
  card.style.position = originalStyle.value.position || ''
  card.style.width = originalStyle.value.width || ''
  card.style.height = originalStyle.value.height || ''
  card.style.top = originalStyle.value.top || ''
  card.style.left = originalStyle.value.left || ''
  card.style.zIndex = originalStyle.value.zIndex || ''
}

// 切换折叠
const toggleCollapse = () => {
  if (!props.collapsible) return
  
  isCollapsed.value = !isCollapsed.value
  emit('collapse-change', isCollapsed.value)
}

// 组件卸载前退出全屏
onBeforeUnmount(() => {
  if (isFullscreen.value) {
    exitFullscreen()
  }
})
</script>

<style lang="scss" scoped>
.card {
  width: v-bind('props.width');
  height: v-bind('props.height');
  
  &.el-card--always {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  
  &.el-card--hover:hover {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  
  &.el-card--never {
    box-shadow: none;
  }
  
  &.el-card--primary {
    border-color: var(--el-color-primary);
    
    .card-header {
      background-color: var(--el-color-primary-light-9);
      color: var(--el-color-primary);
    }
  }
  
  &.el-card--success {
    border-color: var(--el-color-success);
    
    .card-header {
      background-color: var(--el-color-success-light-9);
      color: var(--el-color-success);
    }
  }
  
  &.el-card--warning {
    border-color: var(--el-color-warning);
    
    .card-header {
      background-color: var(--el-color-warning-light-9);
      color: var(--el-color-warning);
    }
  }
  
  &.el-card--danger {
    border-color: var(--el-color-danger);
    
    .card-header {
      background-color: var(--el-color-danger-light-9);
      color: var(--el-color-danger);
    }
  }
  
  &.el-card--info {
    border-color: var(--el-color-info);
    
    .card-header {
      background-color: var(--el-color-info-light-9);
      color: var(--el-color-info);
    }
  }
  
  &.is-bordered {
    border: 1px solid var(--el-border-color-light);
  }
  
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 20px;
    border-bottom: 1px solid var(--el-border-color-light);
    box-sizing: border-box;
    
    .card-header-content {
      .card-title {
        font-size: 16px;
        font-weight: 500;
        color: var(--el-text-color-primary);
      }
      
      .card-subtitle {
        font-size: 12px;
        color: var(--el-text-color-secondary);
        margin-top: 4px;
      }
    }
    
    .card-tools {
      display: flex;
      align-items: center;
      gap: 8px;
    }
  }
  
  .card-body {
    padding: 20px;
  }
  
  .card-footer {
    padding: 12px 20px;
    border-top: 1px solid var(--el-border-color-light);
    box-sizing: border-box;
  }
}
</style>