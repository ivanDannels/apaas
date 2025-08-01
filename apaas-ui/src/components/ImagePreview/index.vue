<template>
  <div class="image-preview">
    <el-image
      :src="src"
      :alt="alt"
      :fit="fit"
      :lazy="lazy"
      :scroll-container="scrollContainer"
      :preview-src-list="previewSrcList"
      :preview-teleported="previewTeleported"
      :zoom-rate="zoomRate"
      :max-scale="maxScale"
      :min-scale="minScale"
      :initial-index="initialIndex"
      :close-on-press-escape="closeOnPressEscape"
      :hide-on-click-modal="hideOnClickModal"
      v-bind="$attrs"
    >
      <template #placeholder>
        <div class="image-slot">
          <el-icon><Picture /></el-icon>
        </div>
      </template>
      
      <template #error>
        <div class="image-slot">
          <el-icon><icon-picture /></el-icon>
        </div>
      </template>
    </el-image>
  </div>
</template>

<script setup lang="ts">
import { Picture } from '@element-plus/icons-vue'

// 定义属性
const props = defineProps({
  // 图片源
  src: {
    type: String,
    required: true
  },
  // 图片替代文本
  alt: {
    type: String,
    default: ''
  },
  // 确定图片如何适应容器框
  fit: {
    type: String,
    default: 'fill'
  },
  // 是否开启懒加载
  lazy: {
    type: Boolean,
    default: false
  },
  // 开启懒加载后，监听 scroll 事件的容器
  scrollContainer: {
    type: [String, Object],
    default: null
  },
  // 预览数组
  previewSrcList: {
    type: Array,
    default: () => []
  },
  // 预览时是否将弹出层插入至 body 元素
  previewTeleported: {
    type: Boolean,
    default: false
  },
  // 缩放倍数
  zoomRate: {
    type: Number,
    default: 1.2
  },
  // 最大缩放比例
  maxScale: {
    type: Number,
    default: 7
  },
  // 最小缩放比例
  minScale: {
    type: Number,
    default: 0.1
  },
  // 初始预览图像索引
  initialIndex: {
    type: Number,
    default: 0
  },
  // 是否可以通过按下 ESC 关闭预览
  closeOnPressEscape: {
    type: Boolean,
    default: true
  },
  // 是否可以通过点击遮罩层关闭预览
  hideOnClickModal: {
    type: Boolean,
    default: true
  }
})
</script>

<style lang="scss" scoped>
.image-preview {
  display: inline-block;
  
  :deep(.el-image) {
    display: block;
  }
  
  :deep(.image-slot) {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background: #f5f7fa;
    color: #909399;
    
    .el-icon {
      font-size: 30px;
    }
  }
}
</style>