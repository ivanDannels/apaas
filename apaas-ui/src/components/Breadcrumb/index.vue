<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item 
        v-for="(item, index) in levelList" 
        :key="item.path"
      >
        <span
          v-if="item.redirect === 'noRedirect' || index === levelList.length - 1"
          class="no-redirect"
        >
          {{ item.meta?.title || item.name }}
        </span>
        <a v-else @click.prevent="handleLink(item)">
          {{ item.meta?.title || item.name }}
        </a>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import type { RouteLocationMatched } from 'vue-router'

const route = useRoute()
const router = useRouter()

// 定义响应式数据
const levelList = ref<RouteLocationMatched[]>([])

// 获取面包屑路径
const getBreadcrumb = () => {
  // only show routes with meta.title
  let matched = route.matched.filter(item => item.meta && item.meta.title)
  
  // 如果第一个路由不是首页，则添加首页
  const first = matched[0]
  if (first && first.name !== 'Index') {
    matched = [{ path: '/index', meta: { title: '首页' } } as RouteLocationMatched].concat(matched)
  }
  
  // 过滤掉不需要在面包屑中显示的路由
  levelList.value = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
}

// 处理链接点击
const handleLink = (item: RouteLocationMatched) => {
  const { redirect, path } = item
  if (redirect) {
    router.push(redirect)
    return
  }
  router.push(path)
}

// 监听路由变化
watch(
  () => route.path,
  () => {
    getBreadcrumb()
  },
  { immediate: true }
)
</script>

<style lang="scss" scoped>
.app-breadcrumb.el-breadcrumb {
  display: inline-block;
  font-size: 14px;
  line-height: 50px;
  margin-left: 8px;
  
  .no-redirect {
    color: #97a8be;
    cursor: text;
  }
  
  a {
    font-weight: normal;
    color: #303133;
    
    &:hover {
      color: #409eff;
    }
  }
}

.breadcrumb-enter-active,
.breadcrumb-leave-active {
  transition: all 0.5s;
}

.breadcrumb-enter,
.breadcrumb-leave-active {
  opacity: 0;
  transform: translateX(20px);
}

.breadcrumb-move {
  transition: all 0.5s;
}

.breadcrumb-leave-active {
  position: absolute;
}
</style>