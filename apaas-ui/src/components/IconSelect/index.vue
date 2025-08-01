<template>
  <div class="icon-select">
    <el-input
      v-model="selectedIcon"
      placeholder="请选择图标"
      readonly
      @click="openDialog"
    >
      <template #prepend>
        <svg-icon :icon-class="selectedIcon" />
      </template>
      <template #append>
        <el-button icon="Search" @click="openDialog" />
      </template>
    </el-input>
    
    <el-dialog title="选择图标" v-model="dialogVisible" width="800px" append-to-body>
      <el-input
        v-model="searchText"
        placeholder="搜索图标名称"
        clearable
        @input="handleSearch"
        style="margin-bottom: 10px"
      />
      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="Element Plus Icons" name="element">
          <div class="icon-grid">
            <div
              v-for="icon in filteredIcons"
              :key="icon"
              class="icon-item"
              @click="selectIcon(icon)"
            >
              <el-icon :size="20">
                <component :is="icon" />
              </el-icon>
              <span class="icon-name">{{ icon }}</span>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="SVG Icons" name="svg">
          <div class="icon-grid">
            <div
              v-for="icon in svgIcons"
              :key="icon"
              class="icon-item"
              @click="selectIcon(icon)"
            >
              <svg-icon :icon-class="icon" />
              <span class="icon-name">{{ icon }}</span>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
      <pagination
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="handlePagination"
      />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// 定义响应式数据
const dialogVisible = ref(false)
const activeTab = ref('element')
const searchText = ref('')
const selectedIcon = ref('')
const total = ref(0)

// 图标列表
const elementIcons = Object.keys(ElementPlusIconsVue)
const svgIcons = ref([])

// 过滤后的图标列表
const filteredIcons = ref([])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 40
})

// 定义属性
const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  }
})

// 定义事件
const emit = defineEmits(['update:modelValue'])

// 监听modelValue变化
const watchModelValue = () => {
  selectedIcon.value = props.modelValue
}

// 打开对话框
const openDialog = () => {
  dialogVisible.value = true
  searchText.value = ''
  handleSearch()
}

// 处理搜索
const handleSearch = () => {
  if (activeTab.value === 'element') {
    const icons = elementIcons.filter(icon => 
      icon.toLowerCase().includes(searchText.value.toLowerCase())
    )
    total.value = icons.length
    const start = (queryParams.pageNum - 1) * queryParams.pageSize
    const end = start + queryParams.pageSize
    filteredIcons.value = icons.slice(start, end)
  } else {
    const icons = svgIcons.value.filter(icon => 
      icon.toLowerCase().includes(searchText.value.toLowerCase())
    )
    total.value = icons.length
    const start = (queryParams.pageNum - 1) * queryParams.pageSize
    const end = start + queryParams.pageSize
    filteredIcons.value = icons.slice(start, end)
  }
}

// 处理标签页切换
const handleTabChange = () => {
  queryParams.pageNum = 1
  handleSearch()
}

// 处理分页
const handlePagination = () => {
  handleSearch()
}

// 选择图标
const selectIcon = (icon) => {
  selectedIcon.value = icon
  emit('update:modelValue', icon)
  dialogVisible.value = false
}

// 获取SVG图标列表
const getSvgIcons = () => {
  // 模拟SVG图标列表
  svgIcons.value = [
    'user', 'peoples', 'setting', 'eye', 'eye-open', 'tree', 'password',
    'dict', 'download', 'upload', 'edit', 'delete', 'add', 'search',
    'refresh', 'close', 'success', 'error', 'warning', 'info', 'question',
    'date', 'time', 'phone', 'email', 'home', 'menu', 'log', 'notice',
    'flow', 'task', 'role', 'dept', 'post', 'dict-item', 'code', 'swagger'
  ]
}

// 组件挂载时获取数据
onMounted(() => {
  watchModelValue()
  getSvgIcons()
  handleSearch()
})
</script>

<style scoped>
.icon-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
  gap: 10px;
  max-height: 400px;
  overflow-y: auto;
}

.icon-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 10px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.icon-item:hover {
  border-color: #409eff;
  background-color: #ecf5ff;
}

.icon-name {
  margin-top: 5px;
  font-size: 12px;
  text-align: center;
  word-break: break-all;
}
</style>