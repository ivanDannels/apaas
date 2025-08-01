<template>
  <el-form-item
    ref="formItemRef"
    :prop="prop"
    :label="label"
    :label-width="labelWidth"
    :required="required"
    :rules="rules"
    :error="error"
    :show-message="showMessage"
    :inline-message="inlineMessage"
    :size="size"
  >
    <slot></slot>
  </el-form-item>
</template>

<script setup lang="ts">
import { ref, inject, computed, onMounted, useAttrs } from 'vue'

// 定义属性
const props = defineProps({
  // 字段名
  prop: {
    type: String,
    default: ''
  },
  // 标签文本
  label: {
    type: String,
    default: ''
  },
  // 标签宽度
  labelWidth: {
    type: [String, Number],
    default: ''
  },
  // 是否必填
  required: {
    type: Boolean,
    default: false
  },
  // 验证规则
  rules: {
    type: [Object, Array],
    default: null
  },
  // 错误信息
  error: {
    type: String,
    default: ''
  },
  // 是否显示校验错误信息
  showMessage: {
    type: Boolean,
    default: true
  },
  // 是否以行内形式展示校验信息
  inlineMessage: {
    type: Boolean,
    default: false
  },
  // 表单项大小
  size: {
    type: String,
    default: ''
  }
})

// 定义事件
const emit = defineEmits(['blur', 'change'])

// 注入父级表单引用
const formRef = inject('formRef', null)

// 注入父级表单属性
const formProps = inject('formProps', {})

// 定义响应式数据
const formItemRef = ref(null)

// 计算属性：是否禁用
const isDisabled = computed(() => {
  return formProps.disabled || false
})

// 计算属性：表单项大小
const itemSize = computed(() => {
  return props.size || formProps.size || 'default'
})

// 计算属性：标签宽度
const itemLabelWidth = computed(() => {
  return props.labelWidth || formProps.labelWidth || '100px'
})

// 表单验证
const validate = (trigger, callback) => {
  if (formRef && formRef.value) {
    return formRef.value.validateField(props.prop, callback)
  }
}

// 清理表单验证信息
const clearValidate = () => {
  if (formRef && formRef.value) {
    formRef.value.clearValidate(props.prop)
  }
}

// 定义暴露给父组件的方法
defineExpose({
  validate,
  clearValidate
})
</script>

<style lang="scss" scoped>
// 表单项样式可以根据需要自定义
</style>