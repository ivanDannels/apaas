<template>
  <div class="app-container">
    <div class="form-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
        <el-form-item label="登录地址" prop="ipaddr">
          <el-input
            v-model="queryParams.ipaddr"
            placeholder="请输入登录地址"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="用户名称" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="请输入用户名称"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="成功" value="0" />
            <el-option label="失败" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="登录时间" style="width: 300px">
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
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
          >{{ $t('common.delete') }}</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="Delete"
            @click="handleClean"
          >清空</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
          >导出</el-button>
        </el-col>
      </el-row>
    </div>
    
    <div class="table-container">
      <el-table
        :data="logList"
        style="width: 100%"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="访问编号" align="center" prop="infoId" />
        <el-table-column label="用户名称" align="center" prop="userName" />
        <el-table-column label="登录地址" align="center" prop="ipaddr" width="130" />
        <el-table-column label="登录地点" align="center" prop="loginLocation" />
        <el-table-column label="浏览器" align="center" prop="browser" />
        <el-table-column label="操作系统" align="center" prop="os" />
        <el-table-column label="登录状态" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
              {{ scope.row.status === '0' ? '成功' : '失败' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作信息" align="center" prop="msg" />
        <el-table-column label="登录日期" align="center" prop="loginTime" width="180" />
      </el-table>
      
      <pagination
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { getLoginLogList, delLoginLog, cleanLoginLog } from '@/api/system/log'

// 定义响应式数据
const logList = ref([])
const loading = ref(false)
const total = ref(0)
const ids = ref([])
const multiple = ref(true)
const dateRange = ref([])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  ipaddr: '',
  userName: '',
  status: '',
  orderByColumn: 'loginTime',
  isAsc: 'descending'
})

// 查询表单引用
const queryForm = ref(null)

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

// 获取登录日志列表
const getList = async () => {
  loading.value = true
  try {
    const response = await getLoginLogList(queryParams)
    logList.value = response.data.items
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
  ids.value = selection.map(item => item.infoId)
  multiple.value = !selection.length
}

// 删除日志
const handleDelete = (row) => {
  const infoIds = row.infoId ? [row.infoId] : ids.value
  ElMessageBox.confirm('是否确认删除访问编号为"' + infoIds + '"的数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await delLoginLog(infoIds)
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}

// 清空日志
const handleClean = () => {
  ElMessageBox.confirm('是否确认清空所有登录日志数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await cleanLoginLog()
    ElMessage.success('清空成功')
    getList()
  }).catch(() => {})
}

// 导出日志
const handleExport = () => {
  ElMessageBox.confirm('是否确认导出所有登录日志数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    // 这里应该调用导出API
    ElMessage.success('导出成功')
  }).catch(() => {})
}

// 组件挂载时获取数据
onMounted(() => {
  getList()
})
</script>

<style scoped>
/* 可以在这里添加特定的样式 */
</style>