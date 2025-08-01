<template>
  <el-popconfirm
    ref="popconfirmRef"
    :title="title"
    :confirm-button-text="confirmButtonText"
    :cancel-button-text="cancelButtonText"
    :confirm-button-type="confirmButtonType"
    :cancel-button-type="cancelButtonType"
    :icon="icon"
    :icon-color="iconColor"
    :hide-icon="hideIcon"
    :disabled="disabled"
    :teleported="teleported"
    :persistent="persistent"
    :width="width"
    v-bind="$attrs"
    @confirm="handleConfirm"
    @cancel="handleCancel"
  >
    <template #reference v-if="$slots.reference">
      <slot name="reference"></slot>
    </template>
    
    <template #actions v-if="$slots.actions">
      <slot name="actions"></slot>
    </template>
  </el-popconfirm>
</template>

<script setup lang="ts">\nimport { ref } from 'vue'
import { QuestionFilled } from '@element-plus/icons-vue'

// 定义属性
const props = defineProps({
  // 标题
  title: {
    type: String,
    default: '确定要删除吗？'
  },
  // 确认按钮文字
  confirmButtonText: {
    type: String,
    default: '确定'
  },
  // 取消按钮文字
  cancelButtonText: {
    type: String,
    default: '取消'
  },
  // 确认按钮类型
  confirmButtonType: {
    type: String,
    default: 'primary', // primary / success / warning / danger / info / text
    validator: (value) => ['primary', 'success', 'warning', 'danger', 'info', 'text'].includes(value)
  },
  // 取消按钮类型
  cancelButtonType: {
    type: String,
    default: 'info', // primary / success / warning / danger / info / text
    validator: (value) => ['primary', 'success', 'warning', 'danger', 'info', 'text'].includes(value)
  },
  // 图标
  icon: {
    type: [String, Object],
    default: QuestionFilled
  },
  // 图标颜色
  iconColor: {
    type: String,
    default: '#f90'
  },
  // 是否隐藏图标
  hideIcon: {
    type: Boolean,
    default: false
  },
  // 是否禁用
  disabled: {
    type: Boolean,
    default: false
  },
  // 是否使用 teleport
  teleported: {
    type: Boolean,
    default: true
  },
  // 当 popover 关闭时，是否停留在内存中
  persistent: {
    type: Boolean,
    default: false
  },
  // 宽度
  width: {
    type: [String, Number],
    default: 200
  }
})

// 定义响应式数据
const popconfirmRef = ref(null)

// 定义事件
const emit = defineEmits(['confirm', 'cancel'])

// 处理确认事件
const handleConfirm = () => {
  emit('confirm')
}

// 处理取消事件
const handleCancel = () => {
  emit('cancel')
}
</script>

<style lang="scss" scoped>
// 确认框样式可以根据需要自定义
</style>