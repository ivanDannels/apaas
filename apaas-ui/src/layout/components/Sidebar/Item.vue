<template>
  <el-icon v-if="icon">
    <component :is="icon" />
  </el-icon>
  <span v-if="title" class="menu-title">{{ title }}</span>
</template>

<script setup lang="ts">
interface Props {
  icon?: string
  title?: string
}

withDefaults(defineProps<Props>(), {
  icon: '',
  title: ''
})
</script>

<style scoped>
.menu-title {
  margin-left: 8px;
}
</style>