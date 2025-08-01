<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
        <el-form-item label="流程名称" prop="processName">
          <el-input
            v-model="queryParams.processName"
            placeholder="请输入流程名称"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="发起人" prop="initiator">
          <el-input
            v-model="queryParams.initiator"
            placeholder="请输入发起人"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="运行中" value="running" />
            <el-option label="已完成" value="completed" />
            <el-option label="已终止" value="terminated" />
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
        :data="processInstanceList"
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="id" label="实例ID" width="80" />
        <el-table-column label="流程名称" prop="processName" />
        <el-table-column label="流程标识" prop="processKey" />
        <el-table-column label="发起人" prop="initiator" />
        <el-table-column label="开始时间" prop="startTime" width="180" />
        <el-table-column label="结束时间" prop="endTime" width="180" />
        <el-table-column label="状态" prop="status" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button link type="primary" icon="View" @click="handleView(scope.row)">查看</el-button>
            <el-button v-if="scope.row.status === 'running'" link type="danger" icon="Delete" @click="handleTerminate(scope.row)">终止</el-button>
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
    
    <!-- 流程实例详情对话框 -->
    <el-dialog title="流程实例详情" v-model="detailDialogVisible" width="800px" append-to-body>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="基本信息" name="basic">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="实例ID">{{ currentInstance.id }}</el-descriptions-item>
            <el-descriptions-item label="流程名称">{{ currentInstance.processName }}</el-descriptions-item>
            <el-descriptions-item label="流程标识">{{ currentInstance.processKey }}</el-descriptions-item>
            <el-descriptions-item label="发起人">{{ currentInstance.initiator }}</el-descriptions-item>
            <el-descriptions-item label="开始时间">{{ currentInstance.startTime }}</el-descriptions-item>
            <el-descriptions-item label="结束时间">{{ currentInstance.endTime }}</el-descriptions-item>
            <el-descriptions-item label="状态">
              <el-tag :type="getStatusType(currentInstance.status)">
                {{ getStatusText(currentInstance.status) }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="描述">{{ currentInstance.description }}</el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
        <el-tab-pane label="任务列表" name="tasks">
          <el-table :data="taskList" style="width: 100%">
            <el-table-column prop="id" label="任务ID" width="80" />
            <el-table-column label="任务名称" prop="name" />
            <el-table-column label="处理人" prop="assignee" />
            <el-table-column label="创建时间" prop="createTime" width="180" />
            <el-table-column label="完成时间" prop="completeTime" width="180" />
            <el-table-column label="状态" prop="status" width="100">
              <template #default="scope">
                <el-tag :type="getTaskStatusType(scope.row.status)">
                  {{ getTaskStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
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
import { getProcessInstances } from '@/api/workflow'

// 定义响应式数据
const processInstanceList = ref([])
const loading = ref(false)
const total = ref(0)
const detailDialogVisible = ref(false)
const activeTab = ref('basic')
const currentInstance = ref({})
const taskList = ref([])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  processName: '',
  initiator: '',
  status: ''
})

// 查询表单引用
const queryForm = ref(null)

// 获取流程实例列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getProcessInstances(queryParams)
    processInstanceList.value = response.data.items
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

// 获取状态类型
const getStatusType = (status) => {
  const statusMap = {
    running: 'primary',
    completed: 'success',
    terminated: 'danger'
  }
  return statusMap[status] || 'info'
}

// 获取状态文本
const getStatusText = (status) => {
  const statusMap = {
    running: '运行中',
    completed: '已完成',
    terminated: '已终止'
  }
  return statusMap[status] || '未知'
}

// 获取任务状态类型
const getTaskStatusType = (status) => {
  const statusMap = {
    pending: 'warning',
    completed: 'success',
    rejected: 'danger'
  }
  return statusMap[status] || 'info'
}

// 获取任务状态文本
const getTaskStatusText = (status) => {
  const statusMap = {
    pending: '待处理',
    completed: '已完成',
    rejected: '已拒绝'
  }
  return statusMap[status] || '未知'
}

// 处理查看
const handleView = (row) => {
  currentInstance.value = row
  // 模拟获取任务列表
  taskList.value = [
    {
      id: 1,
      name: '审批任务',
      assignee: '张三',
      createTime: '2023-05-01 10:00:00',
      completeTime: '2023-05-01 11:00:00',
      status: 'completed'
    },
    {
      id: 2,
      name: '确认任务',
      assignee: '李四',
      createTime: '2023-05-01 11:00:00',
      completeTime: '',
      status: 'pending'
    }
  ]
  activeTab.value = 'basic'
  detailDialogVisible.value = true
}

// 处理终止
const handleTerminate = (row) => {
  ElMessageBox.confirm(`确定要终止流程实例 ${row.id} 吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      // 这里调用终止流程实例的API
      // await terminateProcessInstance(row.id)
      ElMessage.success('终止成功')
      getList()
    } catch (error) {
      console.error(error)
      ElMessage.error('终止失败')
    }
  }).catch(() => {
    ElMessage.info('已取消终止')
  })
}

// 组件挂载时获取数据
onMounted(() => {
  getList()
})
</script>

<style scoped>
/* 可以在这里添加特定的样式 */
</style>