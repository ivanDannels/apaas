<template>
  <div class="result" :class="resultClass">
    <div class="result-icon">
      <slot name="icon">
        <el-icon :size="iconSize" :color="iconColor">
          <component :is="iconComponent" />
        </el-icon>
      </slot>
    </div>
    
    <div class="result-title" v-if="title || $slots.title">
      <slot name="title">
        <h3>{{ title }}</h3>
      </slot>
    </div>
    
    <div class="result-subtitle" v-if="subtitle || $slots.subtitle">
      <slot name="subtitle">
        <p>{{ subtitle }}</p>
      </slot>
    </div>
    
    <div class="result-extra" v-if="$slots.extra">
      <slot name="extra"></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { SuccessFilled, WarningFilled, CircleCloseFilled, InfoFilled } from '@element-plus/icons-vue'

// 定义属性
const props = defineProps({
  // 结果类型
  type: {
    type: String,
    default: 'info', // success / warning / error / info
    validator: (value) => ['success', 'warning', 'error', 'info'].includes(value)
  },
  // 标题
  title: {
    type: String,
    default: ''
  },
  // 副标题
  subtitle: {
    type: String,
    default: ''
  },
  // 图标尺寸
  iconSize: {
    type: [String, Number],
    default: 64
  },
  // 自定义图标组件
  icon: {
    type: [String, Object],
    default: ''
  }
})

// 结果类名
const resultClass = computed(() => {
  return [
    'el-result',
    `el-result--${props.type}`
  ]
})

// 图标颜色
const iconColor = computed(() => {
  const colorMap = {
    success: '#67C23A',
    warning: '#E6A23C',
    error: '#F56C6C',
    info: '#909399'
  }
  return colorMap[props.type] || '#909399'
})

// 图标组件
const iconComponent = computed(() => {
  // 如果有自定义图标，则使用自定义图标
  if (props.icon) {
    return props.icon
  }
  
  // 根据类型返回默认图标
  const iconMap = {
    success: SuccessFilled,
    warning: WarningFilled,
    error: CircleCloseFilled,
    info: InfoFilled
  }
  
  return iconMap[props.type] || InfoFilled
})
</script>

<style lang="scss" scoped>
.result {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 40px 20px;
  box-sizing: border-box;
  
  .result-icon {
    margin-bottom: 20px;
  }
  
  .result-title {
    margin-bottom: 10px;
    
    h3 {
      font-size: 20px;
      font-weight: 500;
      color: var(--el-text-color-primary);
      margin: 0;
    }
  }
  
  .result-subtitle {
    margin-bottom: 20px;
    
    p {
      font-size: 14px;
      color: var(--el-text-color-secondary);
      margin: 0;
    }
  }
  
  .result-extra {
    :deep(.el-button) {
      margin: 0 5px;
    }
  }
}
</style>