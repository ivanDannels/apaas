<template>
  <el-table
    ref="tableRef"
    :data="data"
    :height="height"
    :max-height="maxHeight"
    :stripe="stripe"
    :border="border"
    :size="size"
    :fit="fit"
    :show-header="showHeader"
    :highlight-current-row="highlightCurrentRow"
    :current-row-key="currentRowKey"
    :row-class-name="rowClassName"
    :row-style="rowStyle"
    :cell-class-name="cellClassName"
    :cell-style="cellStyle"
    :header-row-class-name="headerRowClassName"
    :header-row-style="headerRowStyle"
    :header-cell-class-name="headerCellClassName"
    :header-cell-style="headerCellStyle"
    :row-key="rowKey"
    :empty-text="emptyText"
    :default-expand-all="defaultExpandAll"
    :expand-row-keys="expandRowKeys"
    :default-sort="defaultSort"
    :tooltip-effect="tooltipEffect"
    :show-summary="showSummary"
    :sum-text="sumText"
    :summary-method="summaryMethod"
    :span-method="spanMethod"
    :select-on-indeterminate="selectOnIndeterminate"
    :indent="indent"
    :tree-props="treeProps"
    :lazy="lazy"
    :load="load"
    :style="tableStyle"
    :show-overflow-tooltip="showOverflowTooltip"
    :table-layout="tableLayout"
    v-bind="$attrs"
    @select="handleSelect"
    @select-all="handleSelectAll"
    @selection-change="handleSelectionChange"
    @cell-mouse-enter="handleCellMouseEnter"
    @cell-mouse-leave="handleCellMouseLeave"
    @cell-click="handleCellClick"
    @cell-dblclick="handleCellDblclick"
    @cell-contextmenu="handleCellContextmenu"
    @row-click="handleRowClick"
    @row-contextmenu="handleRowContextmenu"
    @row-dblclick="handleRowDblclick"
    @header-click="handleHeaderClick"
    @header-contextmenu="handleHeaderContextmenu"
    @sort-change="handleSortChange"
    @filter-change="handleFilterChange"
    @current-change="handleCurrentChange"
    @header-dragend="handleHeaderDragend"
    @expand-change="handleExpandChange"
  >
    <slot></slot>
    
    <template #empty v-if="$slots.empty">
      <slot name="empty"></slot>
    </template>
    
    <template #append v-if="$slots.append">
      <slot name="append"></slot>
    </template>
  </el-table>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'

// 定义属性
const props = defineProps({
  // 显示的数据
  data: {
    type: Array,
    default: () => []
  },
  // Table 的高度
  height: {
    type: [String, Number],
    default: ''
  },
  // Table 的最大高度
  maxHeight: {
    type: [String, Number],
    default: ''
  },
  // 是否为斑马纹 table
  stripe: {
    type: Boolean,
    default: false
  },
  // 是否带有纵向边框
  border: {
    type: Boolean,
    default: false
  },
  // Table 的尺寸
  size: {
    type: String,
    default: 'default', // large / default / small
    validator: (value) => ['large', 'default', 'small'].includes(value)
  },
  // 列的宽度是否自撑开
  fit: {
    type: Boolean,
    default: true
  },
  // 是否显示表头
  showHeader: {
    type: Boolean,
    default: true
  },
  // 是否要高亮当前行
  highlightCurrentRow: {
    type: Boolean,
    default: false
  },
  // 当前行的 key
  currentRowKey: {
    type: [String, Number],
    default: ''
  },
  // 行的 className 的回调方法
  rowClassName: {
    type: [String, Function],
    default: ''
  },
  // 行的 style 的回调方法
  rowStyle: {
    type: [Object, Function],
    default: () => ({})
  },
  // 单元格的 className 的回调方法
  cellClassName: {
    type: [String, Function],
    default: ''
  },
  // 单元格的 style 的回调方法
  cellStyle: {
    type: [Object, Function],
    default: () => ({})
  },
  // 表头行的 className 的回调方法
  headerRowClassName: {
    type: [String, Function],
    default: ''
  },
  // 表头行的 style 的回调方法
  headerRowStyle: {
    type: [Object, Function],
    default: () => ({})
  },
  // 表头单元格的 className 的回调方法
  headerCellClassName: {
    type: [String, Function],
    default: ''
  },
  // 表头单元格的 style 的回调方法
  headerCellStyle: {
    type: [Object, Function],
    default: () => ({})
  },
  // 行数据的 Key
  rowKey: {
    type: [String, Function],
    default: ''
  },
  // 空数据时显示的文本
  emptyText: {
    type: String,
    default: '暂无数据'
  },
  // 是否默认展开所有行
  defaultExpandAll: {
    type: Boolean,
    default: false
  },
  // 可以通过该属性设置 Table 目前的展开行
  expandRowKeys: {
    type: Array,
    default: () => []
  },
  // 默认的排序列的 prop 和顺序
  defaultSort: {
    type: Object,
    default: () => ({})
  },
  // tooltip effect 属性
  tooltipEffect: {
    type: String,
    default: 'dark', // dark / light
    validator: (value) => ['dark', 'light'].includes(value)
  },
  // 是否在表尾显示合计行
  showSummary: {
    type: Boolean,
    default: false
  },
  // 合计行第一列的文本
  sumText: {
    type: String,
    default: '合计'
  },
  // 自定义的合计计算方法
  summaryMethod: {
    type: Function,
    default: null
  },
  // 合并行或列的计算方法
  spanMethod: {
    type: Function,
    default: null
  },
  // 在多选表格中，当仅有部分行被选中时，点击表头的多选框时的行为
  selectOnIndeterminate: {
    type: Boolean,
    default: true
  },
  // 展示树形数据时，树节点的缩进
  indent: {
    type: Number,
    default: 16
  },
  // 渲染嵌套数据的配置选项
  treeProps: {
    type: Object,
    default: () => ({
      hasChildren: 'hasChildren',
      children: 'children'
    })
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
  // 是否显示超出隐藏的 tooltip
  showOverflowTooltip: {
    type: Boolean,
    default: false
  },
  // 设置表格单元格的布局算法
  tableLayout: {
    type: String,
    default: 'fixed', // fixed / auto
    validator: (value) => ['fixed', 'auto'].includes(value)
  },
  // 表格的 style
  tableStyle: {
    type: Object,
    default: () => ({})
  }
})

// 定义响应式数据
const tableRef = ref(null)

// 定义事件
const emit = defineEmits([
  'select', 'select-all', 'selection-change',
  'cell-mouse-enter', 'cell-mouse-leave',
  'cell-click', 'cell-dblclick', 'cell-contextmenu',
  'row-click', 'row-contextmenu', 'row-dblclick',
  'header-click', 'header-contextmenu',
  'sort-change', 'filter-change',
  'current-change', 'header-dragend',
  'expand-change'
])

// 处理选择事件
const handleSelect = (selection, row) => {
  emit('select', selection, row)
}

// 处理全选事件
const handleSelectAll = (selection) => {
  emit('select-all', selection)
}

// 处理选择变化事件
const handleSelectionChange = (selection) => {
  emit('selection-change', selection)
}

// 处理单元格鼠标进入事件
const handleCellMouseEnter = (row, column, cell, event) => {
  emit('cell-mouse-enter', row, column, cell, event)
}

// 处理单元格鼠标离开事件
const handleCellMouseLeave = (row, column, cell, event) => {
  emit('cell-mouse-leave', row, column, cell, event)
}

// 处理单元格点击事件
const handleCellClick = (row, column, cell, event) => {
  emit('cell-click', row, column, cell, event)
}

// 处理单元格双击事件
const handleCellDblclick = (row, column, cell, event) => {
  emit('cell-dblclick', row, column, cell, event)
}

// 处理单元格右键菜单事件
const handleCellContextmenu = (row, column, cell, event) => {
  emit('cell-contextmenu', row, column, cell, event)
}

// 处理行点击事件
const handleRowClick = (row, column, event) => {
  emit('row-click', row, column, event)
}

// 处理行右键菜单事件
const handleRowContextmenu = (row, column, event) => {
  emit('row-contextmenu', row, column, event)
}

// 处理行双击事件
const handleRowDblclick = (row, column, event) => {
  emit('row-dblclick', row, column, event)
}

// 处理表头点击事件
const handleHeaderClick = (column, event) => {
  emit('header-click', column, event)
}

// 处理表头右键菜单事件
const handleHeaderContextmenu = (column, event) => {
  emit('header-contextmenu', column, event)
}

// 处理排序变化事件
const handleSortChange = ({ column, prop, order }) => {
  emit('sort-change', { column, prop, order })
}

// 处理筛选变化事件
const handleFilterChange = (filters) => {
  emit('filter-change', filters)
}

// 处理当前行变化事件
const handleCurrentChange = (currentRow, oldCurrentRow) => {
  emit('current-change', currentRow, oldCurrentRow)
}

// 处理表头拖拽结束事件
const handleHeaderDragend = (newWidth, oldWidth, column, event) => {
  emit('header-dragend', newWidth, oldWidth, column, event)
}

// 处理展开变化事件
const handleExpandChange = (row, expandedRows) => {
  emit('expand-change', row, expandedRows)
}

// 定义暴露给父组件的方法
const clearSelection = () => {
  if (tableRef.value) {
    tableRef.value.clearSelection()
  }
}

const toggleRowSelection = (row, selected) => {
  if (tableRef.value) {
    tableRef.value.toggleRowSelection(row, selected)
  }
}

const toggleAllSelection = () => {
  if (tableRef.value) {
    tableRef.value.toggleAllSelection()
  }
}

const toggleRowExpansion = (row, expanded) => {
  if (tableRef.value) {
    tableRef.value.toggleRowExpansion(row, expanded)
  }
}

const setCurrentRow = (row) => {
  if (tableRef.value) {
    tableRef.value.setCurrentRow(row)
  }
}

const getSelectionRows = () => {
  if (tableRef.value) {
    return tableRef.value.getSelectionRows()
  }
  return []
}

const sort = (prop, order) => {
  if (tableRef.value) {
    tableRef.value.sort(prop, order)
  }
}

defineExpose({
  clearSelection,
  toggleRowSelection,
  toggleAllSelection,
  toggleRowExpansion,
  setCurrentRow,
  getSelectionRows,
  sort
})
</script>

<style lang="scss" scoped>
// 表格样式可以根据需要自定义
</style>