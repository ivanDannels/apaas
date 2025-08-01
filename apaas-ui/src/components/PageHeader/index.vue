<template>
  <el-page-header
    ref="pageHeaderRef"
    :title="title"
    :content="content"
    :icon="icon"
    v-bind="$attrs"
    @back="handleBack"
  >
    <template #title v-if="$slots.title || title">
      <slot name="title">{{ title }}</slot>
    </template>
    
    <template #content v-if="$slots.content || content">
      <slot name="content">{{ content }}</slot>
    </template>
    
    <template #icon v-if="$slots.icon || icon">
      <slot name="icon">{{ icon }}</slot>
    </template>
  </el-page-header>
</template>

<script setup lang="ts">\nimport { ref, reactive } from 'vue'

// 定义属性
const props = defineProps({
  // 标题
  title: {
    type: String,
    default: 'Back'
  },
  // 内容
  content: {
    type: String,
    default: ''
  },
  // 图标
  icon: {
    type: [String, Object],
    default: 'ArrowLeft'
  }
})

// 定义响应式数据
const pageHeaderRef = ref(null)

// 定义事件
const emit = defineEmits(['back'])

// 处理返回事件
const handleBack = () => {
  emit('back')
}
</script>

<style lang="scss" scoped>
// 页面头部样式可以根据需要自定义
</style>