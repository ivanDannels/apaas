<template>
  <div class="tree-table">
    <el-table
      ref="tableRef"
      :data="tableData"
      :row-key="rowKey"
      :tree-props="treeProps"
      :default-expand-all="defaultExpandAll"
      :expand-row-keys="expandRowKeys"
      :lazy="lazy"
      :load="load"
      v-bind="$attrs"
    >
      <!-- 默认插槽用于传递表格列 -->
      <slot></slot>
      
      <!-- 操作列插槽 -->
      <el-table-column 
        v-if="$slots.operation"
        label="操作" 
        fixed="right" 
        align="center" 
        width="200"
      >
        <template #default="scope">
          <slot name="operation" :row="scope.row" :index="scope.$index"></slot>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 分页 -->
    <pagination
      v-if="showPagination && total > 0"
      :total="total"
      v-model:page="pageNum"
      v-model:limit="pageSize"
      @pagination="handlePagination"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import Pagination from '@/components/Pagination/index.vue'

// 定义属性
const props = defineProps({
  // 表格数据
  data: {
    type: Array,
    required: true
  },
  // 行数据的 Key
  rowKey: {
    type: String,
    default: 'id'
  },
  // 渲染嵌套数据的配置选项
  treeProps: {
    type: Object,
    default: () => ({
      children: 'children',
      hasChildren: 'hasChildren'
    })
  },
  // 是否默认展开所有行
  defaultExpandAll: {
    type: Boolean,
    default: false
  },
  // 展开的行
  expandRowKeys: {
    type: Array,
    default: () => []
  },
  // 是否懒加载子节点数据
  lazy: {
    type: Boolean,
    default: false
  },
  // 加载子节点数据的函数
  load: {
    type: Function,
    default: null
  },
  // 是否显示分页
  showPagination: {
    type: Boolean,
    default: false
  },
  // 总条目数
  total: {
    type: Number,
    default: 0
  },
  // 当前页码
  pageNum: {
    type: Number,
    default: 1
  },
  // 每页显示条目个数
  pageSize: {
    type: Number,
    default: 10
  }
})

// 定义事件
const emit = defineEmits(['update:pageNum', 'update:pageSize', 'pagination'])

// 定义响应式数据
const tableRef = ref(null)

// 表格数据
const tableData = computed(() => props.data)

// 处理分页
const handlePagination = ({ page, limit }) => {
  emit('update:pageNum', page)
  emit('update:pageSize', limit)
  emit('pagination', { page, limit })
}

// 定义暴露给父组件的方法
const getTableRef = () => {
  return tableRef.value
}

defineExpose({
  getTableRef
})
</script>

<style lang="scss" scoped>
.tree-table {
  width: 100%;
  
  :deep(.el-table) {
    .el-table__inner-wrapper::before {
      height: 0;
    }
  }
}
</style>