<template>
  <div class="data-table">
    <!-- 表格工具栏 -->
    <div class="table-toolbar" v-if="showToolbar">
      <div class="toolbar-left">
        <slot name="toolbar-left"></slot>
      </div>
      <div class="toolbar-right">
        <el-button 
          v-if="showRefresh" 
          :icon="Refresh" 
          @click="refresh"
        >
          刷新
        </el-button>
        
        <el-button 
          v-if="showExport" 
          :icon="Download" 
          @click="exportData"
        >
          导出
        </el-button>
        
        <slot name="toolbar-right"></slot>
      </div>
    </div>
    
    <!-- 表格 -->
    <el-table
      ref="tableRef"
      :data="tableData"
      :height="height"
      :max-height="maxHeight"
      :stripe="stripe"
      :border="border"
      :size="size"
      :highlight-current-row="highlightCurrentRow"
      :row-key="rowKey"
      :default-expand-all="defaultExpandAll"
      :expand-row-keys="expandRowKeys"
      :lazy="lazy"
      :load="load"
      :tree-props="treeProps"
      @selection-change="handleSelectionChange"
      @select="handleSelect"
      @select-all="handleSelectAll"
      @current-change="handleCurrentChange"
      @row-click="handleRowClick"
      @row-dblclick="handleRowDblclick"
      @row-contextmenu="handleRowContextmenu"
      @cell-click="handleCellClick"
      @cell-dblclick="handleCellDblclick"
      @cell-contextmenu="handleCellContextmenu"
      @header-click="handleHeaderClick"
      @header-contextmenu="handleHeaderContextmenu"
      @sort-change="handleSortChange"
      @filter-change="handleFilterChange"
      v-bind="$attrs"
    >
      <!-- 列定义 -->
      <slot></slot>
      
      <!-- 默认操作列 -->
      <el-table-column 
        v-if="showOperation && $slots.operation" 
        label="操作" 
        fixed="right" 
        align="center" 
        :width="operationWidth"
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
import { Refresh, Download } from '@element-plus/icons-vue'
import Pagination from '@/components/Pagination/index.vue'

// 定义属性
const props = defineProps({
  // 表格数据
  data: {
    type: Array,
    default: () => []
  },
  // 表格高度
  height: {
    type: [String, Number],
    default: undefined
  },
  // 表格最大高度
  maxHeight: {
    type: [String, Number],
    default: undefined
  },
  // 是否显示斑马纹
  stripe: {
    type: Boolean,
    default: false
  },
  // 是否显示边框
  border: {
    type: Boolean,
    default: false
  },
  // 表格大小
  size: {
    type: String,
    default: 'default'
  },
  // 是否高亮当前行
  highlightCurrentRow: {
    type: Boolean,
    default: false
  },
  // 行数据的 Key
  rowKey: {
    type: [String, Function],
    default: 'id'
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
  // 渲染嵌套数据的配置选项
  treeProps: {
    type: Object,
    default: () => ({
      children: 'children',
      hasChildren: 'hasChildren'
    })
  },
  // 是否显示工具栏
  showToolbar: {
    type: Boolean,
    default: true
  },
  // 是否显示刷新按钮
  showRefresh: {
    type: Boolean,
    default: true
  },
  // 是否显示导出按钮
  showExport: {
    type: Boolean,
    default: true
  },
  // 是否显示操作列
  showOperation: {
    type: Boolean,
    default: true
  },
  // 操作列宽度
  operationWidth: {
    type: [String, Number],
    default: 150
  },
  // 是否显示分页
  showPagination: {
    type: Boolean,
    default: true
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
const emit = defineEmits([
  'update:pageNum', 
  'update:pageSize', 
  'pagination',
  'refresh',
  'export',
  'selection-change',
  'select',
  'select-all',
  'current-change',
  'row-click',
  'row-dblclick',
  'row-contextmenu',
  'cell-click',
  'cell-dblclick',
  'cell-contextmenu',
  'header-click',
  'header-contextmenu',
  'sort-change',
  'filter-change'
])

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

// 刷新
const refresh = () => {
  emit('refresh')
}

// 导出数据
const exportData = () => {
  emit('export')
}

// 处理选择项变化
const handleSelectionChange = (selection) => {
  emit('selection-change', selection)
}

// 处理选择
const handleSelect = (selection, row) => {
  emit('select', selection, row)
}

// 处理全选
const handleSelectAll = (selection) => {
  emit('select-all', selection)
}

// 处理当前行变化
const handleCurrentChange = (currentRow, oldCurrentRow) => {
  emit('current-change', currentRow, oldCurrentRow)
}

// 处理行点击
const handleRowClick = (row, column, event) => {
  emit('row-click', row, column, event)
}

// 处理行双击
const handleRowDblclick = (row, column, event) => {
  emit('row-dblclick', row, column, event)
}

// 处理行右键菜单
const handleRowContextmenu = (row, column, event) => {
  emit('row-contextmenu', row, column, event)
}

// 处理单元格点击
const handleCellClick = (row, column, cell, event) => {
  emit('cell-click', row, column, cell, event)
}

// 处理单元格双击
const handleCellDblclick = (row, column, cell, event) => {
  emit('cell-dblclick', row, column, cell, event)
}

// 处理单元格右键菜单
const handleCellContextmenu = (row, column, cell, event) => {
  emit('cell-contextmenu', row, column, cell, event)
}

// 处理表头点击
const handleHeaderClick = (column, event) => {
  emit('header-click', column, event)
}

// 处理表头右键菜单
const handleHeaderContextmenu = (column, event) => {
  emit('header-contextmenu', column, event)
}

// 处理排序
const handleSortChange = ({ column, prop, order }) => {
  emit('sort-change', { column, prop, order })
}

// 处理筛选
const handleFilterChange = (filters) => {
  emit('filter-change', filters)
}

// 定义暴露给父组件的方法
const getTableRef = () => {
  return tableRef.value
}

const getSelection = () => {
  return tableRef.value.getSelectionRows()
}

const clearSelection = () => {
  tableRef.value.clearSelection()
}

const toggleRowSelection = (row, selected) => {
  tableRef.value.toggleRowSelection(row, selected)
}

const setCurrentRow = (row) => {
  tableRef.value.setCurrentRow(row)
}

defineExpose({
  getTableRef,
  getSelection,
  clearSelection,
  toggleRowSelection,
  setCurrentRow
})
</script>

<style lang="scss" scoped>
.data-table {
  width: 100%;
  
  .table-toolbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 0;
    margin-bottom: 10px;
    
    .toolbar-left,
    .toolbar-right {
      display: flex;
      align-items: center;
      gap: 10px;
    }
  }
}
</style>