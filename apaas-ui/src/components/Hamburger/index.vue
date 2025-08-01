<template>
  <div class="hamburger-container" @click="toggleClick">
    <svg-icon 
      class="hamburger" 
      :icon-class="isActive ? 'close' : 'expand'" 
    />
  </div>
</template>

<script setup lang="ts">
// 定义属性
const props = defineProps({
  isActive: {
    type: Boolean,
    default: false
  }
})

// 定义事件
const emit = defineEmits(['toggleClick'])

// 切换点击事件
const toggleClick = () => {
  emit('toggleClick')
}
</script>

<style lang="scss" scoped>
.hamburger-container {
  display: inline-block;
  vertical-align: middle;
  cursor: pointer;
  padding: 0 15px;
  
  .hamburger {
    display: inline-block;
    vertical-align: middle;
    width: 20px;
    height: 20px;
    font-size: 20px;
    color: #333;
  }
}
</style>