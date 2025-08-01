<template>
  <el-input
    ref="inputRef"
    v-model="inputValue"
    :type="type"
    :maxlength="maxlength"
    :minlength="minlength"
    :show-word-limit="showWordLimit"
    :placeholder="placeholder"
    :clearable="clearable"
    :show-password="showPassword"
    :disabled="isDisabled"
    :size="inputSize"
    :prefix-icon="prefixIcon"
    :suffix-icon="suffixIcon"
    :rows="rows"
    :autosize="autosize"
    :autocomplete="autocomplete"
    :name="name"
    :readonly="readonly"
    :max="max"
    :min="min"
    :step="step"
    :resize="resize"
    :autofocus="autofocus"
    :form="form"
    :label="label"
    :tabindex="tabindex"
    :validate-event="validateEvent"
    v-bind="$attrs"
    @input="handleInput"
    @change="handleChange"
    @focus="handleFocus"
    @blur="handleBlur"
    @clear="handleClear"
    @mouseenter="handleMouseEnter"
    @mouseleave="handleMouseLeave"
  >
    <template v-if="$slots.prepend" #prepend>
      <slot name="prepend"></slot>
    </template>
    
    <template v-if="$slots.prefix" #prefix>
      <slot name="prefix"></slot>
    </template>
    
    <template v-if="$slots.suffix" #suffix>
      <slot name="suffix"></slot>
    </template>
    
    <template v-if="$slots.append" #append>
      <slot name="append"></slot>
    </template>
  </el-input>
</template>

<script setup lang="ts">
import { ref, computed, inject, watch } from 'vue'

// 定义属性
const props = defineProps({
  // 绑定值
  modelValue: {
    type: [String, Number],
    default: ''
  },
  // 输入框类型
  type: {
    type: String,
    default: 'text'
  },
  // 最大输入长度
  maxlength: {
    type: [String, Number],
    default: null
  },
  // 最小输入长度
  minlength: {
    type: [String, Number],
    default: null
  },
  // 是否显示输入字数统计
  showWordLimit: {
    type: Boolean,
    default: false
  },
  // 输入框占位文本
  placeholder: {
    type: String,
    default: ''
  },
  // 是否可清空
  clearable: {
    type: Boolean,
    default: false
  },
  // 是否显示切换密码图标
  showPassword: {
    type: Boolean,
    default: false
  },
  // 是否禁用
  disabled: {
    type: Boolean,
    default: undefined
  },
  // 输入框尺寸
  size: {
    type: String,
    default: ''
  },
  // 输入框头部图标
  prefixIcon: {
    type: String,
    default: ''
  },
  // 输入框尾部图标
  suffixIcon: {
    type: String,
    default: ''
  },
  // 输入框行数，只对 type="textarea" 有效
  rows: {
    type: [String, Number],
    default: 2
  },
  // 自适应内容高度，只对 type="textarea" 有效
  autosize: {
    type: [Boolean, Object],
    default: false
  },
  // 原生属性 autocomplete
  autocomplete: {
    type: String,
    default: 'off'
  },
  // 原生属性 name
  name: {
    type: String,
    default: ''
  },
  // 是否只读
  readonly: {
    type: Boolean,
    default: false
  },
  // 原生属性 max
  max: {
    type: [String, Number],
    default: null
  },
  // 原生属性 min
  min: {
    type: [String, Number],
    default: null
  },
  // 原生属性 step
  step: {
    type: [String, Number],
    default: null
  },
  // 控制是否能被用户缩放
  resize: {
    type: String,
    default: 'none', // none, both, horizontal, vertical
    validator: (value) => ['none', 'both', 'horizontal', 'vertical'].includes(value)
  },
  // 原生属性 autofocus
  autofocus: {
    type: Boolean,
    default: false
  },
  // 原生属性 form
  form: {
    type: String,
    default: ''
  },
  // 输入框关联的 label 文字
  label: {
    type: String,
    default: ''
  },
  // 输入框的 tabindex
  tabindex: {
    type: [String, Number],
    default: ''
  },
  // 是否触发表单验证
  validateEvent: {
    type: Boolean,
    default: true
  }
})

// 定义事件
const emit = defineEmits(['update:modelValue', 'input', 'change', 'focus', 'blur', 'clear', 'mouseenter', 'mouseleave'])

// 注入父级表单项
const formItem = inject('formItem', null)

// 注入父级表单属性
const formProps = inject('formProps', {})

// 定义响应式数据
const inputRef = ref(null)
const inputValue = ref(props.modelValue)

// 计算属性：是否禁用
const isDisabled = computed(() => {
  // 如果props中明确设置了disabled，则使用props的值
  if (props.disabled !== undefined) {
    return props.disabled
  }
  // 否则使用父级表单的disabled值
  return formProps.disabled || false
})

// 计算属性：输入框尺寸
const inputSize = computed(() => {
  return props.size || formProps.size || 'default'
})

// 监听modelValue变化
watch(
  () => props.modelValue,
  (val) => {
    inputValue.value = val
  }
)

// 监听inputValue变化
watch(
  () => inputValue.value,
  (val) => {
    emit('update:modelValue', val)
  }
)

// 处理输入事件
const handleInput = (value) => {
  inputValue.value = value
  emit('input', value)
  
  // 触发表单项验证
  if (formItem && props.validateEvent) {
    formItem.validate('input')
  }
}

// 处理值变化事件
const handleChange = (value) => {
  emit('change', value)
  
  // 触发表单项验证
  if (formItem && props.validateEvent) {
    formItem.validate('change')
  }
}

// 处理聚焦事件
const handleFocus = (event) => {
  emit('focus', event)
}

// 处理失焦事件
const handleBlur = (event) => {
  emit('blur', event)
  
  // 触发表单项验证
  if (formItem && props.validateEvent) {
    formItem.validate('blur')
  }
}

// 处理清空事件
const handleClear = () => {
  emit('clear')
}

// 处理鼠标进入事件
const handleMouseEnter = (event) => {
  emit('mouseenter', event)
}

// 处理鼠标离开事件
const handleMouseLeave = (event) => {
  emit('mouseleave', event)
}

// 定义暴露给父组件的方法
const focus = () => {
  inputRef.value.focus()
}

const blur = () => {
  inputRef.value.blur()
}

const select = () => {
  inputRef.value.select()
}

const clear = () => {
  inputRef.value.clear()
}

defineExpose({
  focus,
  blur,
  select,
  clear
})
</script>

<style lang="scss" scoped>
// 输入框样式可以根据需要自定义
</style>