<template>
  <div class="file-select">
    <el-dialog
      v-model="dialogVisible"
      :title="title"
      width="800px"
      append-to-body
      @close="handleClose"
    >
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="file-tree">
            <el-tree
              ref="treeRef"
              :data="fileTree"
              node-key="id"
              :props="defaultProps"
              @node-click="handleNodeClick"
            />
          </div>
        </el-col>
        <el-col :span="18">
          <div class="file-list">
            <el-form :model="queryForm" ref="queryRef" :inline="true" v-show="showSearch">
              <el-form-item label="文件名" prop="fileName">
                <el-input
                  v-model="queryForm.fileName"
                  placeholder="请输入文件名"
                  clearable
                  style="width: 200px"
                  @keyup.enter="handleQuery"
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                <el-button icon="Refresh" @click="resetQuery">重置</el-button>
              </el-form-item>
            </el-form>
            
            <el-table
              ref="tableRef"
              :data="fileList"
              height="400"
              @selection-change="handleSelectionChange"
              @row-dblclick="handleRowDblClick"
            >
              <el-table-column type="selection" width="50" align="center" />
              <el-table-column label="文件名" prop="fileName" :show-overflow-tooltip="true">
                <template #default="scope">
                  <svg-icon 
                    :icon-class="getFileIcon(scope.row.fileType)" 
                    style="margin-right: 5px"
                  />
                  {{ scope.row.fileName }}
                </template>
              </el-table-column>
              <el-table-column label="文件大小" prop="fileSize" width="120" align="center">
                <template #default="scope">
                  {{ formatFileSize(scope.row.fileSize) }}
                </template>
              </el-table-column>
              <el-table-column label="更新时间" prop="updateTime" width="180" align="center" />
            </el-table>
            
            <pagination
              v-show="total > 0"
              :total="total"
              v-model:page="queryForm.pageNum"
              v-model:limit="queryForm.pageSize"
              @pagination="getList"
            />
          </div>
        </el-col>
      </el-row>
      
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="handleConfirm">确 定</el-button>
          <el-button @click="handleClose">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { listFile } from '@/api/system/file'

// 定义属性
const props = defineProps({
  modelValue: {
    type: Array,
    default: () => []
  },
  title: {
    type: String,
    default: '文件选择'
  },
  multiple: {
    type: Boolean,
    default: true
  }
})

// 定义事件
const emit = defineEmits(['update:modelValue', 'confirm'])

// 定义响应式数据
const dialogVisible = ref(false)
const fileList = ref([])
const fileTree = ref([])
const total = ref(0)
const showSearch = ref(true)
const selectedFiles = ref([])

const queryForm = reactive({
  pageNum: 1,
  pageSize: 10,
  fileName: '',
  fileType: undefined
})

const defaultProps = {
  children: 'children',
  label: 'label'
}

// 定义引用
const queryRef = ref()
const tableRef = ref()
const treeRef = ref()

// 显示对话框
const show = () => {
  dialogVisible.value = true
  getList()
  getFileTree()
}

// 获取文件列表
const getList = () => {
  listFile(queryForm).then(response => {
    fileList.value = response.rows
    total.value = response.total
  })
}

// 获取文件树
const getFileTree = () => {
  // 模拟文件树数据
  fileTree.value = [
    {
      id: 1,
      label: '全部文件',
      children: [
        {
          id: 2,
          label: '图片'
        },
        {
          id: 3,
          label: '文档'
        },
        {
          id: 4,
          label: '视频'
        }
      ]
    }
  ]
}

// 节点点击事件
const handleNodeClick = (data) => {
  // 根据节点类型过滤文件
  queryForm.fileType = data.id === 1 ? undefined : data.label
  getList()
}

// 搜索
const handleQuery = () => {
  queryForm.pageNum = 1
  getList()
}

// 重置搜索
const resetQuery = () => {
  queryRef.value.resetFields()
  handleQuery()
}

// 行选择事件
const handleSelectionChange = (selection) => {
  selectedFiles.value = selection
}

// 双击行
const handleRowDblClick = (row) => {
  selectedFiles.value = props.multiple ? [row] : row
  handleConfirm()
}

// 确认选择
const handleConfirm = () => {
  if (selectedFiles.value.length === 0) {
    ElMessage.warning('请至少选择一个文件')
    return
  }
  
  emit('update:modelValue', selectedFiles.value)
  emit('confirm', selectedFiles.value)
  handleClose()
}

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
  selectedFiles.value = []
  tableRef.value.clearSelection()
}

// 格式化文件大小
const formatFileSize = (size) => {
  if (!size) return ''
  if (size < 1024) {
    return size + 'B'
  } else if (size < 1024 * 1024) {
    return (size / 1024).toFixed(2) + 'KB'
  } else if (size < 1024 * 1024 * 1024) {
    return (size / (1024 * 1024)).toFixed(2) + 'MB'
  } else {
    return (size / (1024 * 1024 * 1024)).toFixed(2) + 'GB'
  }
}

// 获取文件图标
const getFileIcon = (fileType) => {
  if (!fileType) return 'file'
  
  if (['jpg', 'jpeg', 'png', 'gif', 'bmp'].includes(fileType.toLowerCase())) {
    return 'image'
  } else if (['doc', 'docx'].includes(fileType.toLowerCase())) {
    return 'word'
  } else if (['xls', 'xlsx'].includes(fileType.toLowerCase())) {
    return 'excel'
  } else if (['ppt', 'pptx'].includes(fileType.toLowerCase())) {
    return 'ppt'
  } else if (['pdf'].includes(fileType.toLowerCase())) {
    return 'pdf'
  } else if (['zip', 'rar', '7z'].includes(fileType.toLowerCase())) {
    return 'zip'
  } else {
    return 'file'
  }
}

defineExpose({
  show
})
</script>

<style scoped>
.file-tree {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 10px;
  height: 500px;
  overflow-y: auto;
}

.file-list {
  padding: 10px;
}
</style>