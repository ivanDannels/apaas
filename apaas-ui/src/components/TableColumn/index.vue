<template>
  <el-table-column
    :type="type"
    :index="index"
    :column-key="columnKey"
    :label="label"
    :prop="prop"
    :width="width"
    :min-width="minWidth"
    :fixed="fixed"
    :render-header="renderHeader"
    :sortable="sortable"
    :sort-method="sortMethod"
    :sort-by="sortBy"
    :sort-orders="sortOrders"
    :resizable="resizable"
    :formatter="formatter"
    :show-overflow-tooltip="showOverflowTooltip"
    :align="align"
    :header-align="headerAlign"
    :class-name="className"
    :label-class-name="labelClassName"
    :selectable="selectable"
    :reserve-selection="reserveSelection"
    :filters="filters"
    :filter-placement="filterPlacement"
    :filter-multiple="filterMultiple"
    :filter-method="filterMethod"
    :filtered-value="filteredValue"
    v-bind="$attrs"
  >
    <template #default="scope" v-if="$slots.default">
      <slot :row="scope.row" :column="scope.column" :$index="scope.$index"></slot>
    </template>
    
    <template #header="scope" v-if="$slots.header">
      <slot name="header" :column="scope.column" :$index="scope.$index"></slot>
    </template>
  </el-table-column>
</template>

<script setup lang="ts">
// 定义属性
const props = defineProps({
  // 列的类型
  type: {
    type: String,
    default: 'default', // selection / index / expand
    validator: (value) => ['default', 'selection', 'index', 'expand'].includes(value)
  },
  // 索引列的序号
  index: {
    type: [Number, Function],
    default: 1
  },
  // column 的 key
  columnKey: {
    type: String,
    default: ''
  },
  // 显示的标题
  label: {
    type: String,
    default: ''
  },
  // 对应列内容的字段名
  prop: {
    type: String,
    default: ''
  },
  // 对应列的宽度
  width: {
    type: [String, Number],
    default: ''
  },
  // 对应列的最小宽度
  minWidth: {
    type: [String, Number],
    default: ''
  },
  // 列是否固定在左侧或者右侧
  fixed: {
    type: [Boolean, String],
    default: false, // true / false / 'left' / 'right'
    validator: (value) => [true, false, 'left', 'right'].includes(value)
  },
  // 列标题 Label 区域渲染使用的 Function
  renderHeader: {
    type: Function,
    default: null
  },
  // 对应列是否可以排序
  sortable: {
    type: [Boolean, String],
    default: false, // true / false / 'custom'
    validator: (value) => [true, false, 'custom'].includes(value)
  },
  // 指定数据按照哪个属性进行排序，仅当 sortable 为 true 且没有设置 sort-method 或 sort-by 的时候有效
  sortMethod: {
    type: Function,
    default: null
  },
  // 指定数据按照哪个属性进行排序，仅当 sortable 为 true 且没有设置 sort-method 的时候有效
  sortBy: {
    type: [String, Array, Function],
    default: ''
  },
  // 数据在排序时所使用排序策略的轮转顺序
  sortOrders: {
    type: Array,
    default: () => ['ascending', 'descending', null]
  },
  // 对应列是否可以通过拖动改变宽度
  resizable: {
    type: Boolean,
    default: true
  },
  // 用来格式化内容
  formatter: {
    type: Function,
    default: null
  },
  // 当内容过长被隐藏时显示 tooltip
  showOverflowTooltip: {
    type: Boolean,
    default: false
  },
  // 对齐方式
  align: {
    type: String,
    default: 'left', // left / center / right
    validator: (value) => ['left', 'center', 'right'].includes(value)
  },
  // 表头对齐方式，若不设置该项，则使用表格的对齐方式
  headerAlign: {
    type: String,
    default: '', // left / center / right
    validator: (value) => ['', 'left', 'center', 'right'].includes(value)
  },
  // 列的 className
  className: {
    type: String,
    default: ''
  },
  // 当前列标题的自定义类名
  labelClassName: {
    type: String,
    default: ''
  },
  // 仅对 type=selection 的列有效，类型为 Function
  selectable: {
    type: Function,
    default: null
  },
  // 仅对 type=selection 的列有效，类型为 Boolean
  reserveSelection: {
    type: Boolean,
    default: false
  },
  // 数据过滤的选项
  filters: {
    type: Array,
    default: () => []
  },
  // 过滤弹出框的定位
  filterPlacement: {
    type: String,
    default: ''
  },
  // 数据过滤的选项是否多选
  filterMultiple: {
    type: Boolean,
    default: true
  },
  // 数据过滤使用的方法
  filterMethod: {
    type: Function,
    default: null
  },
  // 选中的数据过滤项
  filteredValue: {
    type: Array,
    default: () => []
  }
})
</script>

<style lang="scss" scoped>
// 表格列样式可以根据需要自定义
</style>