<template>
  <el-form
    ref="formRef"
    :model="model"
    :rules="rules"
    :label-width="labelWidth"
    :label-position="labelPosition"
    :size="size"
    :inline="inline"
    :inline-message="inlineMessage"
    :status-icon="statusIcon"
    :hide-required-asterisk="hideRequiredAsterisk"
    :show-message="showMessage"
    :scroll-to-error="scrollToError"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-form>
</template>

<script setup lang="ts">
import { ref, computed, provide, onMounted, nextTick } from 'vue'
import { ElMessage } from 'element-plus'

// 定义属性
const props = defineProps({
  // 表单数据对象
  model: {
    type: Object,
    required: true
  },
  // 表单验证规则
  rules: {
    type: Object,
    default: () => ({})
  },
  // 标签宽度
  labelWidth: {
    type: [String, Number],
    default: '100px'
  },
  // 标签位置
  labelPosition: {
    type: String,
    default: 'right', // left / right / top
    validator: (value) => ['left', 'right', 'top'].includes(value)
  },
  // 表单尺寸
  size: {
    type: String,
    default: 'default' // large / default / small
  },
  // 是否行内表单
  inline: {
    type: Boolean,
    default: false
  },
  // 是否以行内形式展示校验信息
  inlineMessage: {
    type: Boolean,
    default: false
  },
  // 是否在输入框中显示校验结果反馈图标
  statusIcon: {
    type: Boolean,
    default: false
  },
  // 是否隐藏必填字段的标签旁边的红色星号
  hideRequiredAsterisk: {
    type: Boolean,
    default: false
  },
  // 是否显示校验错误信息
  showMessage: {
    type: Boolean,
    default: true
  },
  // 是否在提交表单后滚动到第一个错误字段
  scrollToError: {
    type: Boolean,
    default: false
  },
  // 是否禁用表单
  disabled: {
    type: Boolean,
    default: false
  }
})

// 定义事件
const emit = defineEmits(['submit', 'validate', 'validate-field'])

// 定义响应式数据
const formRef = ref(null)

// 提供表单引用给子组件
provide('formRef', formRef)

// 提供表单属性给子组件
provide('formProps', computed(() => ({
  ...props,
  disabled: props.disabled
})))

// 表单验证
const validate = (callback) => {
  return formRef.value.validate(callback)
}

// 对部分表单字段进行校验
const validateField = (props, callback) => {
  return formRef.value.validateField(props, callback)
}

// 重置表单
const resetFields = () => {
  formRef.value.resetFields()
}

// 清理某个字段的表单验证信息
const clearValidate = (props) => {
  formRef.value.clearValidate(props)
}

// 获取表单数据
const getFormData = () => {
  return { ...props.model }
}

// 设置表单数据
const setFormData = (data) => {
  Object.keys(data).forEach(key => {
    if (props.model.hasOwnProperty(key)) {
      props.model[key] = data[key]
    }
  })
}

// 提交表单
const submitForm = async () => {
  try {
    const valid = await validate()
    if (valid) {
      emit('submit', props.model)
    }
  } catch (error) {
    console.error('表单验证失败:', error)
    ElMessage.error('表单验证失败，请检查输入')
  }
}

// 处理表单验证
const handleValidate = (prop, isValid, message) => {
  emit('validate', prop, isValid, message)
}

// 处理字段验证
const handleValidateField = (prop, isValid, message) => {
  emit('validate-field', prop, isValid, message)
}

// 定义暴露给父组件的方法
defineExpose({
  validate,
  validateField,
  resetFields,
  clearValidate,
  getFormData,
  setFormData,
  submitForm
})
</script>

<style lang="scss" scoped>
// 表单样式可以根据需要自定义
</style>