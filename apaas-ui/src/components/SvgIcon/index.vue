<template>
  <svg
    :class="svgClass"
    :style="style"
    aria-hidden="true"
    v-bind="$attrs"
  >
    <use :xlink:href="iconName" />
  </svg>
</template>

<script setup lang="ts">
import { computed } from 'vue'

// 定义属性
interface Props {
  iconClass: string
  className?: string
  style?: Record<string, any>
}

const props = withDefaults(defineProps<Props>(), {
  className: '',
  style: () => ({})
})

// 计算属性
const iconName = computed(() => {
  return `#icon-${props.iconClass}`
})

const svgClass = computed(() => {
  if (props.className) {
    return `svg-icon ${props.className}`
  } else {
    return 'svg-icon'
  }
})
</script>

<style lang="scss" scoped>
.svg-icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
</style>