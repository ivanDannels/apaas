<template>
  <div class="tags-view-container">
    <el-scrollbar 
      ref="scrollbarRef" 
      class="tags-view-wrapper"
      @wheel.prevent="handleScroll"
    >
      <router-link
        v-for="tag in visitedViews"
        :key="tag.path"
        :to="{ path: tag.path, query: tag.query, fullPath: tag.fullPath }"
        class="tags-view-item"
        :class="isActive(tag) ? 'active' : ''"
        @click.middle="!isAffix(tag) ? closeSelectedTag(tag) : ''"
        @contextmenu.prevent="openMenu(tag, $event)"
      >
        {{ tag.meta?.title || tag.name }}
        <span
          v-if="!isAffix(tag)"
          class="el-icon-close"
          @click.prevent.stop="closeSelectedTag(tag)"
        >
          <i-ep-close class="close-icon" />
        </span>
      </router-link>
    </el-scrollbar>
    
    <!-- 右键菜单 -->
    <ul 
      v-show="visible" 
      :style="{ left: left + 'px', top: top + 'px' }" 
      class="contextmenu"
    >
      <li @click="refreshSelectedTag(selectedTag)">刷新页面</li>
      <li v-if="!isAffix(selectedTag)" @click="closeSelectedTag(selectedTag)">关闭当前</li>
      <li @click="closeOthersTags">关闭其他</li>
      <li @click="closeAllTags(selectedTag)">关闭所有</li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, getCurrentInstance } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useTagsViewStore } from '@/store/modules/tagsView'

// 定义路由和存储
const route = useRoute()
const router = useRouter()
const tagsViewStore = useTagsViewStore()

// 定义响应式数据
const visible = ref(false)
const top = ref(0)
const left = ref(0)
const selectedTag = ref({})
const affixTags = ref([])
const scrollbarRef = ref()

// 计算属性
const visitedViews = computed(() => {
  return tagsViewStore.visitedViews
})

// 判断是否为激活状态
const isActive = (tag) => {
  return tag.path === route.path
}

// 判断是否为固定标签
const isAffix = (tag) => {
  return tag.meta && tag.meta.affix
}

// 初始化标签
const initTags = () => {
  const affixTags = filterAffixTags(permissionStore.routes)
  for (const tag of affixTags) {
    // 必须包含 name 属性
    if (tag.name) {
      tagsViewStore.addVisitedView(tag)
    }
  }
}

// 过滤固定标签
const filterAffixTags = (routes, basePath = '/') => {
  let tags = []
  routes.forEach(route => {
    if (route.meta && route.meta.affix) {
      const tagPath = path.resolve(basePath, route.path)
      tags.push({
        fullPath: tagPath,
        path: tagPath,
        name: route.name,
        meta: { ...route.meta }
      })
    }
    if (route.children) {
      const tempTags = filterAffixTags(route.children, route.path)
      if (tempTags.length >= 1) {
        tags = [...tags, ...tempTags]
      }
    }
  })
  return tags
}

// 添加标签
const addTags = () => {
  const { name } = route
  if (name) {
    tagsViewStore.addView(route)
  }
  return false
}

// 刷新选中标签
const refreshSelectedTag = (view) => {
  tagsViewStore.delCachedView(view).then(() => {
    const { fullPath } = view
    nextTick(() => {
      router.replace({
        path: '/redirect' + fullPath
      })
    })
  })
}

// 关闭选中标签
const closeSelectedTag = (view) => {
  tagsViewStore.delView(view).then(({ visitedViews }) => {
    if (isActive(view)) {
      toLastView(visitedViews, view)
    }
  })
}

// 关闭其他标签
const closeOthersTags = () => {
  router.push(selectedTag.value).catch(err => {
    console.warn(err)
  })
  tagsViewStore.delOthersViews(selectedTag.value).then(() => {
    moveToCurrentTag()
  })
}

// 关闭所有标签
const closeAllTags = (view) => {
  tagsViewStore.delAllViews().then(({ visitedViews }) => {
    if (affixTags.value.some(tag => tag.path === view.path)) {
      return
    }
    toLastView(visitedViews, view)
  })
}

// 跳转到最后一个视图
const toLastView = (visitedViews, view) => {
  const latestView = visitedViews.slice(-1)[0]
  if (latestView) {
    router.push(latestView.fullPath)
  } else {
    // 如果没有标签视图，默认跳转到主页
    if (view.name === 'Dashboard') {
      // 重新加载主页
      router.replace({ path: '/redirect' + view.fullPath })
    } else {
      router.push('/')
    }
  }
}

// 移动到当前标签
const moveToCurrentTag = () => {
  const tags = document.querySelectorAll('.tags-view-item')
  nextTick(() => {
    for (const tag of tags) {
      if (tag.classList.contains('active')) {
        scrollbarRef.value.setScrollLeft(tag.offsetLeft - (scrollbarRef.value.wrapRef.offsetWidth / 2))
        break
      }
    }
  })
}

// 打开右键菜单
const openMenu = (tag, e) => {
  const menuMinWidth = 105
  const offsetLeft = scrollbarRef.value.wrapRef.getBoundingClientRect().left // 容器距离左侧距离
  const offsetWidth = scrollbarRef.value.wrapRef.offsetWidth // 容器宽度
  const maxLeft = offsetWidth - menuMinWidth // 左侧最大距离
  const leftVal = e.clientX - offsetLeft + 15 // 15 为右侧偏移量
  
  if (leftVal > maxLeft) {
    left.value = maxLeft
  } else {
    left.value = leftVal
  }
  
  top.value = e.clientY
  visible.value = true
  selectedTag.value = tag
}

// 关闭菜单
const closeMenu = () => {
  visible.value = false
}

// 处理滚动
const handleScroll = (e) => {
  const eventDelta = e.wheelDelta || -e.deltaY * 40
  const $scrollbar = scrollbarRef.value
  $scrollbar.setScrollLeft($scrollbar.scrollLeft - eventDelta / 4)
}

// 监听路由变化
watch(
  () => route.path,
  () => {
    addTags()
    moveToCurrentTag()
  },
  { immediate: true }
)

// 监听右键菜单显示状态
watch(
  () => visible.value,
  (value) => {
    if (value) {
      document.body.addEventListener('click', closeMenu)
    } else {
      document.body.removeEventListener('click', closeMenu)
    }
  }
)

// 组件挂载时初始化
onMounted(() => {
  initTags()
  addTags()
})
</script>

<style lang="scss" scoped>
.tags-view-container {
  height: 34px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
  
  .tags-view-wrapper {
    .tags-view-item {
      display: inline-block;
      position: relative;
      cursor: pointer;
      height: 26px;
      line-height: 26px;
      border: 1px solid #d8dce5;
      color: #495060;
      background: #fff;
      padding: 0 8px;
      font-size: 12px;
      margin-left: 5px;
      margin-top: 4px;
      
      &:first-of-type {
        margin-left: 15px;
      }
      
      &:last-of-type {
        margin-right: 15px;
      }
      
      &.active {
        background-color: #409eff;
        color: #fff;
        border-color: #409eff;
        
        &::before {
          content: '';
          background: #fff;
          display: inline-block;
          width: 8px;
          height: 8px;
          border-radius: 50%;
          position: relative;
          margin-right: 2px;
        }
      }
      
      .close-icon {
        width: 12px;
        height: 12px;
        vertical-align: 2px;
        border-radius: 50%;
        text-align: center;
        transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
        transform-origin: 100% 50%;
        
        &:before {
          transform: scale(0.6);
          display: inline-block;
          vertical-align: -3px;
        }
        
        &:hover {
          background-color: #b4bccc;
          color: #fff;
        }
      }
    }
  }
  
  .contextmenu {
    margin: 0;
    background: #fff;
    z-index: 3000;
    position: absolute;
    list-style-type: none;
    padding: 5px 0;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 400;
    color: #333;
    box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
    
    li {
      margin: 0;
      padding: 7px 16px;
      cursor: pointer;
      
      &:hover {
        background: #eee;
      }
    }
  }
}
</style>