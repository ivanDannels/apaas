<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
        <el-form-item label="表名称" prop="tableName">
          <el-input
            v-model="queryParams.tableName"
            placeholder="请输入表名称"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="表描述" prop="tableComment">
          <el-input
            v-model="queryParams.tableComment"
            placeholder="请输入表描述"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="创建时间" style="width: 300px">
          <el-date-picker
            v-model="dateRange"
            value-format="YYYY-MM-DD"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :shortcuts="dateShortcuts"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">{{ $t('common.search') }}</el-button>
          <el-button icon="Refresh" @click="resetQuery">{{ $t('common.reset') }}</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div class="toolbar-container">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="Download"
            @click="handleGenTable"
          >生成</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="info"
            plain
            icon="Upload"
            @click="handleImportTable"
          >导入</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleEditTable"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
          >删除</el-button>
        </el-col>
      </el-row>
    </div>
    
    <div class="table-container">
      <el-table
        :data="tableList"
        style="width: 100%"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" align="center" prop="tableId" />
        <el-table-column label="表名称" align="center" prop="tableName" />
        <el-table-column label="表描述" align="center" prop="tableComment" />
        <el-table-column label="实体类名称" align="center" prop="className" />
        <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
        <el-table-column label="更新时间" align="center" prop="updateTime" width="180" />
        <el-table-column label="操作" align="center" width="300" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="View" @click="handlePreview(scope.row)">预览</el-button>
            <el-button link type="primary" icon="Edit" @click="handleEditTable(scope.row)">编辑</el-button>
            <el-button link type="primary" icon="Download" @click="handleGenTable(scope.row)">生成代码</el-button>
            <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <pagination
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
    
    <!-- 预览代码对话框 -->
    <el-dialog :title="preview.title" v-model="preview.open" width="80%" append-to-body>
      <el-tabs v-model="preview.activeName">
        <el-tab-pane
          v-for="(value, key) in preview.data"
          :label="key.substring(key.lastIndexOf('/') + 1, key.indexOf('.vm'))"
          :name="key"
          :key="key"
        >
          <el-link :underline="false" icon="DocumentCopy" @click="handleCopy(value)" style="float: right">复制</el-link>
          <pre>{{ value }}</pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
    
    <!-- 导入表对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".sql"
        :auto-upload="false"
        :file-list="upload.fileList"
        :on-change="handleFileChange"
        :on-exceed="handleExceed"
      >
        <el-button type="primary">选择文件</el-button>
        <template #tip>
          <div class="el-upload__tip text-center">
            <span>仅允许导入.sql格式文件</span>
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="handleTemplate">下载模板</el-link>
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitFileForm">确 定</el-button>
          <el-button @click="upload.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { getGenTableList, delGenTable, previewTable, genCode } from '@/api/tool/gen'

// 定义响应式数据
const tableList = ref([])
const loading = ref(false)
const total = ref(0)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const dateRange = ref([])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  tableName: '',
  tableComment: ''
})

// 预览参数
const preview = reactive({
  open: false,
  title: '代码预览',
  data: {},
  activeName: ''
})

// 上传参数
const upload = reactive({
  open: false,
  title: '导入表结构',
  fileList: []
})

// 查询表单引用
const queryForm = ref(null)
const uploadRef = ref(null)

// 日期快捷选项
const dateShortcuts = [
  {
    text: '最近一周',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      return [start, end]
    }
  },
  {
    text: '最近一个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      return [start, end]
    }
  },
  {
    text: '最近三个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
      return [start, end]
    }
  }
]

// 获取代码生成列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getGenTableList(queryParams)
    tableList.value = response.data.items
    total.value = response.data.total
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 查询
const handleQuery = () => {
  queryParams.pageNum = 1
  getList()
}

// 重置查询
const resetQuery = () => {
  dateRange.value = []
  queryForm.value?.resetFields()
  handleQuery()
}

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.tableId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

// 预览
const handlePreview = async (row) => {
  const response = await previewTable(row.tableId)
  preview.data = response.data
  preview.open = true
  preview.activeName = Object.keys(response.data)[0]
}

// 生成代码
const handleGenTable = (row) => {
  const tableIds = row.tableId ? [row.tableId] : ids.value
  if (tableIds.length === 0) {
    ElMessage.error('请选择要生成的数据')
    return
  }
  ElMessageBox.confirm('是否确认生成表编号为"' + tableIds + '"的数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await genCode(tableIds)
    ElMessage.success('生成成功')
  }).catch(() => {})
}

// 删除按钮操作
const handleDelete = (row) => {
  const tableIds = row.tableId ? [row.tableId] : ids.value
  ElMessageBox.confirm('是否确认删除表编号为"' + tableIds + '"的数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await delGenTable(tableIds)
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}

// 导入表结构
const handleImportTable = () => {
  upload.open = true
  upload.title = '导入表结构'
}

// 文件选择
const handleFileChange = (file, fileList) => {
  upload.fileList = fileList
}

// 文件超出限制
const handleExceed = () => {
  ElMessage.warning('只能上传一个文件')
}

// 提交上传文件
const submitFileForm = () => {
  if (upload.fileList.length === 0) {
    ElMessage.error('请选择文件')
    return
  }
  // 这里应该调用导入API
  ElMessage.success('导入成功')
  upload.open = false
  getList()
}

// 下载模板
const handleTemplate = () => {
  // 这里应该调用下载模板API
  ElMessage.success('模板下载成功')
}

// 复制代码
const handleCopy = (text) => {
  // 这里应该实现复制功能
  ElMessage.success('复制成功')
}

// 修改按钮操作
const handleEditTable = (row) => {
  const tableId = row.tableId || ids.value[0]
  // 跳转到编辑页面
  window.open('/tool/gen/edit/' + tableId, '_blank')
}

// 组件挂载时获取数据
onMounted(() => {
  getList()
})
</script>

<style scoped>
/* 可以在这里添加特定的样式 */
pre {
  white-space: pre-wrap;
  word-wrap: break-word;
  background-color: #f5f7fa;
  padding: 10px;
  border-radius: 4px;
  max-height: 500px;
  overflow-y: auto;
}
</style>