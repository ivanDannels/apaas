<template>
  <el-select
    ref="selectRef"
    v-model="selectValue"
    :multiple="multiple"
    :disabled="isDisabled"
    :value-key="valueKey"
    :size="selectSize"
    :clearable="clearable"
    :collapse-tags="collapseTags"
    :multiple-limit="multipleLimit"
    :name="name"
    :autocomplete="autocomplete"
    :placeholder="placeholder"
    :filterable="filterable"
    :allow-create="allowCreate"
    :filter-method="filterMethod"
    :remote="remote"
    :remote-method="remoteMethod"
    :loading="loading"
    :loading-text="loadingText"
    :no-match-text="noMatchText"
    :no-data-text="noDataText"
    :popper-class="popperClass"
    :reserve-keyword="reserveKeyword"
    :default-first-option="defaultFirstOption"
    :teleported="teleported"
    :persistent="persistent"
    :automatic-dropdown="automaticDropdown"
    :clear-icon="clearIcon"
    :fit-input-width="fitInputWidth"
    :suffix-icon="suffixIcon"
    :suffix-transition="suffixTransition"
    :tag-type="tagType"
    v-bind="$attrs"
    @change="handleChange"
    @focus="handleFocus"
    @blur="handleBlur"
    @clear="handleClear"
    @remove-tag="handleRemoveTag"
    @visible-change="handleVisibleChange"
    @blur="handleBlur"
  >
    <slot></slot>
  </el-select>
</template>

<script setup lang="ts">
import { ref, computed, inject, watch } from 'vue'
import type { SelectInstance } from 'element-plus'

// 定义属性
interface Props {
  // 绑定值
  modelValue?: string | number | boolean | Record<string, any> | Array<any>
  // 是否多选
  multiple?: boolean
  // 是否禁用
  disabled?: boolean
  // 作为 value 唯一标识的键名，绑定值为对象类型时必填
  valueKey?: string
  // 输入框尺寸
  size?: string
  // 是否可以清空选项
  clearable?: boolean
  // 多选时是否将选中值按文字的形式展示
  collapseTags?: boolean
  // 多选时用户最多可以选择的项目数，为 0 则不限制
  multipleLimit?: number
  // select input 的 name 属性
  name?: string
  // select input 的 autocomplete 属性
  autocomplete?: string
  // 占位符
  placeholder?: string
  // 是否可搜索
  filterable?: boolean
  // 是否允许用户创建新条目
  allowCreate?: boolean
  // 自定义搜索方法
  filterMethod?: ((query: string) => void) | null
  // 是否为远程搜索
  remote?: boolean
  // 远程搜索方法
  remoteMethod?: ((query: string) => void) | null
  // 是否正在从远程获取数据
  loading?: boolean
  // 远程加载时显示的文字
  loadingText?: string
  // 搜索条件无匹配时显示的文字
  noMatchText?: string
  // 选项为空时显示的文字
  noDataText?: string
  // Select 下拉框的类名
  popperClass?: string
  // 多选且可搜索时，是否在选中一个选项后保留当前的搜索关键词
  reserveKeyword?: boolean
  // 是否在输入框按下回车时，选择第一个匹配项
  defaultFirstOption?: boolean
  // 是否将弹出框插入至 body 元素
  teleported?: boolean
  // 下拉框是否持久化
  persistent?: boolean
  // 在输入框获得焦点时是否自动弹出选项菜单
  automaticDropdown?: boolean
  // 清空图标的类名
  clearIcon?: string
  // 是否将弹出框的宽度设置为与输入框相同
  fitInputWidth?: boolean
  // 自定义后缀图标组件
  suffixIcon?: string
  // 自定义后缀图标是否旋转
  suffixTransition?: boolean
  // 多选时标签的类型
  tagType?: 'success' | 'info' | 'warning' | 'danger'
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: '',
  multiple: false,
  disabled: undefined,
  valueKey: 'value',
  size: '',
  clearable: false,
  collapseTags: false,
  multipleLimit: 0,
  name: '',
  autocomplete: 'off',
  placeholder: '请选择',
  filterable: false,
  allowCreate: false,
  filterMethod: null,
  remote: false,
  remoteMethod: null,
  loading: false,
  loadingText: '加载中',
  noMatchText: '无匹配数据',
  noDataText: '无数据',
  popperClass: '',
  reserveKeyword: false,
  defaultFirstOption: false,
  teleported: true,
  persistent: true,
  automaticDropdown: false,
  clearIcon: '',
  fitInputWidth: false,
  suffixIcon: '',
  suffixTransition: true,
  tagType: 'info'
})

// 定义事件
interface Emits {
  (e: 'update:modelValue', value: string | number | boolean | Record<string, any> | Array<any>): void
  (e: 'change', value: string | number | boolean | Record<string, any> | Array<any>): void
  (e: 'focus', event: FocusEvent): void
  (e: 'blur', event: FocusEvent): void
  (e: 'clear'): void
  (e: 'remove-tag', tag: any): void
  (e: 'visible-change', visible: boolean): void
}

const emit = defineEmits<Emits>()

// 注入父级表单项
const formItem = inject<any>('formItem', null)

// 注入父级表单属性
const formProps = inject<Record<string, any>>('formProps', {})

// 定义响应式数据
const selectRef = ref<SelectInstance | null>(null)
const selectValue = ref(props.modelValue)

// 计算属性：是否禁用
const isDisabled = computed(() => {
  // 如果props中明确设置了disabled，则使用props的值
  if (props.disabled !== undefined) {
    return props.disabled
  }
  // 否则使用父级表单的disabled值
  return formProps.disabled || false
})

// 计算属性：选择器尺寸
const selectSize = computed(() => {
  return props.size || formProps.size || 'default'
})

// 监听modelValue变化
watch(
  () => props.modelValue,
  (val) => {
    selectValue.value = val
  }
)

// 监听selectValue变化
watch(
  () => selectValue.value,
  (val) => {
    emit('update:modelValue', val)
  }
)

// 处理值变化事件
const handleChange = (value: string | number | boolean | Record<string, any> | Array<any>) => {
  selectValue.value = value
  emit('change', value)
  
  // 触发表单项验证
  if (formItem && props.validateEvent) {
    formItem.validate('change')
  }
}

// 处理聚焦事件
const handleFocus = (event: FocusEvent) => {
  emit('focus', event)
}

// 处理失焦事件
const handleBlur = (event: FocusEvent) => {
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

// 处理移除标签事件
const handleRemoveTag = (tag: any) => {
  emit('remove-tag', tag)
}

// 处理下拉框显示/隐藏事件
const handleVisibleChange = (visible: boolean) => {
  emit('visible-change', visible)
}

// 定义暴露给父组件的方法
const focus = () => {
  selectRef.value?.focus()
}

const blur = () => {
  selectRef.value?.blur()
}

defineExpose({
  focus,
  blur
})
</script>

<style lang="scss" scoped>
// 选择器样式可以根据需要自定义
</style>