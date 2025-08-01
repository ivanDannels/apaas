<template>
  <div class="top-right-btn" :style="style">
    <el-row>
      <el-tooltip class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top">
        <el-button circle icon="Search" @click="toggleSearch()" />
      </el-tooltip>
      
      <el-tooltip class="item" effect="dark" content="刷新" placement="top">
        <el-button circle icon="Refresh" @click="refresh()" />
      </el-tooltip>
      
      <el-tooltip class="item" effect="dark" content="显隐列" placement="top" v-if="columns">
        <el-button circle icon="Menu" @click="showColumn()" />
      </el-tooltip>
    </el-row>
    
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-transfer
        :titles="['显示', '隐藏']"
        v-model="value"
        :data="columns"
        @change="dataChange"
      ></el-transfer>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import type { TransferDataItem } from 'element-plus'

// 定义属性
interface Props {
  showSearch?: boolean
  columns?: TransferDataItem[]
  search?: boolean
  gutter?: number
}

const props = withDefaults(defineProps<Props>(), {
  showSearch: true,
  columns: () => [],
  search: true,
  gutter: 10,
})

// 定义事件
interface Emits {
  (e: 'update:showSearch', showSearch: boolean): void
  (e: 'queryTable'): void
}

const emit = defineEmits<Emits>()

// 定义响应式数据
const open = ref(false)
const title = ref('显示/隐藏')
const value = ref<number[]>([])

// 定义计算属性
const style = reactive({
  marginBottom: props.gutter / 2 + 'px',
})

// 切换搜索
const toggleSearch = () => {
  emit('update:showSearch', !props.showSearch)
}

// 刷新
const refresh = () => {
  emit('queryTable')
}

// 显示列
const showColumn = () => {
  open.value = true
  // 显示列的逻辑
  const columnList = props.columns || []
  value.value = []
  columnList.forEach((item, index) => {
    if (!item.hidden) {
      value.value.push(index)
    }
  })
}

// 数据变化
const dataChange = (arr: number[]) => {
  // 数据变化的逻辑
  const columnList = props.columns || []
  columnList.forEach((item, index) => {
    item.hidden = !arr.includes(index)
  })
}
</script>

<style lang="scss" scoped>
:deep(.el-transfer__button) {
  border-radius: 50%;
  display: block;
  margin-left: 0px;
}

:deep(.el-transfer__button:first-child) {
  margin-bottom: 10px;
}

.top-right-btn {
  text-align: right;
  
  .el-button {
    margin-left: 10px;
  }
}
</style>