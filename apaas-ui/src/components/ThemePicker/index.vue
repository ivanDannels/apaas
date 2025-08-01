<template>
  <el-color-picker
    v-model="theme"
    :predefine="predefineColors"
    popper-class="theme-picker-dropdown"
  />
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { useSettingsStore } from '@/stores/settings'

// 定义事件
const emit = defineEmits(['change'])

// 定义响应式数据
const settingsStore = useSettingsStore()
const theme = ref(settingsStore.theme)

// 预定义颜色
const predefineColors = [
  '#409EFF',
  '#1890ff',
  '#304156',
  '#212121',
  '#11a983',
  '#13c2c2',
  '#6959CD',
  '#f5222d'
]

// 监听主题颜色变化
watch(theme, (val) => {
  settingsStore.changeSetting({
    key: 'theme',
    value: val
  })
  emit('change', val)
})
</script>

<style lang="scss" scoped>
.theme-picker-dropdown {
  z-index: 99999 !important;
  
  .el-color-dropdown__link-btn {
    display: none;
  }
}
</style>