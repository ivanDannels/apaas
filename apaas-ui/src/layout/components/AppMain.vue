<template>
  <section class="app-main">
    <router-view v-slot="{ Component }">
      <transition name="fade-transform" mode="out-in">
        <component :is="Component" :key="routerViewKey" />
      </transition>
    </router-view>
  </section>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

// 为路由视图提供唯一的key，确保页面正确切换
const routerViewKey = computed(() => {
  return route.path
})
</script>

<style scoped>
.app-main {
  flex: 1;
  padding: 20px;
  overflow: auto;
}

/* fade-transform过渡动画 */
.fade-transform-leave-active,
.fade-transform-enter-active {
  transition: all .5s;
}

.fade-transform-enter-from {
  opacity: 0;
  transform: translateX(-30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>