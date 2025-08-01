<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
        <el-form-item label="任务名称" prop="name">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入任务名称"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="流程名称" prop="processName">
          <el-input
            v-model="queryParams.processName"
            placeholder="请输入流程名称"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="处理人" prop="assignee">
          <el-input
            v-model="queryParams.assignee"
            placeholder="请输入处理人"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="待处理" value="pending" />
            <el-option label="已完成" value="completed" />
            <el-option label="已拒绝" value="rejected" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">{{ $t('common.search') }}</el-button>
          <el-button icon="Refresh" @click="resetQuery">{{ $t('common.reset') }}</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div class="table-container">
      <el-table
        :data="taskList"
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="id" label="任务ID" width="80" />
        <el-table-column label="任务名称" prop="name" />
        <el-table-column label="流程名称" prop="processName" />
        <el-table-column label="处理人" prop="assignee" />
        <el-table-column label="创建时间" prop="createTime" width="180" />
        <el-table-column label="到期时间" prop="dueTime" width="180" />
        <el-table-column label="优先级" prop="priority" width="100">
          <template #default="scope">
            <el-tag :type="getPriorityType(scope.row.priority)">
              {{ getPriorityText(scope.row.priority) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="Edit" @click="handleProcess(scope.row)">处理</el-button>
            <el-button link type="primary" icon="View" @click="handleView(scope.row)">查看</el-button>
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
    
    <!-- 任务处理对话框 -->
    <el-dialog title="处理任务" v-model="processDialogVisible" width="600px" append-to-body>
      <el-form :model="processForm" :rules="processRules" ref="processFormRef" label-width="100px">
        <el-form-item label="任务名称">
          <el-input v-model="currentTask.name" disabled />
        </el-form-item>
        <el-form-item label="处理人">
          <el-input v-model="currentTask.assignee" disabled />
        </el-form-item>
        <el-form-item label="处理意见" prop="comment">
          <el-input v-model="processForm.comment" type="textarea" placeholder="请输入处理意见" />
        </el-form-item>
        <el-form-item label="处理结果" prop="result">
          <el-radio-group v-model="processForm.result">
            <el-radio label="approve">通过</el-radio>
            <el-radio label="reject">拒绝</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="processDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitProcess">提交</el-button>
        </span>
      </template>
    </el-dialog>
    
    <!-- 任务详情对话框 -->
    <el-dialog title="任务详情" v-model="detailDialogVisible" width="800px" append-to-body>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="基本信息" name="basic">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="任务ID">{{ currentTask.id }}</el-descriptions-item>
            <el-descriptions-item label="任务名称">{{ currentTask.name }}</el-descriptions-item>
            <el-descriptions-item label="流程名称">{{ currentTask.processName }}</el-descriptions-item>
            <el-descriptions-item label="处理人">{{ currentTask.assignee }}</el-descriptions-item>
            <el-descriptions-item label="创建时间">{{ currentTask.createTime }}</el-descriptions-item>
            <el-descriptions-item label="到期时间">{{ currentTask.dueTime }}</el-descriptions-item>
            <el-descriptions-item label="优先级">
              <el-tag :type="getPriorityType(currentTask.priority)">
                {{ getPriorityText(currentTask.priority) }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="状态">
              <el-tag :type="getStatusType(currentTask.status)">
                {{ getStatusText(currentTask.status) }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="描述">{{ currentTask.description }}</el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
        <el-tab-pane label="表单数据" name="form">
          <el-table :data="formData" style="width: 100%">
            <el-table-column prop="field" label="字段名" />
            <el-table-column prop="value" label="字段值" />
          </el-table>
        </el-tab-pane>
      </el-tabs>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="detailDialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { getTasks, completeTask } from '@/api/workflow'

// 定义响应式数据
const taskList = ref([])
const loading = ref(false)
const total = ref(0)
const processDialogVisible = ref(false)
const detailDialogVisible = ref(false)
const activeTab = ref('basic')
const currentTask = ref({})
const formData = ref([])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  processName: '',
  assignee: '',
  status: ''
})

// 处理表单数据
const processForm = reactive({
  comment: '',
  result: 'approve'
})

// 处理表单验证规则
const processRules = {
  comment: [{ required: true, message: '请输入处理意见', trigger: 'blur' }]
}

// 查询表单引用
const queryForm = ref(null)
const processFormRef = ref(null)

// 获取任务列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getTasks(queryParams)
    taskList.value = response.data.items
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
  queryForm.value?.resetFields()
  handleQuery()
}

// 获取优先级类型
const getPriorityType = (priority) => {
  const priorityMap = {
    high: 'danger',
    medium: 'warning',
    low: 'success'
  }
  return priorityMap[priority] || 'info'
}

// 获取优先级文本
const getPriorityText = (priority) => {
  const priorityMap = {
    high: '高',
    medium: '中',
    low: '低'
  }
  return priorityMap[priority] || '未知'
}

// 获取状态类型
const getStatusType = (status) => {
  const statusMap = {
    pending: 'warning',
    completed: 'success',
    rejected: 'danger'
  }
  return statusMap[status] || 'info'
}

// 获取状态文本
const getStatusText = (status) => {
  const statusMap = {
    pending: '待处理',
    completed: '已完成',
    rejected: '已拒绝'
  }
  return statusMap[status] || '未知'
}

// 处理任务
const handleProcess = (row) => {
  currentTask.value = row
  processForm.comment = ''
  processForm.result = 'approve'
  processDialogVisible.value = true
}

// 提交处理
const submitProcess = () => {
  if (!processFormRef.value) return
  
  processFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const data = {
          comment: processForm.comment,
          result: processForm.result
        }
        await completeTask(currentTask.value.id, data)
        ElMessage.success('处理成功')
        processDialogVisible.value = false
        getList()
      } catch (error) {
        console.error(error)
        ElMessage.error('处理失败')
      }
    }
  })
}

// 查看任务详情
const handleView = (row) => {
  currentTask.value = row
  // 模拟表单数据
  formData.value = [
    { field: '申请人', value: '张三' },
    { field: '申请类型', value: '请假' },
    { field: '开始时间', value: '2023-05-01' },
    { field: '结束时间', value: '2023-05-03' },
    { field: '原因', value: '生病' }
  ]
  activeTab.value = 'basic'
  detailDialogVisible.value = true
}

// 组件挂载时获取数据
onMounted(() => {
  getList()
})
</script>

<style scoped>
/* 可以在这里添加特定的样式 */
</style>