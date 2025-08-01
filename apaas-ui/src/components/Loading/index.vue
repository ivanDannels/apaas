<template>
  <div 
    ref="loadingRef" 
    class="loading" 
    :class="loadingClass" 
    :style="loadingStyle"
    v-if="visible"
  >
    <div class="loading-mask" v-if="mask"></div>
    <div class="loading-spinner">
      <slot>
        <div class="loading-icon">
          <div class="spinner" :class="spinnerClass"></div>
        </div>
        <div class="loading-text" v-if="text">{{ text }}</div>
      </slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'

// 定义属性
interface Props {
  // 是否显示加载
  visible?: boolean
  // 加载文案
  text?: string
  // 加载图标类型
  spinner?: 'default' | 'dots' | 'bars' | 'custom'
  // 是否显示遮罩
  mask?: boolean
  // 遮罩背景色
  background?: string
  // 是否全屏显示
  fullscreen?: boolean
  // 自定义类名
  customClass?: string
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  text: '',
  spinner: 'default',
  mask: true,
  background: 'rgba(0, 0, 0, 0.5)',
  fullscreen: false,
  customClass: ''
})

// 定义响应式数据
const loadingRef = ref<HTMLElement | null>(null)

// 加载类名
const loadingClass = computed(() => {
  return [
    'el-loading',
    `el-loading--${props.spinner}`,
    {
      'is-fullscreen': props.fullscreen,
      'is-mask': props.mask
    },
    props.customClass
  ]
})

// 加载样式
const loadingStyle = computed(() => {
  const style: { [key: string]: string } = {}
  if (props.background) {
    style.backgroundColor = props.background
  }
  return style
})

// 旋转图标类名
const spinnerClass = computed(() => {
  return [
    'spinner-icon',
    `spinner-${props.spinner}`
  ]
})

// 显示加载
const show = () => {
  // 可以通过修改visible属性来控制显示
}

// 隐藏加载
const hide = () => {
  // 可以通过修改visible属性来控制隐藏
}

// 定义暴露给父组件的方法
defineExpose({
  show,
  hide
})
</script>

<style lang="scss" scoped>
.loading {
  position: relative;
  
  &.is-fullscreen {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 9999;
  }
  
  .loading-mask {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: v-bind('props.background');
    opacity: 0.5;
  }
  
  .loading-spinner {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: #ffffff;
    
    .loading-icon {
      .spinner {
        width: 40px;
        height: 40px;
        
        &.spinner-default {
          border: 4px solid #f3f3f3;
          border-top: 4px solid #409eff;
          border-radius: 50%;
          animation: spin 1s linear infinite;
        }
        
        &.spinner-dots {
          display: flex;
          gap: 4px;
          
          &::before,
          &::after {
            content: '';
            width: 10px;
            height: 10px;
            border-radius: 50%;
            background-color: #409eff;
            animation: bounce 1.5s infinite;
          }
          
          &::after {
            animation-delay: 0.5s;
          }
        }
        
        &.spinner-bars {
          display: flex;
          gap: 2px;
          
          &::before,
          &::after,
          & > div {
            content: '';
            width: 6px;
            height: 20px;
            background-color: #409eff;
            border-radius: 2px;
            animation: stretch 1.2s infinite ease-in-out;
          }
          
          &::before {
            animation-delay: -1.2s;
          }
          
          & > div {
            animation-delay: -1.0s;
          }
          
          &::after {
            animation-delay: -0.8s;
          }
        }
      }
    }
    
    .loading-text {
      margin-top: 10px;
      font-size: 14px;
    }
  }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

@keyframes bounce {
  0%, 100% { transform: scale(0); }
  50% { transform: scale(1); }
}

@keyframes stretch {
  0%, 40%, 100% { transform: scaleY(0.4); }
  20% { transform: scaleY(1); }
}
</style>