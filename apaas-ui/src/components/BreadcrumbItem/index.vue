<template>
  <el-breadcrumb-item
    :to="to"
    :replace="replace"
    v-bind="$attrs"
  >
    <slot></slot>
  </el-breadcrumb-item>
</template>

<script setup lang="ts">
// 定义属性
const props = defineProps({
  // 路由跳转对象，同 vue-router 的 to
  to: {
    type: [String, Object],
    default: ''
  },
  // 在使用 to 进行路由跳转时，启用 replace 将不会向 history 添加新记录
  replace: {
    type: Boolean,
    default: false
  }
})
</script>

<style lang="scss" scoped>
// 面包屑项样式可以根据需要自定义
</style>