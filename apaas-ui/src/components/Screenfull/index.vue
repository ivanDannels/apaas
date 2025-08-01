<template>
  <div class="screenfull-container">
    <svg-icon 
      :icon-class="isFullscreen ? 'exit-fullscreen' : 'fullscreen'" 
      @click="toggle" 
      class="screenfull-icon"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import screenfull from 'screenfull'

// 定义响应式数据
const isFullscreen = ref<boolean>(false)

// 切换全屏
const toggle = () => {
  if (!screenfull.isEnabled) {
    ElMessage.warning('您的浏览器不支持全屏功能')
    return false
  }
  screenfull.toggle()
}

// 全屏状态变化
const change = () => {
  isFullscreen.value = screenfull.isFullscreen
}

// 组件挂载时添加事件监听
onMounted(() => {
  if (screenfull.isEnabled) {
    screenfull.on('change', change)
  }
})

// 组件卸载时移除事件监听
onUnmounted(() => {
  if (screenfull.isEnabled) {
    screenfull.off('change', change)
  }
})
</script>

<style lang="scss" scoped>
.screenfull-container {
  display: inline-block;
  cursor: pointer;
  
  .screenfull-icon {
    font-size: 18px;
    color: #5a5e66;
    vertical-align: middle;
  }
}
</style>