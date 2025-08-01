<template>
  <el-tab-pane
    :label="label"
    :name="name"
    :disabled="disabled"
    :closable="closable"
    :lazy="lazy"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-tab-pane>
</template>

<script setup lang="ts">
// 定义属性
interface Props {
  // 选项卡标题
  label?: string | ((h: any) => any)
  // 与选项卡绑定值 value 对应的标识符
  name?: string | number
  // 是否禁用
  disabled?: boolean
  // 标签是否可关闭
  closable?: boolean
  // 标签是否延迟渲染
  lazy?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  label: '',
  name: '',
  disabled: false,
  closable: false,
  lazy: false
})
</script>

<style lang="scss" scoped>
// 选项卡面板样式可以根据需要自定义
</style>