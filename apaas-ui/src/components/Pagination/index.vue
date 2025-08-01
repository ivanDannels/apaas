<template>
  <div :class="{'hidden': hidden}" class="pagination-container">
    <el-pagination
      :background="background"
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :layout="layout"
      :page-sizes="pageSizes"
      :pager-count="pagerCount"
      :total="total"
      v-bind="$attrs"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

// 定义属性
interface Props {
  total: number
  page?: number
  limit?: number
  pageSizes?: number[]
  layout?: string
  background?: boolean
  autoScroll?: boolean
  hidden?: boolean
  pagerCount?: number
}

const props = withDefaults(defineProps<Props>(), {
  page: 1,
  limit: 20,
  pageSizes: () => [10, 20, 30, 50],
  layout: 'total, sizes, prev, pager, next, jumper',
  background: true,
  autoScroll: true,
  hidden: false,
  pagerCount: 7
})

// 定义事件
interface Emits {
  (e: 'pagination', data: { page: number; limit: number }): void
  (e: 'update:page', page: number): void
  (e: 'update:limit', limit: number): void
}

const emit = defineEmits<Emits>()

// 计算属性
const currentPage = computed({
  get() {
    return props.page
  },
  set(val: number) {
    emit('update:page', val)
  }
})

const pageSize = computed({
  get() {
    return props.limit
  },
  set(val: number) {
    emit('update:limit', val)
  }
})

// 处理页面大小变化
const handleSizeChange = (val: number) => {
  if (currentPage.value * val > props.total) {
    currentPage.value = 1
  }
  emit('pagination', { page: currentPage.value, limit: val })
  if (props.autoScroll) {
    scrollTo(0, 800)
  }
}

// 处理当前页变化
const handleCurrentChange = (val: number) => {
  emit('pagination', { page: val, limit: pageSize.value })
  if (props.autoScroll) {
    scrollTo(0, 800)
  }
}
</script>

<style lang="scss" scoped>
.pagination-container {
  background: #fff;
  padding: 32px 16px;
  
  .el-pagination {
    display: flex;
    justify-content: flex-end;
  }
}

.pagination-container.hidden {
  display: none;
}
</style>